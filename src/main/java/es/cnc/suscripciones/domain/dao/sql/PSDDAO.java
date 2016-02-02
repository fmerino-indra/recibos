package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.PSD;

@Repository
public class PSDDAO extends RegularDAO<PSD, Integer> {

	public PSDDAO() {
		this(null);
	}

	public PSDDAO(List<String> fields) {
		super(fields);
		setClazz(PSD.class);
	}

}
