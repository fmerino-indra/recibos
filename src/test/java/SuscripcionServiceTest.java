
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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.cnc.Application;
import es.cnc.suscripciones.domain.Bancos;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.Sucursal;
import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.suscripciones.services.bancos.BancosService;
import es.cnc.suscripciones.services.persona.PersonaService;
import es.cnc.suscripciones.services.sucursal.SucursalService;
import es.cnc.suscripciones.services.suscripcion.SuscripcionService;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class SuscripcionServiceTest {
	Logger logger;
	@Autowired
	private SuscripcionService suscripcionService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private BancosService bancosService;
	
	@Autowired
	private SucursalService sucursalService;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Test
//	@Transactional
	public void updateSuscripcion() throws Exception {
		Suscripcion s = null;
		// AFUERA CORTES, LUISA
//		s = suscripcionService.findSuscripcionById(6685);
//		assertNotNull(s);
//		s = suscripcionService.cancelSuscripcion(s.getId());
//		assertNotNull(s);
//		assertNotNull(s.getFechaBaja());
//		assertNull(s.getActivePSD());
		
		// ARROYO GONZALEZ, PEDRO
//		s = suscripcionService.findSuscripcionById(6653);
//		assertNotNull(s);
//		s = suscripcionService.cancelSuscripcion(s.getId());
//		assertNotNull(s);
//		assertNotNull(s.getFechaBaja());
//		assertNull(s.getActivePSD());
		
		// BAGUÉS REVUELTA, MARIA DOLORES
//		s = suscripcionService.findSuscripcionById(6560);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES0320381015983004183733", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		// GARCIA VIÑA, JOAQUIN (Mari Carmen)
//		s = suscripcionService.findSuscripcionById(6666);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES1720381015916800017312", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//GUIJARRO PEREZ, FERNANDO
//		s = suscripcionService.findSuscripcionById(6800);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES9400301001320890294271", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());

		//GUZMAN AMO, JOSEFA
//		s = suscripcionService.findSuscripcionById(6695);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES4520381015913002815814", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());

//		s = suscripcionService.findSuscripcionById(6803);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES4520381015913002815814", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());

		//HERNANDEZ ALEGRE, ARACELI
//		s = suscripcionService.findSuscripcionById(6651);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES8201824027230201643045", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());

		//HERNANDEZ PEREZ, JOSE LUIS
//		s = suscripcionService.findSuscripcionById(6936);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES5220381015916000398831", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());

		//HERRERO CIPITRIA, JOSE ANTONIO.
//		s = suscripcionService.findSuscripcionById(6670);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES6920382433003000360995", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());

		//HITA ARROYO, JOSE M?
//		s = suscripcionService.findSuscripcionById(6659);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES7920381015913004090267", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//LLONIS MORLA, MARIA DEL CARMEN
//		s = suscripcionService.findSuscripcionById(6705);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES8701824027200201503228", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//ROSETE LLANO, FCO. JAVIER
//		s = suscripcionService.findSuscripcionById(6665);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES7120381015904500269579", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//SALMADOR ALVAREZ, FRANCISCO FELIPE
//		s = suscripcionService.findSuscripcionById(6949);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES2400301122610004228271", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//SANZ BOIXAREU, PEDRO JOSE
//		s = suscripcionService.findSuscripcionById(6631);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES1900817118510001060315", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//SANZ CORADO, JULIA M?
//		s = suscripcionService.findSuscripcionById(6700);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES9400301122660001963271", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//VALLE SANCHEZ, VICTORIO
//		s = suscripcionService.findSuscripcionById(6618);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES4920382478286000256654", 24d);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//VALVERDE TESORO, JERONIMO ISIDORO
//		s = suscripcionService.findSuscripcionById(6627);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES8520381015963004175274", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
//		s = suscripcionService.findSuscripcionById(6808);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES8520381015963004175274", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//ZURITA ZAZO AURORA
//		s = suscripcionService.findSuscripcionById(6965);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES8620381876693000390105", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//LOPEZ AGUADO, MARIA
//		s = suscripcionService.findSuscripcionById(6714);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES9420381133853001482463", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
//2016/05/24		
		//CALVO GABÁS, JUAN IGNACIO
//		s = suscripcionService.findSuscripcionById(6792);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES7520381015976800009087", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		//GURGES VILLUENDAS, EDUARDO
//		s = suscripcionService.findSuscripcionById(6788);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES4720381133803000013713", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		// BAGUÉS REVUELTA, MARIA DOLORES
//		s = suscripcionService.findSuscripcionById(7000);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES9520381015923003561668", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		// ZARAGOZA DE ALBA, PERFECTO
//		s = suscripcionService.findSuscripcionById(6872);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES5000496102412616139329", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
		
		// CASO NEIRA, ALFREDO
//		s = suscripcionService.findSuscripcionById(6572);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES6800308116110001570271", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());

// 24/06/2016
		// ALBERTO RIVAS GONZALEZ y Mª ISABEL TORRES SÁNCHEZ
//		s = suscripcionService.findSuscripcionById(6662);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES9520381050503001863409", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());

		// CASTELLANO PALACIOS, ANTOLIANO -> JOSEFA MOTTA SANCHEZ
//		s = suscripcionService.findSuscripcionById(6590);
//		assertNotNull(s);
//		s = suscripcionService.changeAccountHolder(s.getId(), "ES6821009281212200446232", null, "00028282S", "Motta Sánchez, Josefa",null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
	
		// Sendino Herrero, José Manuel
//		s = suscripcionService.findSuscripcionById(6976);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES7020381876666000196050", null);		
//		assertNotNull(s);
//		assertNull(s.getFechaBaja());
//		assertNotNull(s.getActivePSD());
	
// 03-08-2016
// MARIA VICTORIA VALERO ARNAZ		
//		s = suscripcionService.findSuscripcionById(6823);
//		assertNotNull(s);
//		s = suscripcionService.cancelSuscripcion(s.getId());
//		assertNotNull(s);
//		assertNotNull(s.getFechaBaja());
//		assertNull(s.getActivePSD());

// JOSE MARIA HITA ARROYO	
//		s = suscripcionService.findSuscripcionById(7008);
//		assertNotNull(s);
//		s = suscripcionService.cancelSuscripcion(s.getId());
//		assertNotNull(s);
//		assertNotNull(s.getFechaBaja());
//		assertNull(s.getActivePSD());
	
// VILLAGRÁ BLANCO, FELISA	
//		s = suscripcionService.findSuscripcionById(6977);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), null, 100d);		
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

// Amo López, Eva	
//		s = suscripcionService.findSuscripcionById(6883);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), null, 50d);		
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

// GALERA RASTROJO, CATALINA	
//		s = suscripcionService.findSuscripcionById(6765);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES3720381015993004154651", 200d);		
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

// GOMEZ FERNANDEZ, TOMAS	
//		s = suscripcionService.findSuscripcionById(6793);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES4500495142122816194983", 40d);		
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

//		s = suscripcionService.findSuscripcionById(7033);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), null, 40d);		
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

// AYALA PEREDA, MARIA ROSA	
//		s = suscripcionService.findSuscripcionById(6744);
//		assertNotNull(s);

//		s = suscripcionService.createSuscripcion("ES0600494679182216466862",30d,"01155157M", "M");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

// 05-08-2017
// SANCHEZ-POBRE LACOSTA, MARIANO	
//		s = suscripcionService.findSuscripcionById(6743);
//		assertNotNull(s);
//
//		s = suscripcionService.updateSuscripcion(s.getId(), "ES3901280064330100049244", null);		
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());
		
		
// GARCIA GARCIA, PILAR	
//		s = suscripcionService.createSuscripcion("ES9401824027230201645836",30d,"46203687E", "M");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());
		
// CARMONA GONZÁLEZ, AUGUSTO	
//		s = suscripcionService.createSuscripcion("ES9614650100921701413658",500d,"50832751Z", "M");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

	
// 05-10-2017
// HERNANDEZ, ARACELI	
//		s = suscripcionService.findSuscripcionById(7005);
//		assertNotNull(s);
//		s = suscripcionService.updateSuscripcion(s.getId(), null, 40d);		
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

// 01-12-2017		
// NIETO JOVER, FERNANDO JAVIER 	
//		s = suscripcionService.createSuscripcion("ES8920381015983004077813",50d,"22865935W", "S");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

		// Mal
// CARMONA GONZALEZ, BEGOÑA	
//		s = suscripcionService.findSuscripcionById(6743);
//		assertNotNull(s);
//      
//		s = suscripcionService.createSuscripcion("ES8500491103262310467333",50d,"50814312K", "M");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

// SANZ CORADO JULIA MARIA	
//		s = suscripcionService.findSuscripcionById(7013);
//		assertNotNull(s);
//
//		s = suscripcionService.updateSuscripcion(s.getId(), null, 50d);		
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());
		
		Persona p = null;
