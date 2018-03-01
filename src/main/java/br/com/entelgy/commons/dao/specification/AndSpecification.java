package br.com.entelgy.commons.dao.specification;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;

public abstract class AndSpecification implements Specification {

	private final Map<String, Object> data = new HashMap<>();

	protected void and(String propertie, Object value) {
		this.data.put(propertie, value);
	}
	
	@Override
	public void prepare(DynamicQuery dynamicQuery) {
		
		for (Entry<String, Object> entry : data.entrySet()) {

			Object value = entry.getValue();

			if (value instanceof Collection) {
				dynamicQuery.add(RestrictionsFactoryUtil.in(entry.getKey(), (Collection<?>) value));
			} else {
				dynamicQuery.add(RestrictionsFactoryUtil.eq(entry.getKey(), value));
			}

		}
		
	}

}
