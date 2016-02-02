package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.ParroquiaAux;

@Repository
public class ParroquiaAuxDAO extends RegularDAO<ParroquiaAux, Integer> {

	public ParroquiaAuxDAO() {
		this(null);
	}

	public ParroquiaAuxDAO(List<String> fields) {
		super(fields);
		setClazz(ParroquiaAux.class);
	}

}
