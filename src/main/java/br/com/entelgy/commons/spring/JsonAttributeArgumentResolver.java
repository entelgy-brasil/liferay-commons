package br.com.entelgy.commons.spring;

import br.com.entelgy.commons.util.JSONUtils;

import com.liferay.portal.util.PortalUtil;

import java.nio.charset.Charset;

import javax.portlet.PortletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public class JsonAttributeArgumentResolver implements WebArgumentResolver {

	private static final Charset UTF8 = Charset.forName("UTF-8");

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {

		if (!methodParameter.hasParameterAnnotation(JsonAttribute.class)) {
			return UNRESOLVED;
		}

		PortletRequest portletRequest = webRequest.getNativeRequest(PortletRequest.class);

		String json = StreamUtils.copyToString(PortalUtil.getHttpServletRequest(portletRequest).getInputStream(), UTF8);

		if (StringUtils.isNotBlank(json)) {

			return JSONUtils.fromJson(json, methodParameter.getParameterType());

		} else {

			return methodParameter.getParameterType().newInstance();

		}
	}

}
