package br.com.entelgy.commons.dao;

import br.com.entelgy.commons.exception.DAOException;

import java.util.Date;

/**
 * Created by andre on 06/01/17.
 */
public interface PersistGenericDAO<E> extends GenericDAO<E> {

    E create(Date now, long companyId) throws DAOException;

    E save(E entity) throws DAOException;

    E update(E entity) throws DAOException;

    E saveOrUpdate(E entity) throws DAOException;
}
