package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Emision;

@Repository
public class EmisionDAO extends RegularDAO<Emision, Integer> {

	public EmisionDAO(List<String> fields) {
		super(fields);
	}

	public EmisionDAO() {
		this(null);
	}

}
