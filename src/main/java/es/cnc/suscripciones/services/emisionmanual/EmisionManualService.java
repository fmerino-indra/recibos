package es.cnc.suscripciones.services.emisionmanual;

import java.util.Date;
import java.util.List;

import es.cnc.suscripciones.domain.CabeceraEmisionManual;
import es.cnc.suscripciones.domain.SolicitudEmisionManual;
import es.cnc.suscripciones.front.dto.SolicitudDTO;

public interface EmisionManualService {

	/**
	 * Generate a SolicitudEmisionManual for request passed as parameter.
	 * @param solicitudes
	 * @param concepto
	 * @param fecha
	 * @return
	 */
	SolicitudEmisionManual generateSolicitudManual(List<SolicitudDTO> solicitudes, String concepto, Date fecha);
	
	/**
	 * Generate a CabeceraEmisionManual and EmisionManual list for request passed as parameter.
	 * @param solicitudes
	 * @param concepto
	 * @param fecha
	 * @return
	 */
	List<CabeceraEmisionManual> generateCabeceraEmisionManual(List<SolicitudEmisionManual> solicitudes);
}