package main.java.com.serpest.sudokuresolver.entities.exceptions;

import main.java.com.serpest.sudokuresolver.entities.Point;

public class PositionNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 816326998914811391L;



	public PositionNotExistsException(Point point) {
		super("The position " + point + " doesn't exist.");
	}

}