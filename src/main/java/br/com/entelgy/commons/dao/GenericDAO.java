package br.com.entelgy.commons.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.entelgy.commons.exception.DAOException;

public interface GenericDAO <E> extends SpecificationDAO<E>{

    E findById(long id) throws DAOException;

    E findByUuid(long companyId, String uuid) throws DAOException;

    List<E> findByUuid(long companyId, Collection<String> uuids) throws DAOException;

    Map<String, E> findByUuids(long companyId, Collection<String> uuids) throws DAOException;

    List<E> findAll(long companyId, int start, int end) throws DAOException;

    long findAllCount(long companyId) throws DAOException;

}
