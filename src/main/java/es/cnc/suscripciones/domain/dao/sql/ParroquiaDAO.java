package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Parroquia;

@Repository
public class ParroquiaDAO extends RegularDAO<Parroquia, Integer> {

	public ParroquiaDAO() {
		this(null);
	}

	public ParroquiaDAO(List<String> fields) {
		super(fields);
		setClazz(Parroquia.class);
	}

}
