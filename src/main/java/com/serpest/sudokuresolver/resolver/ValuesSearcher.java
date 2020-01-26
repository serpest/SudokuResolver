package main.java.com.serpest.sudokuresolver.resolver;

import java.util.List;
import java.util.concurrent.Callable;

import main.java.com.serpest.sudokuresolver.entities.Point;
import main.java.com.serpest.sudokuresolver.entities.Sudoku;

/**
 * It searches a possible solution testing every possibility.
 */
class ValuesSearcher implements Callable<Sudoku> {

	/**
	 * The original sudoku to resolve.
	 */
	private final Sudoku SUDOKU;

	/**
	 * The empty cells of the sudoku.
	 */
	private final List<Point> EMPTY_CELLS;



	/**
	 * @param FIRST_VALUE the first tested value
	 */
	public ValuesSearcher(final Sudoku SUDOKU, final int FIRST_VALUE, final List<Point> EMPTY_CELLS) {
		this.SUDOKU = SUDOKU;
		this.EMPTY_CELLS = EMPTY_CELLS;
		initFirstValue(FIRST_VALUE);
	}



	@Override
	public Sudoku call() {
		findSolution(0);
		return SUDOKU;
	}



	/**
	 * It searches the sudoku solution using a recursion process.
	 * If it does't find any solution it returns <code>false</code>
	 * 
	 * @param index the <code>EMPTY_CELLS</code> index
	 * @return is the solution found?
	 */
	private boolean findSolution(int index) {
		if (index == EMPTY_CELLS.size())
			return true;
		else if (SUDOKU.getValue(EMPTY_CELLS.get(index)) == Sudoku.SIZE) {
			SUDOKU.setValue(EMPTY_CELLS.get(index), (byte) 0);
			return false;
		}
		else {
			SUDOKU.setValue(EMPTY_CELLS.get(index), (byte) (SUDOKU.getValue(EMPTY_CELLS.get(index)) + 1));
			if (SUDOKU.isValid() && findSolution(index + 1))
				return true;
			else
				return findSolution(index);
		}
	}

	/**
	 * It sets up the initial values of the sudoku.
	 * It's used to increment the performance using multi-threading.
	 * 
	 * @param FIRST_VALUE the initial value
	 */
	private void initFirstValue(final int FIRST_VALUE) {
		int FIRST_VALUELenght = (int) (Math.log10(FIRST_VALUE) + 1);
		if (FIRST_VALUE == 0)
			FIRST_VALUELenght = 1;
		for (int i = 1; i <= FIRST_VALUELenght; i++)
			SUDOKU.setValue(EMPTY_CELLS.get(i - 1), (byte) (FIRST_VALUE / Math.pow(10, FIRST_VALUELenght - i) - 10 * (byte) (FIRST_VALUE / Math.pow(10, FIRST_VALUELenght - i + 1))));
	}

}
