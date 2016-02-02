package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Suscripcion;

@Repository
public class SuscripcionDAO extends RegularDAO<Suscripcion, Integer> {

	public SuscripcionDAO() {
		this(null);
	}

	public SuscripcionDAO(List<String> fields) {
		super(fields);
		setClazz(Suscripcion.class);
	}

}
