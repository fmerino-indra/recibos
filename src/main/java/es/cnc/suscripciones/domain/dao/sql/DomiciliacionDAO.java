package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Domiciliacion;

@Repository
public class DomiciliacionDAO extends RegularDAO<Domiciliacion, Integer> {

	public DomiciliacionDAO() {
		this(null);
	}

	public DomiciliacionDAO(List<String> fields) {
		super(fields);
	}

}
