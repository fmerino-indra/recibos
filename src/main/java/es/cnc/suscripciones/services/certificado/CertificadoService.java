package es.cnc.suscripciones.services.certificado;

import java.util.List;
import java.util.Map;

import es.cnc.suscripciones.domain.ParroquiaHasParroco;
import es.cnc.suscripciones.front.dto.CertificadoDTO;

public interface CertificadoService {

	/**
	 * Find a list of CertifiacoDTO of a person grouped by year (Emissions & Donativo)
	 * @return The list of CertificadoDTO
	 */
	List<CertificadoDTO> findListForCetificado(Integer idPersona);
	
	/**
	 * Returns the CertificadoDTO object identified as year
	 * @param idPersona
	 * @param idCertificadoYear. The year.
	 * @return
	 */
	CertificadoDTO getCertificado(Integer idPersona, Integer idCertificadoYear);

	ParroquiaHasParroco getParrocoActivo();
	
	void generateCertificates(Integer year);

	Map<String, Object> obtainPHPData(CertificadoDTO dto);
}