package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Meses;

@Repository
public class MesesDAO extends RegularDAO<Meses, Integer> {

	public MesesDAO() {
		this(null);
	}

	public MesesDAO(List<String> fields) {
		super(fields);
		setClazz(Meses.class);
	}

}
