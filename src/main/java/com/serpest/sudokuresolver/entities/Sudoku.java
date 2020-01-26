package main.java.com.serpest.sudokuresolver.entities;

import java.util.HashSet;
import java.util.Set;

import main.java.com.serpest.sudokuresolver.entities.exceptions.PositionNotExistsException;
import main.java.com.serpest.sudokuresolver.entities.exceptions.SubregionIndexException;

/**
 * A classic 9x9 sudoku.
 */
public class Sudoku implements Cloneable {

	/**
	 * The grid size and the subregions total size.
	 */
	public final static byte SIZE = 9;



	/**
	 * The content of the sudoku grid.
	 * It uses coordinates (column[y]; row[x]).
	 */
	private byte[][] values;



	/**
	 * It creates a empty sudoku.
	 */
	public Sudoku() {
		setValues(new byte[SIZE][SIZE]);
	}

	/**
	 * It creates a normal sudoku.
	 * 
	 * @param values the sudoku's content
	 */
	public Sudoku(byte[][] values) {
		setValues(values);
	}



	public byte[][] getValues() {
		return values;
	}

	public void setValues(byte[][] values) {
		//Verification
		boolean ok = values.length == SIZE;
		for (byte[] values01 : values) {
			if (values01.length != SIZE) {
				ok = false;
				break;
			}
		}
		if (!ok) {
			throw new IllegalArgumentException();
		}
		//Assignment
		this.values = values;
	}

	/**
	 * @param point the point of the value
	 * @return the value
	 */
	public byte getValue(Point point) {
		doesThePointExist(point, true);
		return getValues()[point.getY()][point.getX()];
	}

	/**
	 * @param point the point where insert the value
	 * @param value the value
	 */
	public void setValue(Point point, byte value) {
		doesThePointExist(point, true);
		getValues()[point.getY()][point.getX()] = value;
	}

	/**
	 * @param index the row index 
	 * @return the row
	 */
	public byte[] getRow(int index) {
		doesThePointExist(new Point(0, index), true);
		return getValues()[index];
	}

	/**
	 * @param index the column index 
	 * @return the column
	 */
	public byte[] getColumn(int index) {
		doesThePointExist(new Point(index, 0), true);
		byte[] column = new byte[SIZE];
		for (byte i = 0; i < getValues().length; i++) {
			column[i] = getValues()[i][index];
		}
		return column;
	}

	/**
	 * @param index the subregion index (0 - <code>SIZE - 1</code>)
	 * @return the subregion array
	 * @throws SubregionIndexException if the index isn't between 0 and 8
	 */
	public byte[] getSubregion(int index) throws SubregionIndexException {
		if (index > SIZE - 1 || index < 0) {
			throw new SubregionIndexException(0, SIZE - 1);
		}
		Point point = new Point(index % 3 * 3, (int)(index / 3) * 3);
		return getSubregion(point);
	}

	/**
	 * @return is the sudoku valid?
	 */
	public boolean isValid() {
		for (int i = 0; i < SIZE; i++) {
			if (!isValid(getRow(i)) || !isValid(getColumn(i)) || !isValid(getSubregion(i)))
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (byte[] row : getValues()) {
			sb.append(System.lineSeparator()+ "-" + "----".repeat(getValues().length) + System.lineSeparator() + "| ");
			for (int number : row) {
				sb.append(((number != 0) ? number : " ") + " | ");
			}
		}
		sb.append(System.lineSeparator()+ "-" + "----".repeat(getValues().length) + System.lineSeparator());
		return sb.toString();
	}

	@Override
	public Sudoku clone() {
		byte[][] clonedValues = new byte[SIZE][];
		for (int i = 0; i < SIZE; i++) {
			values.clone();
			clonedValues[i] = values[i].clone();
		}
		return new Sudoku(clonedValues);
	}



	/**
	 * @param firstPoint the first point of the 3x3 subregion
	 * @return a unique subregion array
	 */
	private byte[] getSubregion(Point firstPoint) {
		byte[] subregion = new byte[SIZE];
		for (byte i = 0; i < SIZE; i++) {
			subregion[i] = getValues()[firstPoint.getY() + (int)(i / 3)][firstPoint.getX() + i % 3];
		}
		return subregion;
	}

	/**
	 * @param numbers the numbers of a row, column or subregion to analyze
	 * @return are the numbers valid?
	 */
	private boolean isValid(byte[] numbers) {
		Set<Byte> set = new HashSet<>();
		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i] != 0 && set.contains(numbers[i]))
				return false;
			else
				set.add(numbers[i]);
		}
		return true;
	}

	/**
	 * @param point the point
	 * @param does the method throw an exception if the return value is false?
	 * @return does the point exist?
	 */
	private boolean doesThePointExist(Point point, boolean throwException) {
		if (point.getX() >= 0 && point.getX() < SIZE &&
			point.getY() >= 0 && point.getY() < SIZE) {
			return true;
		}
		if (throwException) {
			throw new PositionNotExistsException(point);
		}
		return false;
	}
}
