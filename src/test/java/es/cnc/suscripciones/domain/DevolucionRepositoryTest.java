package es.cnc.suscripciones.domain;

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
import es.cnc.suscripciones.domain.dao.spring.DevolucionRepository;
import es.cnc.suscripciones.domain.dao.spring.SepaCoreXmlRepository;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class DevolucionRepositoryTest {
	Logger logger;

	@Autowired
	private DevolucionRepository devolucionRepository;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Test
//	@Transactional
	public void findXmlByCabeceraTest() throws Exception {
		Devoluciones devolucion = null;
		Emision emision = new Emision();
		emision.setId(253065);
		devolucion = devolucionRepository.findActiveReturnedByEmision(emision);
		if (devolucion != null) {
			logger.debug("{}-{}-{}-{}-{}", devolucion.getId(), devolucion.getFechaDevolucion(), devolucion.getIdMsgDevolucion(), devolucion.getReason());
		}
	}
}