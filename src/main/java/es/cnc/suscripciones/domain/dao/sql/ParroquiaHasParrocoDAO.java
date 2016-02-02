package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.ParroquiaHasParroco;

@Repository
public class ParroquiaHasParrocoDAO extends RegularDAO<ParroquiaHasParroco, Integer> {

	public ParroquiaHasParrocoDAO() {
		this(null);
	}

	public ParroquiaHasParrocoDAO(List<String> fields) {
		super(fields);
		setClazz(ParroquiaHasParroco.class);
	}

}
