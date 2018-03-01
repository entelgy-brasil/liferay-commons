package br.com.entelgy.commons.filter.xss;

import javax.portlet.RenderRequest;
import javax.portlet.filter.RenderRequestWrapper;

import br.com.entelgy.commons.util.XSSUtil;

public class XSSRenderRequestWrapper extends RenderRequestWrapper {

	public XSSRenderRequestWrapper(RenderRequest request) {
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
