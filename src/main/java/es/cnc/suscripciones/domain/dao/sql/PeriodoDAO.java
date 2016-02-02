package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Periodo;

@Repository
public class PeriodoDAO extends RegularDAO<Periodo, String> {

	public PeriodoDAO() {
		this(null);
	}
	public PeriodoDAO(List<String> fields) {
		super(fields);
		setClazz(Periodo.class);
	}

}
