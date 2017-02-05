package es.cnc.suscripciones.services.cabecera;

import java.util.List;

import org.springframework.data.domain.Page;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Emision;

public interface CabeceraService {

	/**
	 * Returns a list of Cabeceraemisiones in a descendant order
	 * 
	 * @return
	 */
	List<Cabeceraemisiones> findCabecerasDesc();

	/**
	 * Finds Cabeceraemisiones in a descendant order.
	 * 
	 * @return Returns a Page of Cabeceraemisiones
	 */
	Page<Cabeceraemisiones> findCabecerasDesc(Integer page, Integer start, Integer limit);

	/**
	 * Returns a Cabeceraemsiones detail.
	 * @param ce
	 * @return
	 */
	@Deprecated // This is a heavy query
	Cabeceraemisiones findCabeceraDetail(Cabeceraemisiones ce);
	
	/**
	 * Returns a Cabeceraemisiones detail without emissions.
	 * @param ce
	 * @return
	 */
	Cabeceraemisiones findCabeceraDetailSimple(Cabeceraemisiones ce);

	/**
	 * Returns all emissions from a given header
	 * @param ce The Cabeceraemisiones to search emissions.
	 * @return
	 */
	List<Emision> findEmissionList(Cabeceraemisiones ce);
	
	/**
	 * Returns all refunded emissions from a given header
	 * @param ce The Cabeceraemisiones to search emissions.
	 * @return
	 */
	List<Emision> findRefundedEmissionList(Cabeceraemisiones ce);
	
	/**
	 * Returns all cabeceraemisiones from a given year
	 * @param anyo
	 * @return
	 */
	List<Cabeceraemisiones> findCabecerasByYear(Integer anyo);

}