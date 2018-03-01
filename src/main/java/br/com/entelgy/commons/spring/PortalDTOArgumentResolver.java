package br.com.entelgy.commons.spring;

import javax.portlet.PortletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import br.com.entelgy.commons.liferay.dto.PortalDTO;

public class PortalDTOArgumentResolver implements WebArgumentResolver {
	
	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {

		if (methodParameter.getParameterType() != PortalDTO.class) {
			return UNRESOLVED;
		}

		return PortalDTO.getInstance(webRequest.getNativeRequest(PortletRequest.class));
	}

}
