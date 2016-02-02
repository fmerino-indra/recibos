package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Pais;

@Repository
public class PaisDAO extends RegularDAO<Pais, Integer> {

	public PaisDAO() {
		this(null);
	}

	public PaisDAO(List<String> fields) {
		super(fields);
		setClazz(Pais.class);
	}

}
