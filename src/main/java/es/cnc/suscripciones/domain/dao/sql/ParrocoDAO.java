package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Parroco;

@Repository
public class ParrocoDAO extends RegularDAO<Parroco, Integer> {

	public ParrocoDAO() {
		this(null);
	}

	public ParrocoDAO(List<String> fields) {
		super(fields);
		setClazz(Parroco.class);
	}

}
