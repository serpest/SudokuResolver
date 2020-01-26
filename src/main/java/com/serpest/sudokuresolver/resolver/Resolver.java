package main.java.com.serpest.sudokuresolver.resolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import main.java.com.serpest.sudokuresolver.entities.Point;
import main.java.com.serpest.sudokuresolver.entities.Sudoku;

/**
 * It solves the sudoku using multi-threading.
 * It's a brute force resolver.
 */
public class Resolver {

	/**
	 * The default number of threads.
	 */
	private static final int DEFAULT_THREADS_NUMBER = Runtime.getRuntime().availableProcessors();



	/**
	 * The original sudoku to resolve.
	 */
	private final Sudoku ORIGINAL_SUDOKU;

	/**
	 * The empty cells of the sudoku.
	 */
	private final List<Point> EMPTY_CELLS;

	/**
	 * The number of threads to use.
	 */
	private final int THREADS_NUMBER;



	/**
	 * It creates a sudoku resolver.
	 * It can solve only the sudoku of the parameter.
	 * 
	 * @param ORIGINAL_SUDOKU the original sudoku
	 */
	public Resolver(final Sudoku ORIGINAL_SUDOKU) {
		this(ORIGINAL_SUDOKU, DEFAULT_THREADS_NUMBER);
	}

	/**
	 * It creates a sudoku resolver.
	 * It can solve only the sudoku of the parameter.
	 * 
	 * @param ORIGINAL_SUDOKU the original sudoku
	 * @param THREADS_NUMBER the number of threads to use
	 * @throws IllegalArgumentException if <code>THREADS_NUMBER</code> isn't a positive value
	 */
	public Resolver(final Sudoku ORIGINAL_SUDOKU, final int THREADS_NUMBER) throws IllegalArgumentException {
		if (THREADS_NUMBER <= 0)
			throw new IllegalArgumentException("\"THREADS_NUMBER\" must be a positive value.");
		this.ORIGINAL_SUDOKU = ORIGINAL_SUDOKU;
		this.THREADS_NUMBER = THREADS_NUMBER;
		this.EMPTY_CELLS = initEmptyCells();
	}



	/**
	 * It solves the sudoku and return the solved sudoku.
	 */
	public Sudoku getSolvedSudoku() {
		Collection<ValuesSearcher> tasks = getValuesSearcherTasks();
		ExecutorService threadsPool = Executors.newFixedThreadPool(THREADS_NUMBER);
		Sudoku solvedSudoku = null;
		try {
			List<Future<Sudoku>> solvedSudokusFutures = threadsPool.invokeAll(tasks);
			for (Future<Sudoku> future : solvedSudokusFutures) {
				solvedSudoku = future.get();
				if (solvedSudoku != null)
					break;
			}
		} catch (InterruptedException | ExecutionException exc) {
			System.err.println("Exception: sudoku resolver tasks aborted.");
			System.exit(1);
		}
		return solvedSudoku;
	}



	List<Point> getEMPTY_CELLS() {
		return EMPTY_CELLS;
	}


	/**
	 * @return the empty cells of <code>ORIGINAL_SUDOKU</code>
	 */
	private List<Point> initEmptyCells() {
		List<Point> emptyCells = new ArrayList<>();
		Point currentPoint;
		for (int y = 0; y < Sudoku.SIZE; y++) {
			for (int x = 0; x < Sudoku.SIZE; x++) {
				currentPoint = new Point(y, x);
				if (ORIGINAL_SUDOKU.getValue(currentPoint) == 0)
					emptyCells.add(currentPoint);
			}
		}
		return emptyCells;
	}

	/**
	 * It creates the tasks dividing the work.
	 * 
	 * @return the tasks
	 */
	private Collection<ValuesSearcher> getValuesSearcherTasks() {
		int searcherRange = 999_999 / THREADS_NUMBER;
		Collection<ValuesSearcher> tasks = new ArrayList<>(THREADS_NUMBER);
		for (int i = 0; i < THREADS_NUMBER; i++) {
			tasks.add(new ValuesSearcher(ORIGINAL_SUDOKU.clone(), searcherRange*i, EMPTY_CELLS));	
		}
		return tasks;
	}

}
