package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Cabeceraemisiones;

@Repository
public class CabeceraemisionesDAO extends RegularDAO<Cabeceraemisiones, Integer> {

	public CabeceraemisionesDAO(List<String> fields) {
		super(fields);
	}

	public CabeceraemisionesDAO() {
		this(null);
	}
}
