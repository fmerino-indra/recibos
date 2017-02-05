package es.cnc.suscripciones.services.generacion;

import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Periodo;

public interface GeneracionService {

	/**
	 * Devuelve los períodos que hay que emitir / generar en el mes actual
	 * @return Lista de períodos
	 */
	List<Periodo> findMascara();

	String generateISO20022(Cabeceraemisiones cabecera) throws DatatypeConfigurationException;
	String calcFileName(Cabeceraemisiones cabecera);

}