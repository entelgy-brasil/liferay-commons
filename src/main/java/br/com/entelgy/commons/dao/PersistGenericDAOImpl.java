package br.com.entelgy.commons.dao;

import br.com.entelgy.commons.exception.DAOException;
import br.com.entelgy.commons.reflection.FluentReflection;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;

/**
 * Created by andre on 06/01/17.
 */
public class PersistGenericDAOImpl<E, U> extends GenericDAOImpl<E, U> implements PersistGenericDAO<E> {

    private static final Log LOG = LogFactoryUtil.getLog(PersistGenericDAOImpl.class);

    public static final String ADD_PREFIX = "add";
    public static final String UPDATE_PREFIX = "update";
    private static final String CREATE_PREFIX = "create";
    public static final String IS_NEW = "isNew";
    public static final String SET_MODIFIED_DATE = "setModifiedDate";
    private final Class<E> entityClass;
    private final Class<U> serviceBuilder;

    public PersistGenericDAOImpl() {
        entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        serviceBuilder = (Class<U>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Override
    public E create(Date now, long companyId) throws DAOException {
        try {
            long increment = CounterLocalServiceUtil.increment(entityClass.getSimpleName());
            String createMethod = CREATE_PREFIX + entityClass.getSimpleName();
            return (E) FluentReflection.on(serviceBuilder).invoke(createMethod, increment);
        }catch (Exception e){
            throw new DAOException(e);
        }
    }

    @Override
    public E save(E entity) throws DAOException {
        try{
            String addMethod = ADD_PREFIX + entityClass.getSimpleName();
            Method method = ReflectionUtils.findMethod(serviceBuilder, addMethod, null);
            return (E) method.invoke(serviceBuilder, entity);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public E update(E entity) throws DAOException {
        try{
            String updateMethod = UPDATE_PREFIX + entityClass.getSimpleName();
            Method method = ReflectionUtils.findMethod(serviceBuilder, updateMethod, null);
            return (E) method.invoke(serviceBuilder, entity);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public E saveOrUpdate(E entity) throws DAOException {
        try {
            Method methodSetModifiedDate = ReflectionUtils.findMethod(entityClass, SET_MODIFIED_DATE, null);
            methodSetModifiedDate.invoke(entity,new Date());
        } catch (Exception e) {
            LOG.warn("Entity don't have setModifiedDate method.");
        }

        try{
            Method methodIsNew = ReflectionUtils.findMethod(entityClass, IS_NEW, null);
            boolean isNew = (Boolean) methodIsNew.invoke(entity);
            return (isNew) ? (E) save(entity) : (E) update(entity);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
}
