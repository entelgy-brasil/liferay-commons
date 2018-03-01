package br.com.entelgy.commons.filter;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.ResourceFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.PortalUtil;

public class CSRFFilter implements ResourceFilter {

	private static final String AUTH_PARAM = "p_auth";
	
	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
	}

	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ResourceRequest request, ResourceResponse response, FilterChain chain) throws IOException, PortletException {
		
		HttpServletRequest originalHttpServletRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));

		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);
		
		try {

			String csrfToken = ParamUtil.getString(originalHttpServletRequest, AUTH_PARAM);

			String csrfSessionToken = AuthTokenUtil.getToken(httpServletRequest);

			if (csrfSessionToken == null || csrfToken == null || !csrfSessionToken.equals(csrfToken)) {
				throw new PrincipalException("csrf token invalid.");
			}

			chain.doFilter(request, response);

		} catch (PrincipalException e) {
			response.setProperty(ResourceResponse.HTTP_STATUS_CODE,
					String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
		}
		
	}
	
}
