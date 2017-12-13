
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

import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.sepa.domain.v008.v001.v02.Document;

import es.cnc.Application;
import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.SepaCoreXml;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.domain.dao.spring.SepaCoreXmlRepository;
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
	
	@Autowired
	private SepaCoreXmlRepository sepaCoreXmlRepository;
	
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


//	@Test
	public void updateXMLIdx() throws Exception {
		List<SepaCoreXml> xmls = null;
		xmls = sepaCoreXmlRepository.findXmlWithCabecera();
		Document document = null;
		String msgId = null;

		MessageDigest md = null;
		String myHash = null;
		byte[] digest = null;
		
		XmlMapper mapper = new XmlMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		mapper.setLocale(Locale.getDefault());
		mapper.getDateFormat().setTimeZone(TimeZone.getDefault());
		mapper.registerModule(new JaxbAnnotationModule());
		mapper.registerModule(new JacksonXmlModule());
		md = MessageDigest.getInstance("MD5");
		
		if (xmls != null) {
			for (SepaCoreXml xml:xmls) {
				document = mapper.readValue(xml.getXml(), Document.class);
				msgId = document.getCstmrDrctDbtInitn().getGrpHdr().getMsgId();
				md.update(xml.getXml().getBytes());
				digest = md.digest();
				myHash = DatatypeConverter.printBase64Binary(digest);
				xml.setIdMsg(msgId);
				xml.setHash(myHash);
				xml.setFechaEnvio(xml.getIdCabecera().getFechaEnvio());
				System.out.println(xml.getId()+"-"+xml.getIdCabecera().getId()+"-"+msgId+"-"+myHash+"-"+xml.getXml().substring(0, 200));
				sepaCoreXmlRepository.saveAndFlush(xml);
			}
			
			md = MessageDigest.getInstance("MD5");
			for (SepaCoreXml xml:xmls) {
				md.reset();
				md.update(xml.getXml().getBytes());
				digest = md.digest();
				myHash = DatatypeConverter.printBase64Binary(digest);
				System.out.println(xml.getId()+"-"+(xml.getHash().equals(myHash)?"TRUE":"mal")+"-"+xml.getIdCabecera().getId()+"-"+xml.getIdMsg());
				
			}
			
		}
	}

//	@Test
	public void validateXMLIdx() throws Exception {
		List<SepaCoreXml> xmls = null;
		xmls = sepaCoreXmlRepository.findXmlWithCabecera();
//		Document document = null;
//		String msgId = null;
	
		MessageDigest md = null;
		String myHash = null;
		byte[] digest = null;
		
		XmlMapper mapper = new XmlMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		mapper.setLocale(Locale.getDefault());
		mapper.getDateFormat().setTimeZone(TimeZone.getDefault());
		mapper.registerModule(new JaxbAnnotationModule());
		mapper.registerModule(new JacksonXmlModule());
		
		md = MessageDigest.getInstance("MD5");
		
		if (xmls != null) {
			md = MessageDigest.getInstance("MD5");
			for (SepaCoreXml xml:xmls) {
//				document = mapper.readValue(xml.getXml(), Document.class);
//				msgId = document.getCstmrDrctDbtInitn().getGrpHdr().getMsgId();

				md.reset();
				md.update(xml.getXml().getBytes());
				digest = md.digest();
				myHash = DatatypeConverter.printBase64Binary(digest);
				System.out.println(xml.getId()+"-"+(xml.getHash().equals(myHash)?"TRUE":"mal")+"-"+xml.getIdCabecera().getId()+"-"+xml.getIdMsg()+"-"+xml.getXml());
				
			}
			
		}
	}

//	@Test
	public void validateCeOrder() throws Exception {
		Cabeceraemisiones ce = new Cabeceraemisiones();
		ce.setId(3876);
		ce = cabeceraRepository.findCabeceraByIdFull(ce);
	}
}