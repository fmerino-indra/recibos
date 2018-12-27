package es.cnc.suscripciones.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
import es.cnc.suscripciones.domain.dao.spring.DonativoRepository;
import es.cnc.suscripciones.front.dto.CertificadoDTO;
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
	
	
//	@Test
	public void certificateDTOTest() throws Exception {
		List<CertificadoDTO> lista;
		CertificadoDTO dto;
		
		lista = donativoRepository.findDonativoSummaryByYearDTO(2017);
		assertNotNull(lista);
		assertFalse(lista.isEmpty());
		dto = donativoRepository.findDonativoSummaryByNifAndYearDTO("01173787M",2017);
		assertNotNull(dto);
	}
	@Test
//	@Transactional
	public void insertDonativoTest() throws Exception { 
		Persona p = null;
		Donativo donativo;
		// PEDRO MANUEL MARTINEZ MARTINEZ
//		p = personaService.findPersonaById(5265);
//		donativo = new Donativo();
//		donativo.setDivisa("Eur");
//		donativo.setFechaDonativo(LocalDateUtil.parseStringDateShort("14/03/2016"));
//		donativo.setImporte(2000d);
//		donativo.setIdPersona(p);
//		donativo = donativoRepository.save(donativo);
//		assertNotNull(donativo);
//		logger.debug("{}", donativo.toString());

//		p = personaService.findPersonaById(5265);
//		donativo = new Donativo();
//		donativo.setDivisa("Eur");
//		donativo.setFechaDonativo(LocalDateUtil.parseStringDateShort("08/03/2017"));
//		donativo.setImporte(2000d);
//		donativo.setIdPersona(p);
//		donativo = donativoRepository.save(donativo);
//		assertNotNull(donativo);
//		logger.debug("{}", donativo.toString());
		
// PILAR EUGENIA OLMO SARMIENTO
//		p = personaService.findPersonaById(5329);
//		donativo = new Donativo();
//		donativo.setDivisa("Eur");
//		donativo.setFechaDonativo(LocalDateUtil.parseStringDateShort("14/03/2016"));
//		donativo.setImporte(600d);
//		donativo.setIdPersona(p);
//		donativo = donativoRepository.save(donativo);
//		assertNotNull(donativo);
//		logger.debug("{}", donativo.toString());

//		p = personaService.findPersonaById(5329);
//		donativo = new Donativo();
//		donativo.setDivisa("Eur");
//		donativo.setFechaDonativo(LocalDateUtil.parseStringDateShort("08/03/2017"));
//		donativo.setImporte(600d);
//		donativo.setIdPersona(p);
//		donativo = donativoRepository.save(donativo);
//		assertNotNull(donativo);
//		logger.debug("{}", donativo.toString());
		
		
		// MARGARITA RUIZ SOLAS
//		p = personaService.findPersonaById(5206);
//		donativo = new Donativo();
//		donativo.setDivisa("Eur");
//		donativo.setFechaDonativo(LocalDateUtil.parseStringDateShort("28/12/2017"));
//		donativo.setImporte(200d);
//		donativo.setIdPersona(p);
//		donativo = donativoRepository.save(donativo);
//		assertNotNull(donativo);
//		logger.debug("{}", donativo.toString());

// A ELIMINAR DE LA BASE DE DATOS		
//		p = personaService.findPersonaById(5206);
//		donativo = new Donativo();
//		donativo.setDivisa("Eur");
//		donativo.setFechaDonativo(LocalDateUtil.parseStringDateShort("28/12/2017"));
//		donativo.setImporte(200d);
//		donativo.setIdPersona(p);
//		donativo = donativoRepository.save(donativo);
//		assertNotNull(donativo);
//		logger.debug("{}", donativo.toString());
		
		
		// MARTIN MARIN, MARIA TERESA
//		p = new Persona();
//		p.setNif("1755729R");
//		p.setNombre("Martín Marín, María Teresa");
//		p.setPoblacion("Madrid");
//		p = personaService.createPersona(p);
//		assertNotNull(p);
//
//		donativo = new Donativo();
//		donativo.setDivisa("Eur");
//		donativo.setFechaDonativo(LocalDateUtil.parseStringDateShort("31/12/2017"));
//		donativo.setImporte(200d);
//		donativo.setIdPersona(p);
//		donativo = donativoRepository.save(donativo);
//		assertNotNull(donativo);
//		logger.debug("{}", donativo.toString());

		// TARN MARTIN, ANDRES
//		p = new Persona();
//		p.setNif("50873043X");
//		p.setNombre("Tarn Martín, Andrés");
//		p.setPoblacion("Madrid");
//		p = personaService.createPersona(p);
//		assertNotNull(p);
//
//		donativo = new Donativo();
//		donativo.setDivisa("Eur");
//		donativo.setFechaDonativo(LocalDateUtil.parseStringDateShort("31/12/2017"));
//		donativo.setImporte(200d);
//		donativo.setIdPersona(p);
//		donativo = donativoRepository.save(donativo);
//		assertNotNull(donativo);
//		logger.debug("{}", donativo.toString());
		
	}
}