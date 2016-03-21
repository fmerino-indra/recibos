package es.cnc.suscripciones.services.emision;

import java.util.List;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Periodo;

public interface EmisionService {

	/**
	 * Returns a list of Periods (Annual, Six-monthly, T Quarter, Monthly) for
	 * the current month.
	 * 
	 * @return
	 */
	List<Periodo> findMascara();

	public List<Cabeceraemisiones> generate();

	public List<Cabeceraemisiones> generate(int mes);

	void deleteCabecera(Cabeceraemisiones ce);

	void devolver(List<Integer> ids);
	
	void anular(List<Integer> ids);
	
}