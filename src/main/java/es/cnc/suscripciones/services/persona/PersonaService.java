package es.cnc.suscripciones.services.persona;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.dto.FilterBaseDTO;
import es.cnc.suscripciones.dto.FilterHolder;

public interface PersonaService {
	public Persona createPersona(String nif, String nombre, String domicilio, String cp, String poblacion, String tfno);
	public Persona createPersona(String nif, String nombre, Persona antecesor);
	public Persona createPersona(Persona p);
	/**
	 * Find a list of active personas
	 * @return The list of active personas
	 */
	public Page<Persona> findPersonas(Integer page, Integer start, Integer limit);
	public Page<Persona> findPersonasWithCriteria(FilterHolder<? extends Collection<FilterBaseDTO<?>>> filter, Integer page, Integer start, Integer limit);
	public List<Persona> findPersonasByNif(String nif);

	/**
	 * Find a Persona by PK with all relations.
	 * @param id
	 * @return The Persona.
	 */
	public Persona findPersonaById(Integer id);
	
	/**
	 * Delete a Persona by PK.
	 * @param id
	 */
	public void deletePersona(Integer id);

	/**
	 * Genera un PDF por cada suscriptor
	 */
	void generateFichaSuscriptores();
}