package es.cnc.suscripciones.domain;

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
import es.cnc.util.app.HashUtils;

import static org.junit.Assert.assertEquals;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class CabeceraRepositoryTest {
	Logger logger;

	@Autowired
	private CabeceraRepository cabeceraRepository;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Test
//	@Transactional
	public void findXmlByCabeceraTest() throws Exception {
		Cabeceraemisiones ce = null;
		String xmlContent = null;
		String md5Calc = null; 
		ce = cabeceraRepository.findCabeceraByMsgIdFull("R7800543F387520171004080419");
		
		logger.debug("{} - {}", ce.getId(), ce.getFechaEnvio());
		for (SepaCoreXml xml : ce.getSepaCoreXMLs()) {
			if (xml != null) {
				logger.debug("{}-{}-{}-{}", xml.getId(), xml.getIdMsg(), xml.getFechaEnvio(), xml.getHash());
				xmlContent = xml.getXml();
				md5Calc = HashUtils.calcMD5Base64(xmlContent);
				assertEquals(md5Calc, xml.getHash());
			}
		}
	}
}