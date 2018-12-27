package es.cnc.suscripciones.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.cnc.Application;
import es.cnc.suscripciones.domain.dao.spring.CabeceraEmisionManualRepository;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class CabeceraManualRepositoryTest {
	Logger logger;

	@Autowired
	private CabeceraEmisionManualRepository cabeceraManualRepository;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Test
	public void testCabecera() throws Exception {
		CabeceraEmisionManual cabecera, aux = null;
		aux = new CabeceraEmisionManual();
		aux.setId(7);
		cabecera = cabeceraManualRepository.findCabeceraManualByIdFull(aux, true);
		assertNotNull(cabecera);
	}
	
}