package br.com.entelgy.commons.datatable;

import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

public class DatatableRequest {

	private static final int NULL_INT = -1;

	private final PortletRequest portlerRequest;

	private int start = NULL_INT;
	private int lenght = NULL_INT;
	private int end = NULL_INT;
	private Order order;
	private int draw = NULL_INT;
	private String searchValue;

	public DatatableRequest(PortletRequest portlerRequest) {
		this.portlerRequest = portlerRequest;
	}

	public static DatatableRequest getInstance(PortletRequest portletRequest) {
		return new DatatableRequest(portletRequest);
	}

	public String getFilterAsString(String filterName) {
		return ParamUtil.getString(portlerRequest, filterName);
	}

	public String getFilterAsString(String filterName, String defaultValue) {
		return ParamUtil.getString(portlerRequest, filterName, defaultValue);
	}

	public boolean getFilterAsBoolean(String filterName, Boolean defaultValue) {
		return ParamUtil.getBoolean(portlerRequest, filterName, defaultValue);
	}

	public int getFilterAsInteger(String filterName) {
		return ParamUtil.getInteger(portlerRequest, filterName);
	}

	public int getFilterAsInteger(String filterName, Integer defaultValue) {
		return ParamUtil.getInteger(portlerRequest, filterName, defaultValue);
	}

	public Long getFilterAsLong(String filterName) {
		return ParamUtil.getLong(portlerRequest, filterName);
	}

	public Long getFilterAsLong(String filterName, Long defaultValue) {
		return ParamUtil.getLong(portlerRequest, filterName, defaultValue);
	}

	public int getStart() {
		if (start == NULL_INT) {
			start = ParamUtil.getInteger(portlerRequest, "start");
		}
		return start;
	}

	public int getEnd() {
		if (end == NULL_INT) {
			end = getStart() + getLenght();
		}
		return end;
	}

	public int getLenght() {
		if (lenght == NULL_INT) {
			lenght = ParamUtil.getInteger(portlerRequest, "length");
		}
		return lenght;
	}

	public String getSearchValue() {
		if (searchValue == null) {
			searchValue = ParamUtil.getString(portlerRequest, "search[value]");
		}
		return searchValue;
	}

	public int getDraw() {
		if (draw == NULL_INT) {
			draw = ParamUtil.getInteger(portlerRequest, "draw");
		}
		return draw;
	}

	public Order getOrder() {
		if (order == null) {
			order = new Order(ParamUtil.getString(portlerRequest, "order[0][dir]"),
					ParamUtil.getInteger(portlerRequest, "order[0][column]"));
		}
		return order;
	}

	public static class Order {
		private final String type;
		private final int index;

		public Order(String type, int index) {
			this.type = type;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public boolean isAsc() {
			return "asc".equals(type);
		}

		public boolean isDesc() {
			return "desc".equals(type);
		}
	}
}
