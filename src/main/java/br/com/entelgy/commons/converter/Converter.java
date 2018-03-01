package br.com.entelgy.commons.converter;

public interface Converter<I, O> {

	public O convert(I input);

}
