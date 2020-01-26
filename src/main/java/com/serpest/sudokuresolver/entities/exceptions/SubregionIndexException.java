package main.java.com.serpest.sudokuresolver.entities.exceptions;

public class SubregionIndexException extends RuntimeException {

	private static final long serialVersionUID = -7084594121734563724L;



	public SubregionIndexException(int first, int last) {
		super("The subregion index must be between " + first + " and " + last + ".");
	}

}
