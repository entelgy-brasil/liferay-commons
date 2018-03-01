package br.com.entelgy.commons.filter;

import br.com.entelgy.commons.filter.xss.XSSActionRequestWrapper;
import br.com.entelgy.commons.filter.xss.XSSRenderRequestWrapper;
import br.com.entelgy.commons.filter.xss.XSSResourceRequestWrapper;

import java.io.IOException;

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

public class XSSFilter implements RenderFilter, ResourceFilter, ActionFilter {

	@Override
	public void init(FilterConfig arg0) throws PortletException {
		// Auto-generated method stub
	}

	@Override
	public void destroy() {
		// Auto-generated method stub
	}

	@Override
	public void doFilter(ActionRequest request, ActionResponse response, FilterChain chain)
	        throws IOException, PortletException {

		chain.doFilter(new XSSActionRequestWrapper(request), response);

	}

	@Override
	public void doFilter(ResourceRequest request, ResourceResponse response, FilterChain chain)
	        throws IOException, PortletException {

		chain.doFilter(new XSSResourceRequestWrapper(request), response);

	}

	@Override
	public void doFilter(RenderRequest request, RenderResponse response, FilterChain chain)
	        throws IOException, PortletException {

		chain.doFilter(new XSSRenderRequestWrapper(request), response);

	}

}
