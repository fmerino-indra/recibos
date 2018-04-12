package es.cnc.suscripciones.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cnc.Application;
import es.cnc.suscripciones.front.dto.reports.DashboardSummaryDTO;
import es.cnc.suscripciones.services.dashboard.DashboardService;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class DashboadServiceTests {
	Logger logger;
	@Autowired
	DashboardService dashboardService;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	
	@Test
	public void testDashboard() throws Exception {
		List<DashboardSummaryDTO> toTest = null;
		LocalDate from = null;
		LocalDate to = null;
		from = LocalDate.of(2017, 1, 1);
		to = LocalDate.of(2017, 12, 31);
		toTest = dashboardService.generateEmissionStatistics(from, to);
		assertNotNull(toTest);
		assertFalse(toTest.isEmpty());
		toTest = dashboardService.generateEmissionStatistics(from, to,false,false,true,false);
		assertNotNull(toTest);
		assertFalse(toTest.isEmpty());
	}
	
}