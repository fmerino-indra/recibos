package es.cnc.suscripciones.services.generacion;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Meses;
import es.cnc.suscripciones.domain.Periodo;
import es.cnc.suscripciones.domain.SepaCoreXml;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.domain.dao.spring.PeriodoRepository;
import es.cnc.suscripciones.domain.dao.spring.SepaCoreXmlRepository;
import es.cnc.util.LocalDateUtil;
import es.cnc.util.app.HashUtils;

@Component("generacionService")
public class GeneracionServiceImpl implements GeneracionService {
	@Autowired
	PeriodoRepository periodoRepository;

	@Autowired
	CabeceraRepository cabeceraRepository;

	@Autowired
	SepaCoreXmlRepository xmlRepository;

	@Value("${app.xml.file.dir}")
	private String dir;

	private static ObjectFactory oF = new ObjectFactory();

	public GeneracionServiceImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.cnc.suscripciones.services.generacion.GeneracionService#findMascara()
	 */
	@Override
	public List<Periodo> findMascara() {
		Meses mes = null;
		mes = new Meses();
		LocalDate ld = LocalDate.now();
		int month = ld.getMonthValue();
		mes.setId(month);
		return periodoRepository.findPeriodosByMes(mes);
	}

	// public List<Cabeceraemisiones> findCabecera(Periodo periodo) {
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.cnc.suscripciones.services.generacion.GeneracionService#
	 * generateISO20022(es.cnc.suscripciones.domain.Cabeceraemisiones)
	 */
	@Override
	@Transactional
	public String generateISO20022(Cabeceraemisiones cabecera) throws DatatypeConfigurationException {
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
		Cabeceraemisiones cabAux = cabeceraRepository.findCabeceraByIdFull(cabecera);
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
		cabeceraRepository.save(cabAux);
		
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

	private SepaCoreXml updateSepaCoreXml(Cabeceraemisiones cabAux, Document document, String sb) {
		List<SepaCoreXml> xmls = null;
		xmls = xmlRepository.findXmlByCabecera(cabAux);
		
		for (SepaCoreXml xml : xmls) {
			xml.setActivo(false);
			xmlRepository.saveAndFlush(xml);
		}
		
		SepaCoreXml xml = new SepaCoreXml();
		xml.setIdCabecera(cabAux);
		xml.setXml(sb);
		xml.setFechaEnvio(cabAux.getFechaEnvio());
		xml.setIdMsg(document.getCstmrDrctDbtInitn().getGrpHdr().getMsgId());
		xml.setHash(HashUtils.calcMD5Base64(xml.getXml()));
		xmlRepository.save(xml);
		
		return xml;
	}
	
	public String calcFileName(Cabeceraemisiones cabecera) {
		String retorno = null;
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%04d", cabecera.getAnyo()));
		sb.append("-");
		sb.append(String.format("%02d", cabecera.getCodigoMes()));
		sb.append("-");

		LocalDateTime fecha = LocalDateTime.now();
		fecha = fecha.withMonth(cabecera.getCodigoMes());
		sb.append(LocalDateUtil.obtainTextualMonth(fecha));

		Periodo p = periodoRepository.findOne(cabecera.getPeriodo());
		sb.append(p.getNombre());
		sb.append(".xml");
		retorno = sb.toString();
		System.out.println(retorno);
		return retorno;
	}

}
