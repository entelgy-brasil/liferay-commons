package br.com.entelgy.commons.dao.specification;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;

public interface Specification {
	void prepare(DynamicQuery dynamicQuery);
}