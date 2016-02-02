package es.cnc.suscripciones.services.generacion;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.sepa.domain.CustomerDirectDebitInitiationV02;
import com.sepa.domain.Document;
import com.sepa.domain.GroupHeader39;
import com.sepa.domain.ObjectFactory;
import com.sepa.domain.PaymentInstructionInformation4;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Meses;
import es.cnc.suscripciones.domain.Periodo;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.domain.dao.spring.PeriodoRepository;

@Component("generacionService")
public class GeneracionServiceImpl implements GeneracionService {
	@Autowired
	PeriodoRepository periodoRepository;
	
	@Autowired
	CabeceraRepository cabeceraRepository;
	
	private static ObjectFactory oF = new ObjectFactory();


	public GeneracionServiceImpl() {
	}

	/* (non-Javadoc)
	 * @see es.cnc.suscripciones.services.generacion.GeneracionService#findMascara()
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
	
//	public List<Cabeceraemisiones> findCabecera(Periodo periodo) {
//		
//	}
	
	/* (non-Javadoc)
	 * @see es.cnc.suscripciones.services.generacion.GeneracionService#generateISO20022(es.cnc.suscripciones.domain.Cabeceraemisiones)
	 */
	@Override
	public void generateISO20022(Cabeceraemisiones cabecera) throws DatatypeConfigurationException {
		/*
		 * 1. Recibe la cabecera (ya lleva parroquia, parroco, etc.)
		 * 2. Busca las emisiones
		 * 3. 
		 */
		Cabeceraemisiones cabAux = cabeceraRepository.findEmisionesByCabecera(cabecera);
		if (cabAux == null) {
			// TODO To throw a Business Exception
			throw new RuntimeException("No Emission Header");
		}
			
		Document document = oF.createDocument();
		CustomerDirectDebitInitiationV02 cstmrDrctDbtInitn = oF.createCustomerDirectDebitInitiationV02();
		
		// This is for database
//		DirectDebitOrder ddo = new DirectDebitOrder();
		
//		ddo.setId(1);
//		ddo.setCreDtTm(LocalDateUtil.dateToLocalDateTime(cabAux.getFechaEmision()));
		
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

		try {
			mapper.writeValue(System.out, document);
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
