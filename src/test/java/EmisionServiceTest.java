
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.services.cabecera.CabeceraService;
import es.cnc.suscripciones.services.emision.EmisionService;
import es.cnc.suscripciones.services.generacion.GeneracionService;
import es.cnc.util.LocalDateUtil;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class EmisionServiceTest {
	Logger logger;
	@Autowired
	private GeneracionService generacionService;

	@Autowired
	private EmisionService emisionService;

	@Autowired
	private CabeceraService cabeceraService;

	@Autowired
	private CabeceraRepository cabeceraRepository;
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

//	@Test
//	@Transactional
	public void emitirYGenerar() throws Exception {
		List<Cabeceraemisiones> cabeceras = null;
		cabeceras = emisionService.generate(2016, 8);
		assertNotNull(cabeceras);
		assertTrue(cabeceras.size()>0);
		for (Cabeceraemisiones cabecera: cabeceras) {
			System.out.println(cabecera.getId() + "-" + cabecera.getPeriodo() +  "-" + cabecera.getCodigoMes() + "-" + cabecera.getConcepto()+"-"+cabecera.getFechaEmision());
		}
		for (Cabeceraemisiones cabecera: cabeceras) {
			generacionService.generateISO20022(cabecera);
		}
	}
	
	
//	@Test
	public void generar() throws Exception {
		Cabeceraemisiones cabecera = null;
		cabecera = cabeceraRepository.findOne(3854);
		assertNotNull(cabecera);
		System.out.println(cabecera.getId() + "-" + cabecera.getPeriodo() +  "-" + cabecera.getCodigoMes() + "-" + cabecera.getConcepto()+"-"+cabecera.getFechaEmision());
		generacionService.generateISO20022(cabecera);
	}
	
//	@Test
	@Transactional
	public void preEmitirYGenerar() throws Exception {
		List<Cabeceraemisiones> cabeceras = null;
		LocalDate today = LocalDate.now();

		cabeceras = emisionService.preGenerate(today.getYear(), today.getMonthValue());
		assertNotNull(cabeceras);
		assertTrue(cabeceras.size()>0);
		for (Cabeceraemisiones cabecera: cabeceras) {
			assertNotNull(cabecera);
		}
	}
	
//	@Test
//	public void generarCabecera() throws Exception {
//		Cabeceraemisiones cab = null;
//		cab = cabeceraRepository.findOne(3805);
//		generacionService.generateISO20022(cab);
//	}
	
//	@Test
	@Transactional
	public void testRefunded() throws Exception {
		List<Cabeceraemisiones> cabeceras = null;
		LocalDateTime from = null;
		LocalDateTime to = null;
		
		from = LocalDateUtil.fromInitialYearMonth(2016, 2);
		to = LocalDateUtil.fromLastYearMonth(2016, 2);
		
		
		cabeceras = cabeceraRepository.findRefundedCabeceraBetweenDatesFull(
				LocalDateUtil.localDateTimeToDate(from),
				LocalDateUtil.localDateTimeToDate(to));
		
		assertNotNull(cabeceras);
		assertTrue(cabeceras.size()>0);
		for (Cabeceraemisiones cabecera : cabeceras) {
			System.out.println(cabecera.getId() + "-" + cabecera.getCodigoMes() + "-" + cabecera.getFechaEmision());
			for (Emision emision : cabecera.getEmisions()) {
				System.out.println("    +" + emision.getId() + "-" + emision.getImporte() + "-" + emision.getIdSuscripcion().getIdSuscripcion().getPersona().getNombre() + "-" + emision.getIdSuscripcion().getIdSuscripcion().getPersona().getNif());
			}
			
		}
		
		cabeceras = emisionService.generateRefunded(2016, 2);
		assertNotNull(cabeceras);
		assertTrue(cabeceras.size()>0);
		for (Cabeceraemisiones cabecera: cabeceras) {
			generacionService.generateISO20022(cabecera);
		}
		
	}
	
	@Test
	public void deleteCabeceras2018() throws Exception {
		List<Cabeceraemisiones> cabeceras = null;
		cabeceras = cabeceraService.findCabecerasByYear(2018);
		assertNotNull(cabeceras);
		assertTrue(!cabeceras.isEmpty());
		
		for (Cabeceraemisiones ce : cabeceras) {
			emisionService.deleteCabecera(ce);
		}
		cabeceras = cabeceraService.findCabecerasByYear(2018);
		assertNotNull(cabeceras);
		assertTrue(cabeceras.isEmpty());
	}
	
	
//	@Test
	@Transactional
	public void eliminar() throws Exception {
		Cabeceraemisiones cabecera = null;

		cabecera = cabeceraRepository.findOne(3865);
		assertNotNull(cabecera);
		emisionService.deleteCabecera(cabecera);
		cabecera = cabeceraRepository.findOne(3865);
		assertNull(cabecera);
		
		cabecera = cabeceraRepository.findOne(3866);
		assertNotNull(cabecera);
		emisionService.deleteCabecera(cabecera);
		cabecera = cabeceraRepository.findOne(3866);
		assertNull(cabecera);
		
		cabecera = cabeceraRepository.findOne(3863);
		assertNotNull(cabecera);
		emisionService.deleteCabecera(cabecera);
		cabecera = cabeceraRepository.findOne(3863);
		assertNull(cabecera);
		
		cabecera = cabeceraRepository.findOne(3864);
		assertNotNull(cabecera);
		emisionService.deleteCabecera(cabecera);
		cabecera = cabeceraRepository.findOne(3864);
		assertNull(cabecera);
		
		
		
		
//		cabecera = cabeceraRepository.findOne(3808);
//		assertNotNull(cabecera);
//		emisionService.deleteCabecera(cabecera);
//		cabecera = cabeceraRepository.findOne(3808);
//		assertNull(cabecera);
//
//		cabecera = cabeceraRepository.findOne(3809);
//		assertNotNull(cabecera);
//		emisionService.deleteCabecera(cabecera);
//		cabecera = cabeceraRepository.findOne(3809);
//		assertNull(cabecera);
//
//		cabecera = cabeceraRepository.findOne(3810);
//		assertNotNull(cabecera);
//		emisionService.deleteCabecera(cabecera);
//		cabecera = cabeceraRepository.findOne(3809);
//		assertNull(cabecera);
		
		
	}
	
}