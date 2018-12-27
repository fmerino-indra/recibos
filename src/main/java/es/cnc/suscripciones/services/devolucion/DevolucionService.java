package es.cnc.suscripciones.services.devolucion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.transaction.Transactional;

import es.cnc.suscripciones.front.dto.reports.DevolucionesReportDTO;

public interface DevolucionService {

	public void readRefundXMLJackson(File xmlFile);
	/**
	 * Process the status file of one or serveral direct debt refund 
	 * @param xmlFile -> The file
	 * @throws FileNotFoundException 
	 */
	@Transactional
	void readRefundXMLJAXB(File xmlFile) throws FileNotFoundException;

	@Transactional
	void readRefundXMLJAXB(InputStream is) throws FileNotFoundException;
	
	/**
	 * Service method that makes a record of a direct debt refund.
	 * @param ids -> List of Emision identifiers.
	 * @param reasons -> List of String with reasons of refund
	 */
	@Transactional
	void devolver(List<Integer> ids);
	
	/**
	 * Service method than cancel a direct debt refund. 
	 * @param ids -> List of Emision identifiers.
	 */
	@Transactional
	void anular(List<Integer> ids);
	
	List<DevolucionesReportDTO> generateRefundReport(Integer fromYear, Integer toYear);

}