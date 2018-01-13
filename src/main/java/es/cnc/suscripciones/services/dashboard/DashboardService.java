package es.cnc.suscripciones.services.dashboard;

import java.time.LocalDate;
import java.util.List;

import es.cnc.suscripciones.front.dto.DashboardDTO;

public interface DashboardService {

	/**
	 * Gets emission statistics summary
	 * @param from
	 * @param to
	 * @return The list of active subscriptions
	 */
	List<DashboardDTO> generateEmissionStatistics(LocalDate from, LocalDate to);
	
}