// MARÍA DORINDA RODICIO GÓMEZ
//		p = new Persona();
//		p.setNif("34425302Y");
//		p.setCp("28007");
//		p.setDomicilio("Av. del Mediterráneo, 24");
//		p.setNombre("Rodicio Gómez, María Dorinda");
//		p.setPoblacion("Madrid");
//		p.setTfno("914333236");
//		p = personaService.createPersona(p);
//		assertNotNull(p);
//		
//		s = suscripcionService.createSuscripcion("ES7120381015916001290307", 30d, "34425302Y", "M");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());
		
// MARÍA DEL PILAR CRUZADO DE LA HERA
//		p = new Persona();
//		p.setNif("00975466J");
//		p.setCp("28007");
//		p.setDomicilio("C/ Amado Nervo, 4 - 6º A");
//		p.setNombre("Cruzado de la Hera, María del Pilar");
//		p.setPoblacion("Madrid");
//		p.setTfno("915517990");
//		p.setMovil("627379493");	
//		p = personaService.createPersona(p);
//		assertNotNull(p);
//		
//		s = suscripcionService.createSuscripcion("ES9000301038750002372271", 600d, "00975466J", "A");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

// MARGARITA RUIZ SOLAS
//		s = suscripcionService.findSuscripcionById(6909);
//		assertNotNull(s);
//
//		s = suscripcionService.updateSuscripcion(s.getId(), null, 70d);		
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());
		
		
// BEGOÑA CARMONA GONZALEZ
//		p = personaService.findPersonaById(5277); 
//		assertNotNull(p);
////		p.setCp("28007");
//		p.setDomicilio("C/ Antonio Díaz Cañabate, 8");
//		p.setTfno(null);
//		p.setMovil("607503072");
//		p.setCorreo("begonacar@yahoo.es");
//		p = personaService.updatePersona(p);
//		assertNotNull(p);
//
//		Bancos banco = null;
//		List<Bancos>lista = null;
//		banco = bancosService.findBancosByCode("0049", true);
//		Sucursal suc = new Sucursal();
//		suc.setIdBanco(banco);
//		suc.setCodSuc("1108");
//		suc.setDenSuc("Oficina 1108 BANCO-SANTANDER en MADRID (MADRID) - CRUZ DEL SUR, 4 ");
//		suc = sucursalService.updateSucursal(suc);
//		
//		s = suscripcionService.findSuscripcionById(6715);
//		assertNotNull(s);
//        s= suscripcionService.updateSuscripcion(s.getId(), "ES8500491108262310467333", 50d);
//      
////		s = suscripcionService.createSuscripcion("ES8500491108262310467333",50d,"50814312K", "M");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());
				
