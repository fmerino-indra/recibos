package es.cnc.suscripciones.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import es.cnc.Application;
import es.cnc.suscripciones.domain.Bancos;
import es.cnc.suscripciones.domain.Domiciliacion;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.SolicitudEmisionManual;
import es.cnc.suscripciones.domain.Sucursal;
import es.cnc.suscripciones.domain.dao.spring.SolicitudEmisionManualRepository;
import es.cnc.suscripciones.front.dto.SolicitudDTO;
import es.cnc.suscripciones.services.bancos.BancosService;
import es.cnc.suscripciones.services.domiciliacion.DomiciliacionService;
import es.cnc.suscripciones.services.emisionmanual.EmisionManualService;
import es.cnc.suscripciones.services.persona.PersonaService;
import es.cnc.suscripciones.services.sucursal.SucursalService;
import es.cnc.util.LocalDateUtil;
import es.cnc.util.sepa.ConstantsCNC;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class EmisionManualServiceTest {
	Logger logger;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private BancosService bancosService;
	
	@Autowired
	private SucursalService sucursalService;
	
	@Autowired
	private EmisionManualService emisionManualService;

	@Autowired
	private DomiciliacionService domiciliacionService;

	@Autowired
	private SolicitudEmisionManualRepository solicitudRepository;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

//	@Test
	@Transactional
	public void createSolicitudes() throws Exception {
		List<Persona> listaPersonas = null;
		Persona persona = null;
		Domiciliacion dom = null; 
		
		Bancos banco = null;
		Sucursal suc = null;
		String codSuc = null;
		Date fecha = null;

		SolicitudDTO solicitud = new SolicitudDTO();
		List<SolicitudDTO> solicitudes = null;
		
		solicitudes = new ArrayList<>();

		String nif = null;
		String iban = null;
		
		
		// Javier Sastre Picón
		nif = "51693031W";
		iban = "ES3101824027220000908817";
		
		listaPersonas=personaService.findPersonasByNif(nif);
		if (listaPersonas == null || listaPersonas.size() == 0) {
			persona = new Persona();
			persona.setCorreo("ljaviersastre@yahoo.es");
			persona.setCp("28007");
			persona.setDomicilio("Av. Mediterráneo, 19 - 6ºI");
			persona.setMovil("686984672");
			persona.setNif(nif);
			persona.setNombre("Sastre Picón, Javier");
			persona.setPoblacion("Madrid");
			persona = personaService.createPersona(persona);
		} else {
			persona = listaPersonas.get(0);
		}
		
		dom = domiciliacionService.findDomiciliacion(iban);
		
		if (dom == null) {
			dom = new Domiciliacion();
			dom.setIban(iban);
			dom.setIdPersona(persona);
		
			banco = bancosService.findBancosByCode("0182", true);
			codSuc = "4027";
			suc = sucursalService.findSucursalByBcoAndCode(banco.getCodBco(), codSuc);
			if (suc == null) {
				suc = new Sucursal();
				suc.setIdBanco(banco);
				suc.setCodSuc(codSuc);
				suc.setDenSuc("Oficina 4027 BBVA MADRID - Av. del Mediterráneo, 24");
				suc = sucursalService.updateSucursal(suc);
			}
			dom.setSucursalId(suc);
			domiciliacionService.createDomiciliacion(dom);
		}
		
		solicitud = new SolicitudDTO();
		solicitud.setDomiciliacion(dom);
		solicitud.setImporte(240d);
		
		solicitudes.add(solicitud);
		

		// Otro
		nif = "50523380Q";
		iban = "ES9400301122660001963271";
		
		listaPersonas=personaService.findPersonasByNif(nif);
		if (listaPersonas == null || listaPersonas.size() == 0) {
			persona = new Persona();
			persona.setCorreo("julitamari@movistar.es");
			persona.setCp("28007");
			persona.setDomicilio("Av. Mediterráneo, 15 - 3ºA");
			persona.setMovil("605905253");
			persona.setNif(nif);
			persona.setNombre("Sanz Corado, Julia María");
			persona.setPoblacion("Madrid");
			persona = personaService.createPersona(persona);
		} else {
			persona = listaPersonas.get(0);
			persona.setMovil("605905253");
			persona.setCorreo("julitamari@movistar.es");
			persona = personaService.updatePersona(persona);
		}
		
		dom = domiciliacionService.findDomiciliacion(iban);
		
		if (dom == null) {
			dom = new Domiciliacion();
			dom.setIban(iban);
			dom.setIdPersona(persona);
		
			banco = bancosService.findBancosByCode("0030", true);
			codSuc = "1122";
			suc = sucursalService.findSucursalByBcoAndCode(banco.getCodBco(), codSuc);
			dom.setSucursalId(suc);
			domiciliacionService.createDomiciliacion(dom);
		}
		
		solicitud = new SolicitudDTO();
		solicitud.setDomiciliacion(dom);
		solicitud.setImporte(2000d);
		
		solicitudes.add(solicitud);

		
		
		// González Álvarez, Adela María
		nif = "51633482T";
		iban = "ES6821009281282200341032";
		
		listaPersonas=personaService.findPersonasByNif(nif);
		if (listaPersonas == null || listaPersonas.size() == 0) {
			persona = new Persona();
			persona.setCorreo("ademgonz@gmail.com");
			persona.setCp("28007");
			persona.setDomicilio("Av. Mediterráneo, 15 - 4B");
			persona.setMovil("626170576");
			persona.setTfno("915523530");
			persona.setNif(nif);
			persona.setNombre("González Álvarez, Adela María");
			persona.setPoblacion("Madrid");
			persona = personaService.createPersona(persona);
		} else {
			persona = listaPersonas.get(0);
		}
		
		dom = domiciliacionService.findDomiciliacion(iban);
		
		if (dom == null) {
			dom = new Domiciliacion();
			dom.setIban(iban);
			dom.setIdPersona(persona);
		
			banco = bancosService.findBancosByCode("2100", true);
			codSuc = "9281";
			suc = sucursalService.findSucursalByBcoAndCode(banco.getCodBco(), codSuc);
			dom.setSucursalId(suc);
			domiciliacionService.createDomiciliacion(dom);
		}
		
		solicitud = new SolicitudDTO();
		solicitud.setDomiciliacion(dom);
		solicitud.setImporte(200d);
		
		solicitudes.add(solicitud);
		
		
		// Garrido Galindo, María Jesús
		nif = "11791453C";
		iban = "ES5000492861162494160301";
		
		listaPersonas=personaService.findPersonasByNif(nif);
		if (listaPersonas == null || listaPersonas.size() == 0) {
			persona = new Persona();
			persona.setCorreo("mariagargal@hotmail.es");
			persona.setCp("28007");
			persona.setDomicilio("C/ Leo, 7");
			persona.setMovil("650563031");
			persona.setNif(nif);
			persona.setNombre("Garrido Galindo, María Jesús");
			persona.setPoblacion("Madrid");
			persona = personaService.createPersona(persona);
		} else {
			persona = listaPersonas.get(0);
			persona.setCorreo("mariagargal@hotmail.es");
			persona = personaService.updatePersona(persona);
			
		}
		
		dom = domiciliacionService.findDomiciliacion(iban);
		
		if (dom == null) {
			dom = new Domiciliacion();
			dom.setIban(iban);
			dom.setIdPersona(persona);
		
			banco = bancosService.findBancosByCode("0049", true);
			codSuc = "2861";
			suc = sucursalService.findSucursalByBcoAndCode(banco.getCodBco(), codSuc);
			dom.setSucursalId(suc);
			domiciliacionService.createDomiciliacion(dom);
		}
		
		solicitud = new SolicitudDTO();
		solicitud.setDomiciliacion(dom);
		solicitud.setImporte(100d);
		
		solicitudes.add(solicitud);
		
		
		// Llamada
		LocalDateTime today = null;
		today = LocalDateTime.now();
		today.getMonthValue();
		fecha = LocalDateUtil.localDateTimeToDate(today);
		
		
		emisionManualService.generateSolicitudManual(solicitudes, ConstantsCNC.CONCEPTO + "-Ocasional", fecha);
		
	}
	
	@Test
//	@Transactional
	public void createEmisionManual() throws Exception {
		
		List<SolicitudEmisionManual> solicitudes = null;
		SolicitudEmisionManual sem = null;
		sem = solicitudRepository.findFullSolicitudEmisionManualById(5);
		solicitudes = new ArrayList<>();
		solicitudes.add(sem);
		emisionManualService.generateCabeceraEmisionManual(solicitudes);
	}
}