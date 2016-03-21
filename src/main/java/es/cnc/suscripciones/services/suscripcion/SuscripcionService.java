package es.cnc.suscripciones.services.suscripcion;

import org.springframework.data.domain.Page;

import es.cnc.suscripciones.domain.Suscripcion;

public interface SuscripcionService {

	/**
	 * Find a list of active subscriptions 
	 * @return The list of active subscriptions
	 */
	Page<Suscripcion> findSuscripcionesActivas(Integer page, Integer start, Integer limit);

	/**
	 * Find a Suscripcion by PK with PSD set.
	 * @param id
	 * @return The Suscripcion.
	 */
	Suscripcion findSuscripcionById(Integer id);
}