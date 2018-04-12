package es.cnc.suscripciones.services.dashboard;

import java.time.LocalDate;
import java.util.List;

import es.cnc.suscripciones.front.dto.reports.DashboardSummaryDTO;

public interface DashboardService {

	/**
	 * Gets emission statistics summary
	 * @param from
	 * @param to
	 * @return The list of active subscriptions
	 */
	List<DashboardSummaryDTO> generateEmissionStatistics(LocalDate from, LocalDate to);
	/**
	 * Gets emission statistics summary
	 * @param from
	 * @param to
	 * @return The list of active subscriptions
	 */
	List<DashboardSummaryDTO> generateEmissionStatistics(LocalDate from, LocalDate to, Boolean mensuales, Boolean trimestrales, Boolean semestrales, Boolean anuales);
	
}