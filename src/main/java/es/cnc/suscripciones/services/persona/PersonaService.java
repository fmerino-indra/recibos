package es.cnc.suscripciones.services.persona;

import org.springframework.data.domain.Page;

import es.cnc.suscripciones.domain.Persona;

public interface PersonaService {
	public Persona createPersona(String nif, String nombre, String domicilio, String cp, String poblacion, String tfno);
	/**
	 * Find a list of active personas
	 * @return The list of active personas
	 */
	public Page<Persona> findPersonas(Integer page, Integer start, Integer limit);

	/**
	 * Find a Persona by PK with all relations.
	 * @param id
	 * @return The Persona.
	 */
	public Persona findPersonaById(Integer id);
}