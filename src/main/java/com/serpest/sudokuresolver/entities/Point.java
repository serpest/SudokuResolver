package main.java.com.serpest.sudokuresolver.entities;

/**
 * It's a simple point P(x, y).
 */
public class Point {

	/**
	 * It's the x coordinate.
	 */
	private int x;

	/**
	 * It's the y coordinate.
	 */
	private int y;



	/**
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Point(int x, int y) {
		setX(x);
		setY(y);
	}



	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}



	private void setX(int x) {
		this.x = x;
	}

	private void setY(int y) {
		this.y = y;
	}

}