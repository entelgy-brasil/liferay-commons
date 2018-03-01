package br.com.entelgy.commons.exception;

public class ConverterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConverterException(ReflectiveOperationException e) {

		super(e);
	}

}
