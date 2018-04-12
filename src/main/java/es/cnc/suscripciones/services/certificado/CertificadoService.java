package es.cnc.suscripciones.services.certificado;

import java.util.List;
import java.util.Map;

import es.cnc.suscripciones.domain.ParroquiaHasParroco;
import es.cnc.suscripciones.front.dto.CertificadoDTO;

public interface CertificadoService {

	/**
	 * Find a list of active subscriptions 
	 * @return The list of active subscriptions
	 */
	List<CertificadoDTO> findListForCetificado(Integer idPersona);
	
	/**
	 * Returns the certificado object
	 * @param idPersona
	 * @param idCertificado. The year.
	 * @return
	 */
	CertificadoDTO getCertificado(Integer idPersona, Integer idCertificado);

	ParroquiaHasParroco getParrocoActivo();
	
	void generateCertificates(Integer year);

	Map<String, Object> obtainPHPData(CertificadoDTO dto);
}