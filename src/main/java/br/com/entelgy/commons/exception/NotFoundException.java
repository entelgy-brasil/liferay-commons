package br.com.entelgy.commons.exception;

public class NotFoundException extends DAOException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(Throwable cause) {
		super(cause);
	}

}
