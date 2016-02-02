
/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cnc.Application;
import es.cnc.suscripciones.domain.Bancos;
import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Meses;
import es.cnc.suscripciones.domain.ParroquiaHasParroco;
import es.cnc.suscripciones.domain.Periodo;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.domain.dao.spring.ParroquiaHasParrocoRepository;
import es.cnc.suscripciones.domain.dao.spring.PeriodoRepository;
import es.cnc.suscripciones.services.bancos.BancosService;
import es.cnc.suscripciones.services.generacion.GeneracionService;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class VariosTests {
	Logger logger;
//	@Autowired
//	private PeriodoDAO dao;
	
	@Autowired
	private BancosService bancosService;

	@Autowired
	private GeneracionService generacionService;

	@Autowired
	private CabeceraRepository cabeceraRepository;
	
	@Autowired
	private ParroquiaHasParrocoRepository parroquiaHasParrocoRepository;
	
	@Autowired
	private PeriodoRepository periodoRepository;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

//	@Test
//	@Transactional
	public void testDao() throws Exception {
		List<Periodo> lista = null; 
//		lista = dao.findAll();
		assertNotNull(lista);
	}

//	@Test
//	@Transactional
	public void testBancos() throws Exception {
		Page<Bancos> lista = bancosService.findBancos(null, (Pageable)null); 
		assertNotNull(lista);
	}
	
//	@Test
	public void testCabeceraEmisiones() throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateInString = "20/01/2009";
		List<Cabeceraemisiones> lista = cabeceraRepository.findCabeceraAndEmisionesByFecha(formatter.parse(dateInString));
		assertNotNull(lista);
		assertTrue(lista.size()>0);
	}
	
	//@Test
	public void testMascara() throws Exception {
		List<Periodo> lista = periodoRepository.findPeriodosByMes(1);
		assertNotNull(lista);
		assertTrue(lista.size()>0);

		Meses mes = new Meses();
		mes.setId(1);
		lista = periodoRepository.findPeriodosByMes(mes);
		assertNotNull(lista);
		assertTrue(lista.size()>0);

		lista = generacionService.findMascara();
		assertNotNull(lista);
		assertTrue(lista.size()>0);
	}
	
//	@Test
	public void testCabecera() throws Exception {
		Cabeceraemisiones cabecera = null;
		cabecera = new Cabeceraemisiones();
		cabecera.setId(185);
		
		cabecera = cabeceraRepository.findEmisionesByCabecera(cabecera);
		assertNotNull(cabecera);
		
		ParroquiaHasParroco php = null;
		php = parroquiaHasParrocoRepository.findActiveParroquiaHasParroco();
		assertNotNull(php);
	}
	
	@Test
	public void testGeneracion() throws Exception {
		Cabeceraemisiones cabecera = null;
		cabecera = new Cabeceraemisiones();
		cabecera.setId(3785);
		
		generacionService.generateISO20022(cabecera);
	}
}