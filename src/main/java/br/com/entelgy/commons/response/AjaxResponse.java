package br.com.entelgy.commons.response;

import br.com.entelgy.commons.util.JSONUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AjaxResponse {

	private ArrayList<String> errors = new ArrayList<>();
	private Object data;

	private AjaxResponse() {
	}

	public static AjaxResponse getInstance() {
		return new AjaxResponse();
	}

	public void addError(String error) {
		this.errors.add(error);
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String toJson() {
		return JSONUtils.toJson(this);
	}

	public Object getData() {
		return data;
	}

	public List<String> getErrors() {
		return errors;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}