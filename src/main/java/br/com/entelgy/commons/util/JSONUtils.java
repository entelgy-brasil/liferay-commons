package br.com.entelgy.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JSONUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private JSONUtils(){
	}
	
	static {
		OBJECT_MAPPER.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
		OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
		OBJECT_MAPPER.setSerializationInclusion(Include.NON_EMPTY);
	}

	public static String toJson(Object content) {
		try {
			return OBJECT_MAPPER.writeValueAsString(content);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, Class<?> type) {
		try {
			return (T) OBJECT_MAPPER.readValue(json, type);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
