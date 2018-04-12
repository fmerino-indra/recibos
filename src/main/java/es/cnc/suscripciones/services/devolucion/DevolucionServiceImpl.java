package es.cnc.suscripciones.services.devolucion;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.sepa.domain.v002.v001.v03.CustomerPaymentStatusReportV03;
import com.sepa.domain.v002.v001.v03.Document;
import com.sepa.domain.v002.v001.v03.ObjectFactory;
import com.sepa.domain.v002.v001.v03.OriginalPaymentInformation1;
import com.sepa.domain.v002.v001.v03.PaymentTransactionInformation25;
import com.sepa.domain.v002.v001.v03.StatusReasonInformation8;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Devoluciones;
import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.domain.Meses;
import es.cnc.suscripciones.domain.MsgDevolucion;
import es.cnc.suscripciones.domain.Reason;
import es.cnc.suscripciones.domain.SepaCoreXml;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.domain.dao.spring.DevolucionRepository;
import es.cnc.suscripciones.domain.dao.spring.EmisionRepository;
import es.cnc.suscripciones.domain.dao.spring.MesesRepository;
import es.cnc.suscripciones.domain.dao.spring.MsgDevolucionRepository;
import es.cnc.suscripciones.domain.dao.spring.ReasonRepository;
import es.cnc.suscripciones.front.dto.reports.DevolucionesReportDTO;
import es.cnc.util.LocalDateUtil;
import es.cnc.util.sepa.ConverterUtils;

@Component("devolucionService")
public class DevolucionServiceImpl implements DevolucionService {

	Logger logger;

	@Autowired
	CabeceraRepository cabeceraRepository;

	@Autowired
	EmisionRepository emisionRepository;

	@Autowired
	MsgDevolucionRepository msgDevolucionRepository;
	
	@Autowired
	DevolucionRepository devolucionRepository;

	@Autowired
	ReasonRepository reasonRepository;

	@Autowired
	MesesRepository mesesRepository;

