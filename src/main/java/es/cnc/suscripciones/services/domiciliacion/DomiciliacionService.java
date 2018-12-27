package es.cnc.suscripciones.services.domiciliacion;

import es.cnc.suscripciones.domain.Domiciliacion;

public interface DomiciliacionService {

	/**
	 * Find a Domiciliacion by IBAN
	 * @return The Domiciliacion
	 */
	Domiciliacion findDomiciliacion(String iban);
	
	/**
	 * Creates a Domiciliacion
	 * @param dom
	 * @return the Domiciliacion created
	 */
	Domiciliacion createDomiciliacion(Domiciliacion dom);

}