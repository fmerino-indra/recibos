package es.cnc.suscripciones.domain;

import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cnc.Application;
import es.cnc.suscripciones.domain.dao.spring.DonativoRepository;
import es.cnc.suscripciones.services.persona.PersonaService;
import es.cnc.util.LocalDateUtil;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class DonativoRepositoryTest {
	Logger logger;

	@Autowired
	private DonativoRepository donativoRepository;
	@Autowired
	private PersonaService personaService;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Test
	@Transactional
	public void insertDonativoTest() throws Exception { 
		Persona p = null;
		// PEDRO MANUEL MARTINEZ MARTINEZ
		p = personaService.findPersonaById(5265);
		Donativo donativo;
		donativo = new Donativo();
		donativo.setDivisa("Eur");
		donativo.setFechaDonativo(LocalDateUtil.parseStringDateShort("14/03/2016"));
		donativo.setImporte(2000d);
		donativo.setIdPersona(p);
		donativo = donativoRepository.save(donativo);
		assertNotNull(donativo);
		logger.debug("{}", donativo.toString());
	}
}