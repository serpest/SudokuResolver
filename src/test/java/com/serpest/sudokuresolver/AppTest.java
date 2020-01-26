package test.java.com.serpest.sudokuresolver;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.serpest.sudokuresolver.entities.Sudoku;
import main.java.com.serpest.sudokuresolver.resolver.Resolver;

public class AppTest {

	private final static byte[][] UNSOLVED_SUDOKU_CONTENT = {	{ 0, 9, 0, 0, 0, 0, 8, 5, 3 },
																{ 0, 0, 0, 8, 0, 0, 0, 0, 4 },
																{ 0, 0, 8, 2, 0, 3, 0, 6, 9 },
																{ 5, 7, 4, 0, 0, 2, 0, 0, 0 },
																{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
																{ 0, 0, 0, 9, 0, 0, 6, 3, 7 },
																{ 9, 4, 0, 1, 0, 8, 5, 0, 0 },
																{ 7, 0, 0, 0, 0, 6, 0, 0, 0 },
																{ 6, 8, 2, 0, 0, 0, 0, 9, 0 }};

	private final static byte[][] SOLVED_SUDOKU_CONTENT = {	{ 2, 9, 7, 6, 1, 4, 8, 5, 3 },
															{ 1, 3, 6, 8, 5, 9, 7, 2, 4 },
															{ 4, 5, 8, 2, 7, 3, 1, 6, 9 },
															{ 5, 7, 4, 3, 6, 2, 9, 1, 8 },
															{ 3, 6, 9, 7, 8, 1, 2, 4, 5 },
															{ 8, 2, 1, 9, 4, 5, 6, 3, 7 },
															{ 9, 4, 3, 1, 2, 8, 5, 7, 6 },
															{ 7, 1, 5, 4, 9, 6, 3, 8, 2 },
															{ 6, 8, 2, 5, 3, 7, 4, 9, 1 }};



	private static void verifyEqualContent(byte[][] content0, byte[][] content1) {
		assertEquals(content0.length, content1.length);
		for (int i = 0; i < content0.length; i++) {
			assertArrayEquals(content0[i], content1[i]);
		}
	}



	@Test
	public void test0() {
		//Default number of threads
		Sudoku sudoku = new Sudoku(UNSOLVED_SUDOKU_CONTENT);
		Resolver resolver = new Resolver(sudoku);
		Sudoku solvedSudoku = resolver.getSolvedSudoku();
		verifyEqualContent(SOLVED_SUDOKU_CONTENT, solvedSudoku.getValues());
	}

	@Test
	public void test1() {
		//Custom number of threads
		Sudoku sudoku = new Sudoku(UNSOLVED_SUDOKU_CONTENT);
		Resolver resolver = new Resolver(sudoku, 16);
		Sudoku solvedSudoku = resolver.getSolvedSudoku();
		verifyEqualContent(SOLVED_SUDOKU_CONTENT, solvedSudoku.getValues());
	}

}