// 2018-02-18 - Juan Vicente Herrero Álvarez
//		p = new Persona();
		
//		p.setId(5139);
//		personaService.findPersonaById(5138);
//		
//		assertNotNull(p);
//		
//			
//		p.setNif("27822650X");
//		p.setCp("28025");
//		p.setDomicilio("Av. Nuestra Señora de Valvanera, 106");
//		p.setNombre("Herrero Álvarez, Juan Vicente");
//		p.setPoblacion("Madrid");
//		p.setTfno("912259191");
//		p = personaService.updatePersona(p);
//		assertNotNull(p);
//
//		Bancos banco = null;
//		List<Bancos>lista = null;
//		banco = bancosService.findBancosByCode("0049", true);
//		Sucursal suc = new Sucursal();
//		suc.setIdBanco(banco);
//		suc.setCodSuc("3116");
//		suc.setDenSuc("Oficina 3116 BANCO-SANTANDER en MADRID (MADRID) - Av. Nuestra Señora de Valvanera, 90");
//		suc = sucursalService.updateSucursal(suc);
//		
//		s = suscripcionService.createSuscripcion("ES0900493116412894238211", 30d, "27822650X", "M");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());
		
// 2018-03-16 - Margarita García Sánchez
//				p = new Persona();
//				
//				p.setNif("1805831D");
//				p.setNombre("García Sánchez, Margarita");
//				p.setDomicilio("C/ Juan de Urbieta, 13 -P1-3ºA");
//				p.setCp("28007");
//				p.setPoblacion("Madrid");
//				p.setMovil("639355321");
//				p.setCorreo("margaritamdcgs@gmail.com");
//				p = personaService.createPersona(p);
//				assertNotNull(p);

