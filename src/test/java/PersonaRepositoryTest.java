
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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.type.TypeReference;

import es.cnc.Application;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.dao.spring.PersonaRepository;
import es.cnc.suscripciones.dto.FilterBaseDTO;
import es.cnc.suscripciones.dto.FilterHolder;
import es.cnc.suscripciones.dto.JsonToFilter;
import es.cnc.suscripciones.services.persona.PersonaService;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class PersonaRepositoryTest {
	Logger logger;
	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private PersonaService personaService;

	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

//	@Test
	public void testPersonaRepository() throws Exception {
		String json = "[{\"property\":\"filter\",\"value\":true},{\"property\":\"nif\",\"value\":\"2\"}]";
		List<FilterBaseDTO<?>> list = JsonToFilter.toFilter(json, new TypeReference<List<FilterBaseDTO<?>>>() {});
		
		
		List<Persona> personas = null;
		FilterHolder<? extends Collection<FilterBaseDTO<?>>> filter = new FilterHolder<Collection<FilterBaseDTO<?>>>(list);

//		filter = new FilterHolder<Collection<FilterBaseDTO<?>>>(null);
		Map<String, String> criterios = new HashMap<>();
		criterios.put("nif", "0");
		
		
		personas = personaRepository.findPersonasWithActiveSuscripcionesDomiciliacionBancos();
		assertNotNull(personas);
		assertTrue(personas.size()>0);
	}
	
	@Test
	public void testPersonaService() throws Exception {
		personaService.generateFichaSuscriptores();
	}
	
}