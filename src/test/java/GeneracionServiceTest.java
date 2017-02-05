
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

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cnc.Application;
import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.services.generacion.GeneracionService;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class GeneracionServiceTest {
	Logger logger;
	@Autowired
	private GeneracionService generacionService;

	@Autowired
	private CabeceraRepository cabeceraRepository;
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

//	@Test
	@Transactional
	public void calcularFileNameTest() throws Exception {
		Cabeceraemisiones cabecera = null;
		cabecera = cabeceraRepository.findOne(3863);
		assertNotNull(cabecera);
		String aux=generacionService.calcFileName(cabecera);
		System.out.println(aux);
	}
	

	
//	@Test
	public void generarCabecera() throws Exception {
		Cabeceraemisiones cab = null;
		cab = cabeceraRepository.findOne(3863);
		assertNotNull(cab);
		generacionService.generateISO20022(cab);
	}

	
}