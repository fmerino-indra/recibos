
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

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cnc.Application;
import es.cnc.suscripciones.services.certificado.CertificadoService;
import es.cnc.util.LocalDateUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class PDFTests {
	Logger logger;
	@Autowired
	CertificadoService certificadoService;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Test
	public void testGeneratePDFs() throws Exception {
		certificadoService.generateCertificates(2017);
	}
	
//	@Test
	public void testGeneratePDF() throws Exception {
		
		InputStream is = getClass().getResourceAsStream("withholding-v3.jasper");
		
		
		
		JasperReport jr = (JasperReport)JRLoader.loadObject(is);
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("parroco", "Mauricio A. Palacios Gutiérrez");
		parameters.put("parroquia", "Santa Catalina de Siena");
		parameters.put("suscriptor", "Félix Merino Martínez de Pinillos");
		parameters.put("anyo", "2017");
		parameters.put("importe", new Float(9999.56));
		parameters.put("fecha", LocalDateUtil.localDateToDate(LocalDate.now()));
		parameters.put("poblacion", "Madrid");
		
		
		JRDataSource ds = new JREmptyDataSource();
		JasperPrint jp = JasperFillManager.fillReport(jr, parameters, ds);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(jp, baos);
		
		FileOutputStream fos = new FileOutputStream("salida.pdf");
		fos.write(baos.toByteArray());
		fos.close();
//		JRPdfExporter jrpdf = new JRPdfExporter();
//		jrpdf.setParameter(parameter, value);
//		ExporterInput ei;
//		PdfExporterConfiguration ec;
//		PdfReportConfiguration rc;
//		jrpdf.setExporterInput(exporterInput);
//		jrpdf.setConfiguration(configuration);
		
	}
	
}