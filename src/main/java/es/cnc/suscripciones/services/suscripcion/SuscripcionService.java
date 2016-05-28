package es.cnc.suscripciones.services.suscripcion;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.Suscripcion;

public interface SuscripcionService {

	/**
	 * Find a list of active subscriptions 
	 * @return The list of active subscriptions
	 */
	Page<Suscripcion> findActiveSuscripciones(Integer page, Integer start, Integer limit);

	/**
	 * Find a list of inactive subscriptions 
	 * @return The list of active subscriptions
	 */
	Page<Suscripcion> findInactiveSuscripciones(Integer page, Integer start, Integer limit);

	/**
	 * Find a Suscripcion by PK with PSD set.
	 * @param id
	 * @return The Suscripcion.
	 */
	Suscripcion findSuscripcionById(Integer id);
	
	/**
	 * Find the active Suscripcion of a Persona.
	 * @param p -> The Persona about whom search.
	 * @return
	 */
	Suscripcion findActiveSuscripcionByPersona(Persona p);
	/**
	 * Update a Suscripcion. The old one must be a well formed Suscripcion.
	 * @param id
	 * @param iban
	 * @param euros
	 * @return
	 */
	@Transactional
	Suscripcion updateSuscripcion(Integer id, String iban, Double euros);
	
	@Transactional
	Suscripcion cancelSuscripcion(Integer id);

	@Transactional
	Suscripcion createSuscripcion(String iban, Double euros, String nombre, String periodo);
}