package es.cnc.suscripciones.domain.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import es.cnc.suscripciones.domain.AbstractEntity;

public interface IDAO<T extends AbstractEntity<I>, I extends Serializable> {

	void setClazz(Class<T> clazz);

	EntityManager entityManager();

	T findById(I id);

	List<T> findAll();

	List<T> findAll(String sortFieldName, String sortOrder);

	List<T> findEntries(int firstResult, int maxResults);

	List<T> findEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder);

	long count();

	void persist();

	void flush();

	void clear();

	void remove(T entity);

	T merge(T entity);

}