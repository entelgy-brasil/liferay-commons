package br.com.entelgy.commons.dao;

import java.util.List;

import br.com.entelgy.commons.collection.PageableCollection;
import br.com.entelgy.commons.dao.specification.Specification;
import br.com.entelgy.commons.exception.DAOException;

public interface SpecificationDAO<E> {

	PageableCollection<E> findPageableBySpecification(long companyId, Specification specification, int start, int end) throws DAOException;

	List<E> findBySpecification(long companyId, Specification specification, int start, int end) throws DAOException;

	List<E> findBySpecification(long companyId, Specification specification) throws DAOException;

	E findOneBySpecification(long companyId, Specification specification) throws DAOException;

	boolean existBySpecification(long companyId, Specification specification) throws DAOException;

	long countBySpecification(long companyId, Specification specification) throws DAOException;

}