//				Bancos banco = null;
//				List<Bancos>lista = null;
//				banco = bancosService.findBancosByCode("0049", true);
//				Sucursal suc = new Sucursal();
//				suc.setIdBanco(banco);
//				suc.setCodSuc("5142");
//				suc.setDenSuc("Oficina 5142 BANCO-SANTANDER en MADRID (MADRID) - Av. del Mediterráneo, 30");
//				suc = sucursalService.updateSucursal(suc);
//				
//				s = suscripcionService.createSuscripcion("ES3700495142172110030032", 20d, "1805831D", "M");
//				assertNotNull(s);
//				assertNotNull(s.getActivePSD());
				
// 2018-03-16 - CALVO GABAS, JUAN IGNACIO
//		List<Persona> listaPersonas;
//		
//		listaPersonas = personaService.findPersonasByNif("13558641A");
//		assertNotNull(listaPersonas);
//		
//		if (listaPersonas.isEmpty() || listaPersonas.size() > 1)
//			throw new RuntimeException("Más de una persona para el DNI:" + "13558641A");
//		
//		p=listaPersonas.get(0);
//		p.setDomicilio("Av. del Mediterráneo, 13 - 5ºB");
//		p.setTfno("915526620");
//		p = personaService.updatePersona(p);
//		
//		s = suscripcionService.findActiveSuscripcionByPersona(p);
//		assertNotNull(s);
//      
//		s = suscripcionService.updateSuscripcion(s.getId(), null, 100d);
		

//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());
		
// 2018-04-07 - Sanz Martínez, Emilio
//		p = new Persona();
//		
//		p.setNif("05395853F");
//		
//		List<Persona> listaPersonas;
//		
//		listaPersonas = personaService.findPersonasByNif("05395853F");
//		assertNotNull(listaPersonas);
//		
//		if (listaPersonas.size() > 1) {
//			throw new RuntimeException("Más de una persona para el DNI:" + p.getNif());
//		} else if (!listaPersonas.isEmpty()) {
//			p=listaPersonas.get(0);
//		} else {
//			p.setNombre("Sanz Martínez, Emilio");
//			p.setDomicilio("C/ Julio Rey Pastor, 4 - 4º izda.");
//			p.setCp("28007");
//			p.setPoblacion("Madrid");
//			p.setTfno("915595658");
//			p.setCorreo("emiliosanzm@gmail.com");
//			p = personaService.createPersona(p);
//			assertNotNull(p);
//		}

//		Bancos banco = null;
//		List<Bancos>lista = null;
//		banco = bancosService.findBancosByCode("0049", true);
//		Sucursal suc = new Sucursal();
//		suc.setIdBanco(banco);
//		suc.setCodSuc("5142");
//		suc.setDenSuc("Oficina 5142 BANCO-SANTANDER en MADRID (MADRID) - Av. del Mediterráneo, 30");
//		suc = sucursalService.updateSucursal(suc);
//		
//		s = suscripcionService.createSuscripcion("ES1701824027280200286843", 8d, "05395853F", "M");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

// Antonio Justel Perandones		
		//
