package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Sucursal;

@Repository
public class SucursalDAO extends RegularDAO<Sucursal, Integer> {

	public SucursalDAO() {
		this(null);
	}

	public SucursalDAO(List<String> fields) {
		super(fields);
		setClazz(Sucursal.class);
	}

}
