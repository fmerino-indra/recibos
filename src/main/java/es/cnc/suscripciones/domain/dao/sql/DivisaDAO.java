package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Divisa;

@Repository
public class DivisaDAO extends RegularDAO<Divisa, String> {

	public DivisaDAO() {
		this(null);
	}

	public DivisaDAO(List<String> fields) {
		super(fields);
	}

}
