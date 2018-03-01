package br.com.entelgy.commons.exception;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	public DAOException(Throwable cause) {
		super(cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause, String message) {
		super(message, cause);
	}

}
