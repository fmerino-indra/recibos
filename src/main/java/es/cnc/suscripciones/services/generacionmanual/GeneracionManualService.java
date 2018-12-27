package es.cnc.suscripciones.services.generacionmanual;

import javax.xml.datatype.DatatypeConfigurationException;

import es.cnc.suscripciones.domain.CabeceraEmisionManual;

public interface GeneracionManualService {

	String generateISO20022(CabeceraEmisionManual cabecera) throws DatatypeConfigurationException;
	String calcFileName(CabeceraEmisionManual cabecera);

}