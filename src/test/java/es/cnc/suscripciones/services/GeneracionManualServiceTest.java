package es.cnc.suscripciones.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.cnc.Application;
import es.cnc.suscripciones.domain.CabeceraEmisionManual;
import es.cnc.suscripciones.domain.SolicitudEmisionManual;
import es.cnc.suscripciones.domain.dao.spring.CabeceraEmisionManualRepository;
import es.cnc.suscripciones.services.generacionmanual.GeneracionManualService;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class GeneracionManualServiceTest {
	Logger logger;

	@Autowired
	private GeneracionManualService generacionManualService;

	@Autowired
	private CabeceraEmisionManualRepository cabeceraManualRepository;
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Test
//	@Transactional
	public void generateXMLManual() throws Exception {
		CabeceraEmisionManual cabecera;
		cabecera = new CabeceraEmisionManual();
		cabecera.setId(7);
		cabecera = cabeceraManualRepository.findCabeceraManualByIdFull(cabecera, true);
		String response;
		response = generacionManualService.generateISO20022(cabecera);
	}
}