package es.cnc.suscripciones.services.emision;

import java.util.List;

import javax.transaction.Transactional;

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

	/**
	 * Generate a list of Cabeceraemisiones and Emision acording with current month pattern.
	 * @return
	 */
	@Transactional
	public List<Cabeceraemisiones> generate();

	/**
	 * Generate a list of Cabeceraemisiones and Emision for the month passed as parameter.
	 * @param month
	 * @return
	 */
	@Transactional
	public List<Cabeceraemisiones> generate(int month);


	/**
	 * Generate a list of Cabeceraemisiones and Emision of the refunded direct debts of the month passed as parameter.
	 * @param month -> Month of the refunded direct debts.
	 * @return
	 */
	@Transactional
	public List<Cabeceraemisiones> generateRefunded(int year, int month);

	/**
	 * Delete a Cabeceraemisiones.
	 * @param ce
	 */
	@Transactional
	void deleteCabecera(Cabeceraemisiones ce);

	/**
	 * Service method that makes a record of a direct debt refund.
	 * @param ids -> List of Emision identifiers.
	 */
	@Transactional
	void devolver(List<Integer> ids);
	
	/**
	 * Service method than cancel a direct debt refund. 
	 * @param ids -> List of Emision identifiers.
	 */
	@Transactional
	void anular(List<Integer> ids);
	
}