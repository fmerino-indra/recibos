package es.cnc.suscripciones.services.generacionmanual;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.sepa.domain.v008.v001.v02.CustomerDirectDebitInitiationV02;
import com.sepa.domain.v008.v001.v02.Document;
import com.sepa.domain.v008.v001.v02.GroupHeader39;
import com.sepa.domain.v008.v001.v02.ObjectFactory;
import com.sepa.domain.v008.v001.v02.PaymentInstructionInformation4;

import es.cnc.suscripciones.domain.CabeceraEmisionManual;
import es.cnc.suscripciones.domain.SepaCoreXmlManual;
import es.cnc.suscripciones.domain.dao.spring.CabeceraEmisionManualRepository;
import es.cnc.suscripciones.domain.dao.spring.PeriodoRepository;
import es.cnc.suscripciones.domain.dao.spring.SepaCoreXmlManualRepository;
import es.cnc.util.LocalDateUtil;
import es.cnc.util.app.HashUtils;

@Component("generacionManualService")
public class GeneracionServiceImpl implements GeneracionManualService {
	@Autowired
	PeriodoRepository periodoRepository;

	@Autowired
	CabeceraEmisionManualRepository cabeceraManualRepository;

	@Autowired
	SepaCoreXmlManualRepository xmlManualRepository;

	@Value("${app.xml.file.dir}")
	private String dir;

	private static ObjectFactory oF = new ObjectFactory();

	public GeneracionServiceImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.cnc.suscripciones.services.generacion.GeneracionService#
	 * generateISO20022(es.cnc.suscripciones.domain.Cabeceraemisiones)
	 */
	@Override
	@Transactional
	public String generateISO20022(CabeceraEmisionManual cabecera) throws DatatypeConfigurationException {
		String fileName = null;
		FileOutputStream fos = null;
		ByteArrayOutputStream baos = null;
		Path ruta = null;
		/*
		 * 1. Recibe la cabecera (ya lleva parroquia, parroco, etc.) 2. Busca
		 * las emisiones 3.
		 */
		// Cabeceraemisiones cabAux =
		// cabeceraRepository.findCabeceraByIdWithEmisiones(cabecera);
		CabeceraEmisionManual cabAux = cabeceraManualRepository.findCabeceraManualByIdFull(cabecera, true);
		if (cabAux == null) {
			// TODO To throw a Business Exception
			throw new RuntimeException("No Emission Header");
		}
		cabAux.getDomiciliacion();
		Document document = oF.createDocument();
		CustomerDirectDebitInitiationV02 cstmrDrctDbtInitn = oF.createCustomerDirectDebitInitiationV02();

		// This is for database
		// DirectDebitOrder ddo = new DirectDebitOrder();

		// ddo.setId(1);
		// ddo.setCreDtTm(LocalDateUtil.dateToLocalDateTime(cabAux.getFechaEmision()));

		// TODO [FMM] 2017-11-06 Ahora se actualiza la fecha de envío en la generación del XML
		LocalDateTime now = LocalDateTime.now();
		cabAux.setFechaEnvio(LocalDateUtil.localDateTimeToDate(now));
		cabeceraManualRepository.save(cabAux);
		
		GroupHeader39 grpHdr = GroupHeaderBuilder.buildGroupHeader(cabAux);
		PaymentInstructionInformation4 pmtInf = PaymentInstructionBuilder.buildPmtInf(cabAux);

		cstmrDrctDbtInitn.setGrpHdr(grpHdr);
		cstmrDrctDbtInitn.getPmtInf().add(pmtInf);

		document.setCstmrDrctDbtInitn(cstmrDrctDbtInitn);

		XmlMapper mapper = new XmlMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		mapper.setLocale(Locale.getDefault());
		mapper.getDateFormat().setTimeZone(TimeZone.getDefault());
		mapper.registerModule(new JaxbAnnotationModule());
		mapper.registerModule(new JacksonXmlModule());

		fileName = calcFileName(cabAux);
		try {
			// Peta porque el directorio tiene una ñ. Mierda
			// fos = new FileOutputStream(dir + fileName);

			// File fichero = null;
			// Path camino = null;
			// camino = Paths.get("xmlDevelopment/" + cabecera.getAnyo() + "/"+
			// fileName);
			// if (Files.exists(camino)) {
			//
			// } else {
			// Files.createFile(camino);
			// }
			// fichero = new File("xmlDevelopment/" + cabecera.getAnyo() + "/" +
			// fileName);
			// fichero.exists();
			// Path ficheroPath = Files.createFile(camino);
			baos = new ByteArrayOutputStream();
			mapper.writeValue(baos, document);
			String sb = new String(baos.toByteArray());
			System.out.println(sb);
			
			updateSepaCoreXml(cabAux, document, sb);
			ruta = Paths.get(dir, cabecera.getAnyo().toString());
			if (!Files.exists(ruta))
				Files.createDirectory(ruta);
			ruta = Paths.get(ruta.toAbsolutePath().toString(), fileName);
			ruta = Files.createFile(ruta);
			fos = new FileOutputStream(ruta.toFile());
			mapper.writeValue(fos, document);
			fos.flush();
			fos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}

	private SepaCoreXmlManual updateSepaCoreXml(CabeceraEmisionManual cabAux, Document document, String sb) {
		List<SepaCoreXmlManual> xmls = null;
		xmls = xmlManualRepository.findXmlByCabecera(cabAux);
		
		for (SepaCoreXmlManual xml : xmls) {
			xml.setActivo(false);
			xmlManualRepository.saveAndFlush(xml);
		}
		
		SepaCoreXmlManual xml = new SepaCoreXmlManual();
		xml.setIdCabecera(cabAux);
		xml.setXml(sb);
		xml.setFechaEnvio(cabAux.getFechaEnvio());
		xml.setIdMsg(document.getCstmrDrctDbtInitn().getGrpHdr().getMsgId());
		xml.setHash(HashUtils.calcMD5Base64(xml.getXml()));
		xmlManualRepository.save(xml);
		
		return xml;
	}
	
	public String calcFileName(CabeceraEmisionManual cabecera) {
		String retorno = null;
		
		StringBuilder sb = new StringBuilder();
		
		Formatter formatter = new Formatter(sb);
		formatter.format("%1$TY-%1$Tm-%1$Td-%1$TH-%1$TM-%1$TS-%2$S", cabecera.getFechaEnvio(),"Ocasional");
		formatter.close();

		sb.append(".xml");
		retorno = sb.toString();
		return retorno;
	}

}
