package br.com.entelgy.commons.filter.xss;

import javax.portlet.ActionRequest;
import javax.portlet.filter.ActionRequestWrapper;

import br.com.entelgy.commons.util.XSSUtil;

public class XSSActionRequestWrapper extends ActionRequestWrapper {

	public XSSActionRequestWrapper(ActionRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {

		String value = super.getParameter(name);
		
		return (value == null) ? value : XSSUtil.stripXSS(value);

	}

	@Override
	public String[] getParameterValues(String name) {

		String[] values = super.getParameterValues(name);

		if (values == null) {
			return values;
		}

		int count = values.length;

		String[] encodedValues = new String[count];

		for (int i = 0; i < count; i++) {
			encodedValues[i] = XSSUtil.stripXSS(values[i]);
		}

		return encodedValues;
	}

}
