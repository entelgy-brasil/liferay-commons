package br.com.entelgy.commons.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Type;

import br.com.entelgy.commons.collection.PageableCollection;
import br.com.entelgy.commons.constants.LiferayConstants;
import br.com.entelgy.commons.dao.specification.Specification;
import br.com.entelgy.commons.exception.DAOException;
import br.com.entelgy.commons.exception.NotFoundException;
import br.com.entelgy.commons.reflection.FluentReflection;

@SuppressWarnings("unchecked")
public abstract class GenericDAOImpl <E, I> implements GenericDAO <E> {

    private final Class<E> entityClass;
    private final Class<I> serviceBuilder;
    private final String simpleName;

    private static final String FETCH = "fetch%s";
    private static final String FETCH_BY_UUID_AND_COMPANY_ID = FETCH + "ByUuidAndCompanyId";
    private static final String DYNAMIC_QUERY = "dynamicQuery";
    private static final String DYNAMIC_QUERY_COUNT = DYNAMIC_QUERY + "Count";
    private static final String UUID = "uuid";
    private static final String UUID_REFLECTION = "_uuid";


    public GenericDAOImpl() {
        entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        serviceBuilder = (Class<I>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        simpleName = entityClass.getSimpleName();
    }

    @Override
    public E findById(long id) throws DAOException {

        try {

            E e = (E) FluentReflection.on(serviceBuilder).invoke(String.format(FETCH, simpleName), id);

            notNull(e);

            return e;

        } catch (NoSuchModelException e) {
            throw new NotFoundException(e);

        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public E findByUuid(long companyId, String uuid) throws DAOException {
        try {

            E e = (E) FluentReflection.on(serviceBuilder).invoke(
                    String.format(FETCH_BY_UUID_AND_COMPANY_ID, simpleName), uuid, companyId);

            notNull(e);

            return e;

        } catch (NoSuchModelException e) {
            throw new NotFoundException(e);

        } catch (Exception e) {
            throw new DAOException(e);
        }

    }

    @Override
    public List<E> findByUuid(long companyId, Collection<String> uuids) throws DAOException {
        try {

            DynamicQuery dynamicQuery = this.createQuery(companyId);

            dynamicQuery.add(RestrictionsFactoryUtil.in(UUID, uuids));

            return (List<E>) FluentReflection.on(serviceBuilder).invoke(DYNAMIC_QUERY,DynamicQuery.class, dynamicQuery);

        } catch (Exception e) {
            throw new DAOException(e);
        }

    }


    @Override
    public Map<String, E> findByUuids(long companyId, Collection<String> uuids) throws DAOException {
        try {

            List<E> result = findByUuid(companyId, uuids);

            Map<String, E> map = new HashMap<>();

            for (E e : result) {

                String uuid = (String) FluentReflection.on(e).setField(UUID_REFLECTION, e);

                map.put(uuid, e);

            }

            return map;

        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<E> findAll(long companyId, int start, int end) throws DAOException {
        try {

            DynamicQuery dynamicQuery = this.createQuery(companyId);

            if (start > QueryUtil.ALL_POS && end > QueryUtil.ALL_POS) {
                dynamicQuery.setLimit(start, end);
            }

            return (List<E>) FluentReflection.on(serviceBuilder).invoke(DYNAMIC_QUERY,DynamicQuery.class, dynamicQuery);

        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public long findAllCount(long companyId) throws DAOException {
        try {

            DynamicQuery dynamicQuery = this.createQuery(companyId);

            return (long) FluentReflection.on(serviceBuilder).invoke(DYNAMIC_QUERY_COUNT, DynamicQuery.class, dynamicQuery);

        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
    
    @Override
    public PageableCollection<E> findPageableBySpecification(long companyId, Specification specification, int start, int end) throws DAOException {
    	
		long count = countBySpecification(companyId, specification);

		if (count <= 0) {
			return new PageableCollection<>(new ArrayList<E>(), 0);
		}

		return new PageableCollection<>(findBySpecification(companyId, specification, start, end), count);
    }
    
	public List<E> findBySpecification(long companyId, Specification specification) throws DAOException {
		return findBySpecification(companyId, specification, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}
    
    public List<E> findBySpecification(long companyId, Specification specification, int start, int end) throws DAOException {
		if (specification == null) {
			throw new NullPointerException();
		}
    	
    	try {
    	
			DynamicQuery dynamicQuery = this.createQuery(companyId);
			specification.prepare(dynamicQuery);

			if (start > QueryUtil.ALL_POS && end > QueryUtil.ALL_POS) {
				dynamicQuery.setLimit(start, end);
			}
	    	
	    	return (List<E>) FluentReflection.on(serviceBuilder).invoke(DYNAMIC_QUERY,DynamicQuery.class, dynamicQuery);
    	
    	} catch (Exception e) {
    		throw new DAOException(e);
    	}
    }
    
    @Override
    public E findOneBySpecification(long companyId, Specification specification) throws DAOException {
		if (specification == null) {
			throw new NullPointerException();
		}
		
		try {
		
	    	DynamicQuery dynamicQuery = this.createQuery(companyId);
	    	specification.prepare(dynamicQuery);
	    	
			List<E> rs = (List<E>) FluentReflection.on(serviceBuilder).invoke(DYNAMIC_QUERY, DynamicQuery.class, dynamicQuery);

			if (rs != null) {

				if (rs.size() == 0) {
					return null;
				} else if (rs.size() == 1) {
					return rs.get(0);
				} else {
					throw new DAOException("Expected: 1 | Returned: " + rs.size());
				}

			}
			
			return null;
			
		} catch (Exception e) {
			throw new DAOException(e);
		}
    	
    }
    
    @Override
    public long countBySpecification(long companyId, Specification specification) throws DAOException {
		if (specification == null) {
			throw new NullPointerException();
		}
    	
    	try {
	
            DynamicQuery dynamicQuery = this.createQuery(companyId);
            specification.prepare(dynamicQuery);
            
            return (long) FluentReflection.on(serviceBuilder).invoke(DYNAMIC_QUERY_COUNT, DynamicQuery.class, dynamicQuery);
            
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
    
    @Override
    public boolean existBySpecification(long companyId, Specification specification) throws DAOException {
    	if (specification == null) {
			throw new NullPointerException();
		}
    	
    	try {
	
            DynamicQuery dynamicQuery = this.createQuery(companyId);
            dynamicQuery.setProjection(ProjectionFactoryUtil.sqlProjection("1 as exist", new String[] { "exist" }, new Type[] { Type.INTEGER }));
            specification.prepare(dynamicQuery);
            
            List<Integer> rs = (List<Integer>) FluentReflection.on(serviceBuilder).invoke(DYNAMIC_QUERY, DynamicQuery.class, dynamicQuery);
            
			return rs != null && !rs.isEmpty() && rs.get(0) == 1;
            
        } catch (Exception e) {
            throw new DAOException(e);
        }
    	
    }

    private void notNull(E e) throws NoSuchModelException {

        if (e == null) {
            throw new NoSuchModelException();
        }

    }

    protected DynamicQuery createQuery(long companyId) {
        DynamicQuery dynamicQuery = (DynamicQuery) FluentReflection.on(serviceBuilder).invoke(DYNAMIC_QUERY);
        dynamicQuery.add(RestrictionsFactoryUtil.eq(LiferayConstants.COMPANY_ID, companyId));
        return dynamicQuery;
    }

}
