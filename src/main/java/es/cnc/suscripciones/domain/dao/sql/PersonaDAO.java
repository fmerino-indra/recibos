package es.cnc.suscripciones.domain.dao.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Persona;

@Repository
public class PersonaDAO extends RegularDAO<Persona, Integer> {

	public PersonaDAO() {
		this(null);
	}

	public PersonaDAO(List<String> fields) {
		super(fields);
		setClazz(Persona.class);
	}

}