	public DevolucionServiceImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public void readRefundXMLJackson(File xmlFile) {
		FileInputStream fis = null;
		@SuppressWarnings("unused")
		ByteArrayInputStream bais = null;
		XmlMapper mapper = new XmlMapper();
		@SuppressWarnings("unused")
		Document document = null;
		if (xmlFile.exists()) {
			try {
				fis = new FileInputStream(xmlFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
			mapper.setLocale(Locale.getDefault());
			mapper.getDateFormat().setTimeZone(TimeZone.getDefault());
			mapper.registerModule(new JaxbAnnotationModule());
			mapper.registerModule(new JacksonXmlModule());
			try {
				document = mapper.readValue(fis, Document.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void readRefundXMLJAXB(File xmlFile) throws FileNotFoundException {
		FileInputStream fis = null;
		if (xmlFile.exists()) {
			fis = new FileInputStream(xmlFile);
			readRefundXMLJAXB(fis);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void readRefundXMLJAXB(InputStream is) {
		String xml = null;
		Document document = null;
		JAXBElement<Document> ele = null;
		
		try {
	        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);

	        Unmarshaller unmarshaller = jc.createUnmarshaller();
	        Object tests = unmarshaller.unmarshal(is);
	        ele = (JAXBElement<Document>)tests;
	        document = ele.getValue();
	        processRefund(document);
	        ((ByteArrayInputStream)is).reset();
	        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
	        	xml = buffer.lines().collect(Collectors.joining("\n"));
	        }
	        
//	        xml = new String (Files.readAllBytes(xmlFile.toPath()));
	        logger.debug(xml);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void processRefund(Document document) { //, File xmlFile) {
		SepaCoreXml xml = null;
		Cabeceraemisiones ce = null;
		MsgDevolucion msgDevolucion = null;
		
		String devolucionXMLString = null;
		
		Integer numeroDeTxs = null;
		Integer numeroDeTxsByTxs = null;
		String orgnlEndToEndId = null;
		List<Integer> ids = null;
		List<String> reasons = null;
		
		if (document == null) {
			String msg = "[DevolucionService] No Document to process refund";
			Log.error(msg);
			throw new RuntimeException(msg);
		}
		
		msgDevolucion = new MsgDevolucion();
		msgDevolucion.setMsgId(document.getCstmrPmtStsRpt().getGrpHdr().getMsgId());
		msgDevolucion.setCreationDate(ConverterUtils.xmlGregorianCalendarToDate(document.getCstmrPmtStsRpt().getGrpHdr().getCreDtTm()));
		msgDevolucion.setOrgMsgId(document.getCstmrPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgId());
		
		devolucionXMLString = document2String(document);
		msgDevolucion.setXml(devolucionXMLString);
		
		msgDevolucionRepository.save(msgDevolucion);
		
		ce = cabeceraRepository.findCabeceraByMsgIdFull(msgDevolucion.getOrgMsgId());
		
		if (ce.getSepaCoreXMLs() != null && !ce.getSepaCoreXMLs().isEmpty()) {
			for (SepaCoreXml x : ce.getSepaCoreXMLs()) {
				// Como CE hay dos OneToMany y se usa @OrderColumn, al ejecutar la
				// consulta, el elemento se mete en una lista cuyo índice es igual
				// al atributo utilizado en @OrderColumn
				if (x != null) {
					xml = x;
					break;
				}
			}
			// Una vez cogido el xml y el ce, hay que procesar el document de 002, es decir las devoluciones.
			numeroDeTxs = new Integer(document.getCstmrPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlNbOfTxs());
			ids = new ArrayList<>();
			reasons = new ArrayList<>();
			
			for (OriginalPaymentInformation1 dev : document.getCstmrPmtStsRpt().getOrgnlPmtInfAndSts()) {
				numeroDeTxsByTxs = new Integer(dev.getOrgnlNbOfTxs());
				for (PaymentTransactionInformation25 devPart : dev.getTxInfAndSts()) {
					orgnlEndToEndId = devPart.getOrgnlEndToEndId();
					ids.add(parseOrgnlEndToEndId(orgnlEndToEndId));
					reasons.add(parseReason(devPart.getStsRsnInf()));
				}
			}
			this.devolver(ids, reasons, msgDevolucion);
		} else {
			// TODO [FMM] No se puede procesar automáticamente porque no hay registro XML
		}
	}
	
	private String parseReason(List<StatusReasonInformation8> reasons) {
		String reason = null;
		if (reasons != null) {
			for (StatusReasonInformation8 reas8:reasons) {
				reason = reas8.getRsn().getCd();
			}
		}
		return reason;
	}
	
	private Integer parseOrgnlEndToEndId(String orgnlEndToEndId) {
		Integer idEmision = null;
		String[] tokens = null;
		String id = null;
		tokens = orgnlEndToEndId.split("[ ]+");
		if (tokens != null && tokens.length > 0)
			id = tokens[0];
		
		if (id != null) 
			idEmision = new Integer(id);
		return idEmision;
	}

	@SuppressWarnings("unused")
	private String readFile(File aux) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		
		FileInputStream fis = null; 
		BufferedReader br = null;
		try {
			fis = new FileInputStream(aux);
			br = new BufferedReader(new InputStreamReader(fis));
			sb = new StringBuffer();
			
			while ((line=br.readLine()) != null) 
				sb.append(line);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return sb.toString();
	}
	@Override
	@Transactional
	public void devolver(List<Integer> ids) {
		devolver(ids, null, null);
	}

	@Transactional
	private void devolver(List<Integer> ids, List<String> reasons, MsgDevolucion msgDevolucion) {
		Emision aux = null;
		Devoluciones devolucionActiva;
		LocalDateTime today = LocalDateTime.now();
		
		Integer id = null;
		String reasonCode = null;
		Reason reason = null;
		
		for (int auxIdx = 0; auxIdx<ids.size(); auxIdx++) {
			reasonCode = null;
			
			id = ids.get(auxIdx);
			if (reasons != null)
				reasonCode = reasons.get(auxIdx);
			
			if (reasonCode != null)
				reason = reasonRepository.findByReasonCode(reasonCode);
			else
				reason = null;
			
			aux = emisionRepository.getOne(id);
			aux.setDevuelto(true);
			emisionRepository.save(aux);
	
			// TODO FMM No tiene sentido, nunca habrá una activa si se va a
			// devolver
			devolucionActiva = devolucionRepository.findActiveReturnedByEmision(aux);
			if (devolucionActiva != null) {
	
				devolucionActiva.setFechaBaja(LocalDateUtil.localDateTimeToDate(today));
				devolucionRepository.save(devolucionActiva);
			}
			devolucionActiva = new Devoluciones();
			devolucionActiva.setEmision(aux);
			devolucionActiva.setFechaDevolucion(LocalDateUtil.localDateTimeToDate(today));
			if (reason != null)
				devolucionActiva.setReason(reason);
			devolucionActiva.setIdMsgDevolucion(msgDevolucion);
			devolucionRepository.save(devolucionActiva);
		}
	}
	

	@Override
	@Transactional
	public void anular(List<Integer> ids) {
		Emision aux = null;
		Devoluciones devolucionActiva;
		LocalDateTime today = LocalDateTime.now();
		for (Integer id : ids) {
			aux = emisionRepository.getOne(id);
			aux.setDevuelto(false);
			emisionRepository.save(aux);

			devolucionActiva = devolucionRepository.findActiveReturnedByEmision(aux);
			if (devolucionActiva != null) {
				if (devolucionActiva.getIdMsgDevolucion() != null) {
					throw new RuntimeException("[] Automatic refund can't be refunded");
				}
				devolucionActiva.setFechaBaja(LocalDateUtil.localDateTimeToDate(today));
				devolucionRepository.save(devolucionActiva);
			} else {
				// TODO FMM Si está anulada y es
			}
		}
	}


	@SuppressWarnings("unchecked")
//		@Override
		public void readRefundXMLJAXBOld(File xmlFile) {
			@SuppressWarnings("unused")
			FileInputStream fis = null;
			@SuppressWarnings("unused")
			ByteArrayInputStream bais = null;
			Document document = null;
			JAXBElement<Document> ele = null;
			
			if (xmlFile.exists()) {
				try {
					fis = new FileInputStream(xmlFile);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	//			prueba();
				try {
			        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
		
			        Unmarshaller unmarshaller = jc.createUnmarshaller();
	//		        Document tests = (Document) unmarshaller.unmarshal(xmlFile);
			        Object tests = unmarshaller.unmarshal(xmlFile);
			        ele = (JAXBElement<Document>)tests;
			        document = ele.getValue();
			        processRefund(document);
			        
	//		        Marshaller marshaller = jc.createMarshaller();
	//		        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	//		        marshaller.marshal(tests, System.out);
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	public static void prueba() {
		ObjectFactory of = new ObjectFactory();
		Document doc = new Document();
		CustomerPaymentStatusReportV03 cus = new CustomerPaymentStatusReportV03();
		doc.setCstmrPmtStsRpt(cus);
//		doc.setCstmrPmtStsRpt(null);
		JAXBContext jc = null;
		Marshaller mar = null;
		JAXBElement<Document> ele = null;
		ele = of.createDocument(doc);
		
		try {
			jc = JAXBContext.newInstance(Document.class);
			
			mar = jc.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			mar.marshal(ele, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		prueba();
	}
	
	private static String document2String(Document document) {
		ByteArrayOutputStream baos = null;
		String xml = null;
		
		XmlMapper mapper = new XmlMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		mapper.setLocale(Locale.getDefault());
		mapper.getDateFormat().setTimeZone(TimeZone.getDefault());
		mapper.registerModule(new JaxbAnnotationModule());
		mapper.registerModule(new JacksonXmlModule());

		
		baos = new ByteArrayOutputStream();
		try {
			mapper.writeValue(baos, document);
			xml = new String(baos.toByteArray());
			System.out.println(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

	@Override
	public List<DevolucionesReportDTO> generateRefundReport(Integer fromYear, Integer toYear) {
		return buildDevolucionesReportDTOList(devolucionRepository.findDevolucionesByPeriod(fromYear, toYear));
	}
	
	private List<DevolucionesReportDTO> buildDevolucionesReportDTOList(List<Devoluciones> laLista) {
		List<Meses> meses = mesesRepository.findMesesWithPeriodos();
		
		List<DevolucionesReportDTO> listaRetorno = new ArrayList<>();
		for (Devoluciones dev:laLista) {
			listaRetorno.add(buildDevolucionesReportDTO(dev, meses));
		}
		return listaRetorno;
	}
	
	private DevolucionesReportDTO buildDevolucionesReportDTO(Devoluciones devolucion, final List<Meses> meses) {
		DevolucionesReportDTO dto = null;
		
		if (devolucion != null) {
			dto = new DevolucionesReportDTO();
			dto.setId(devolucion.getId());
			dto.setAnyo(devolucion.getEmision().getIdCabecera().getAnyo());
			dto.setCodigoMes(devolucion.getEmision().getIdCabecera().getCodigoMes());
			dto.setDescMes(findMesByCode(meses, dto.getCodigoMes()));
			dto.setReasonCode(devolucion.getReason()!=null?devolucion.getReason().getReasonCode():"");
			dto.setReasonDescription(devolucion.getReason()!=null?devolucion.getReason().getReasonDescription():"");
			dto.setFechaDevolucion(devolucion.getFechaDevolucion());
			dto.setImporte(devolucion.getEmision().getImporte());
			dto.setNombrePersona(devolucion.getEmision().getIdSuscripcion().getIdSuscripcion().getPersona().getNombre());
			dto.setTelefono(devolucion.getEmision().getIdSuscripcion().getIdSuscripcion().getPersona().getTfno());
			dto.setMovil(devolucion.getEmision().getIdSuscripcion().getIdSuscripcion().getPersona().getMovil());
			dto.setPeriodo(devolucion.getEmision().getIdSuscripcion().getIdSuscripcion().getPeriodo());
		}
		
		return dto;
	}

	private String findMesByCode(final List<Meses> meses, Integer mesCode) {
		return meses.stream().filter(mes -> mes.getIdMes().equals(mesCode)).findFirst().get().getMes();
	}
}