//		s = suscripcionService.findSuscripcionById(6616);
//		assertNotNull(s);
//      
//		s = suscripcionService.updateSuscripcion(s.getId(), null, 10.0);
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

		
// 2018-07-09 Mª Jesús Garrido Galindo
		//
//		p = new Persona();
//		
//		p.setNif("11791453C");
//		
//		List<Persona> listaPersonas;
//		
//		listaPersonas = personaService.findPersonasByNif("11791453C");
//		assertNotNull(listaPersonas);
//		
//		if (listaPersonas.size() > 1) {
//			throw new RuntimeException("Más de una persona para el DNI:" + p.getNif());
//		} else if (!listaPersonas.isEmpty()) {
//			p=listaPersonas.get(0);
//		} else {
//			p.setNombre("Garrido Galindo, María Jesús");
//			p.setDomicilio("c/ Leo, 7");
//			p.setCp("28007");
//			p.setPoblacion("Madrid");
//			p.setMovil("650563031");
//			p.setCorreo("marigargal@hotmail.com");
//			p = personaService.createPersona(p);
//			assertNotNull(p);
//		}
//
//		Bancos banco = null;
//		banco = bancosService.findBancosByCode("0049", true);
//		Sucursal suc; 
//		String codSuc = "2861";
//		suc = sucursalService.findSucursalByBcoAndCode(banco.getCodBco(), codSuc);
//		if (suc == null) {
//			suc = new Sucursal();
//			suc.setIdBanco(banco);
//			suc.setCodSuc(codSuc);
//			suc.setDenSuc("Oficina 2861 BANCO-SANTANDER en MADRID (MADRID) - c/ Dr. Esquerdo, 126");
//			suc = sucursalService.updateSucursal(suc);
//		}
//		String cta = "ES5000492861162494160301";
//		
//		s = suscripcionService.createSuscripcion(cta, 20d, p.getNif(), "M");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

// 2018-09-13
// Julia Rivas Maestro		
				//
//				s = suscripcionService.findSuscripcionById(6596);
//				assertNotNull(s);
//		      
//				s = suscripcionService.updateSuscripcion(s.getId(), "ES7401280064310100053465", null);
//				assertNotNull(s);
//				assertNotNull(s.getActivePSD());

// 2018-11-12
// María José Vilar Romo - 50€ - ES20-0049-2861-1822-1432-1660
//		p = new Persona();
//		
//		p.setNif("06240872Y");
//		
//		List<Persona> listaPersonas;
//		
//		listaPersonas = personaService.findPersonasByNif(p.getNif());
//		assertNotNull(listaPersonas);
//		
//		if (listaPersonas.size() > 1) {
//			throw new RuntimeException("Más de una persona para el DNI:" + p.getNif());
//		} else if (!listaPersonas.isEmpty()) {
//			p=listaPersonas.get(0);
//		} else {
//			p.setNombre("Vilar Romo, María José");
//			p.setDomicilio("c/ Cruz del Sur, 30C, 2ºE");
//			p.setCp("28007");
//			p.setPoblacion("Madrid");
//			p.setMovil("644025230");
//			p.setCorreo("mariajose.vilar@rtve.es");
//			p = personaService.createPersona(p);
//			assertNotNull(p);
//		}
//
//		Bancos banco = null;
//		banco = bancosService.findBancosByCode("0049", true);
//		Sucursal suc; 
//		String codSuc = "2861";
//		suc = sucursalService.findSucursalByBcoAndCode(banco.getCodBco(), codSuc);
//		if (suc == null) {
//			suc = new Sucursal();
//			suc.setIdBanco(banco);
//			suc.setCodSuc(codSuc);
//			suc.setDenSuc("Oficina 2861 BANCO-SANTANDER en MADRID (MADRID) - c/ Dr. Esquerdo, 126");
//			suc = sucursalService.updateSucursal(suc);
//		}
//		String cta = "ES2000492861182214321660";
//		
//		s = suscripcionService.createSuscripcion(cta, 50d, p.getNif(), "M");
//		assertNotNull(s);
//		assertNotNull(s.getActivePSD());

		
	}
}