package es.cnc.suscripciones.services;

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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cnc.Application;
import es.cnc.suscripciones.services.devolucion.DevolucionService;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class DevolucionServiceTest {
	Logger logger;
	@Autowired
	private DevolucionService devolucionService;

	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Test
//	@Transactional
	public void testFile() throws Exception {
		File xmlFile = null;//new File("xmlProduccion/2017/devoluciones/10-Octubre/Devolucion_2017-11-04T051519_1.xml");
		String fileName = "Devolucion_2017-11-04T051519_1.xml";
		fileName = "sample.xml";
		Path ficheroPath = null;
		Path camino = null;

		Integer year = 2017;
		
		camino = calcPath(year);
		ficheroPath = Paths.get(camino.toAbsolutePath().toString(), fileName);
//		ficheroPath = Files.createFile(ficheroPath);
		
		devolucionService.readRefundXMLJAXB(ficheroPath.toFile());
	}
	
	private Path calcPath(Integer year) throws RuntimeException {
		Path camino = Paths.get("xmlProduction/", year.toString(), "/devoluciones", "10-Octubre");
		//Files.deleteIfExists(camino);
		//Files.createDirectories(camino);
		if (Files.exists(camino))
			return camino;
		else
			logger.error("[CertificadoServiceImpl] Error al crear el directorio:" + camino.toString());
			throw new RuntimeException("No se encuentra la ruta");
	}
	
	
}