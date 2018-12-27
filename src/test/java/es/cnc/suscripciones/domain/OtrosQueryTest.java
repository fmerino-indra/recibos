package es.cnc.suscripciones.domain;

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
import es.cnc.suscripciones.domain.dao.spring.OtrosRepository;
import es.cnc.suscripciones.domain.dao.spring.PersonaRepository;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class OtrosQueryTest {
	Logger logger;
	
	@Autowired
	private OtrosRepository otrosRepository;

	@Autowired
	private PersonaRepository personaRepository;

	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testOtros() throws Exception {
//		PageRequest pr = new PageRequest(page, limit);
		Persona aux=null;
		aux = personaRepository.findByPrimaryKey(5132);
		

		List list = otrosRepository.findEmissionSummaryByNifDTO(aux.getNif());
		assertNotNull(list);
		assertTrue(list.size()>0);
		for (Object cert: list) {
			System.out.println(cert) ;
		}

		List<Persona> listPersonas = otrosRepository.findPersonasWithEmisionsByYear(2016);
		assertNotNull(listPersonas);
		assertTrue(listPersonas.size()>0);
		for (Persona p: listPersonas) {
			System.out.println(otrosRepository.findEmissionSummaryByNifAndYearDTO(p.getNif(), 2016));
		}
	}	
}