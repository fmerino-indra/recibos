package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Bancos;

@Repository
public class BancosDAO extends RegularDAO<Bancos, Integer> {

	public BancosDAO() {
		this(null);
	}
	public BancosDAO(List<String> fields) {
		super(fields);
		setClazz(Bancos.class);
	}
	
}
