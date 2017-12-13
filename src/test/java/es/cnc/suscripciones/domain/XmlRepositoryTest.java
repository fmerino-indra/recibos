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
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.domain.dao.spring.SepaCoreXmlRepository;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class XmlRepositoryTest {
	Logger logger;

	@Autowired
	private CabeceraRepository cabeceraRepository;
	
	@Autowired
	private SepaCoreXmlRepository sepaCoreXmlRepository;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Test
//	@Transactional
	public void findXmlByCabeceraTest() throws Exception {
		Cabeceraemisiones ce = null;
		List<SepaCoreXml> xmls = null;
		
		ce = cabeceraRepository.findOne(3876);
		xmls = sepaCoreXmlRepository.findXmlByCabecera(ce);
		
		for (SepaCoreXml xml : xmls) {
			
			logger.debug("{}-{}-{}-{}-{}", xml.getId(), ce.getId(), xml.getIdMsg(), xml.getFechaEnvio(), xml.getHash());
		}
	}
}