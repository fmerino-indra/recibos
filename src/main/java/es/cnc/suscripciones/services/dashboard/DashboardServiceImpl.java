package es.cnc.suscripciones.services.dashboard;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.dao.spring.OtrosRepository;
import es.cnc.suscripciones.front.dto.DashboardDTO;

@Component("dashboardService")
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private OtrosRepository otrosRepository;

	Logger logger;

	public DashboardServiceImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public List<DashboardDTO> generateEmissionStatistics(LocalDate from, LocalDate to) {
		List<DashboardDTO> lista = null;
		Integer anyoFrom = null;
		Integer anyoTo = null;
		Integer mesFrom = null;
		Integer mesTo = null;
		
		anyoFrom = from.getYear();
		anyoTo = to.getYear();
		mesFrom = from.getMonthValue();
		mesTo = to.getMonthValue();
		lista = otrosRepository.findEmissionStatisticsSummary(anyoFrom, anyoTo, mesFrom, mesTo);
		return lista;
	}
}
