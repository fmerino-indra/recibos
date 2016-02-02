package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Secuenciaadeudo;

@Repository
public class SecuenciaadeudoDAO extends RegularDAO<Secuenciaadeudo, Integer> {

	public SecuenciaadeudoDAO() {
		this(null);
	}

	public SecuenciaadeudoDAO(List<String> fields) {
		super(fields);
		setClazz(Secuenciaadeudo.class);
	}

}
