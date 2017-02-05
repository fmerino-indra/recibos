package es.cnc.suscripciones.services.resumen;

import java.util.List;

import es.cnc.suscripciones.front.dto.ResumenDTO;

public interface ResumenService {
	List<ResumenDTO> getResumen(Integer anyo);
}
