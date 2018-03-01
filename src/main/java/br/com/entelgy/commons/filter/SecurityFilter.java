package br.com.entelgy.commons.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.PortalUtil;

import br.com.entelgy.commons.filter.xss.XSSActionRequestWrapper;
import br.com.entelgy.commons.filter.xss.XSSRenderRequestWrapper;
import br.com.entelgy.commons.filter.xss.XSSResourceRequestWrapper;

public class SecurityFilter implements ResourceFilter, ActionFilter, RenderFilter {

	private static final String AUTH_PARAM = "p_auth";
	private static final String RESOURCE_LIFERAY_PARAM = "p_p_resource_id";

	private List<String> resourceWhritelist = new ArrayList<>();
	
	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
		resourceWhritelist.add("captcha");
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(RenderRequest request, RenderResponse response, FilterChain chain)
			throws IOException, PortletException {
		chain.doFilter(new XSSRenderRequestWrapper(request), response);
	}

	@Override
	public void doFilter(ActionRequest request, ActionResponse response, FilterChain chain)
			throws IOException, PortletException {
		chain.doFilter(new XSSActionRequestWrapper(request), response);
	}

	@Override
	public void doFilter(ResourceRequest request, ResourceResponse response, FilterChain chain)
			throws IOException, PortletException {

		HttpServletRequest originalHttpServletRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);

		try {
			
			String csrfToken = ParamUtil.getString(originalHttpServletRequest, AUTH_PARAM);

			String csrfSessionToken = AuthTokenUtil.getToken(httpServletRequest);

			if (!isResourceWhritelisted(originalHttpServletRequest)) {

				if (csrfSessionToken == null || csrfToken == null || !csrfSessionToken.equals(csrfToken)) {
					throw new PrincipalException("csrf token invalid.");
				}

			}

			chain.doFilter(new XSSResourceRequestWrapper(request), response);

		} catch (PrincipalException e) {
			unauthorized(response);
		}

	}

	private void unauthorized(ResourceResponse response) {
		response.setProperty(ResourceResponse.HTTP_STATUS_CODE, String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
	}
	
	private boolean isResourceWhritelisted(HttpServletRequest httpServletRequest) {

		String resourceId = ParamUtil.get(httpServletRequest, RESOURCE_LIFERAY_PARAM, StringPool.BLANK);

		if (!resourceId.isEmpty() && resourceWhritelist.contains(resourceId)) {
			return true;
		}
		return false;
	}

}
