package br.com.entelgy.commons.converter;

import br.com.entelgy.commons.exception.ConverterException;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.springframework.beans.BeanUtils;

public abstract class GenericConverter<I, O> implements Converter<I, O> {

	private final Class<O> targetClass;

	@SuppressWarnings("unchecked")
	public GenericConverter() {

		targetClass = (Class<O>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

	}

	@Override
	public O convert(I input) {

		O objResult = null;
		try {
			objResult = targetClass.newInstance();
			BeanUtils.copyProperties(input, objResult);
		}
		catch (InstantiationException | IllegalAccessException e) {
			throw new ConverterException(e);
		}

		return objResult;
	}

	public List<O> convert(List<I> inputs) {

		return (List<O>) CollectionUtils.collect(inputs, new Transformer<I, O>() {

			@Override
			public O transform(I input) {

				return convert(input);
			}

		});
	}

}
