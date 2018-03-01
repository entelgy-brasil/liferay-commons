package br.com.entelgy.commons.dao.specification;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;

public abstract class OrSpecification implements Specification {

	private final Map<String, Object> data = new HashMap<>();

	protected void and(String propertie, Object value) {
		this.data.put(propertie, value);
	}

	@Override
	public void prepare(DynamicQuery dynamicQuery) {

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		for (Entry<String, Object> entry : data.entrySet()) {

			Object value = entry.getValue();

			if (value instanceof Collection) {
				disjunction.add(RestrictionsFactoryUtil.in(entry.getKey(), (Collection<?>) value));
			} else {
				disjunction.add(RestrictionsFactoryUtil.eq(entry.getKey(), value));
			}

		}

		dynamicQuery.add(disjunction);
	}

}
