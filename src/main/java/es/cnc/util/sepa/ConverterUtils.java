package es.cnc.util.sepa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Emision;
import es.cnc.util.LocalDateUtil;

public class ConverterUtils {

	public static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	public static String buildNbOfTxs(Integer nbOfTxt) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format("%1$d", nbOfTxt);
		formatter.close();
		return sb.toString();
	}
	
	/**
	 * Builds the sum of the order.
	 * @param ce Cabeceraemisiones with info from database
	 * @return
	 */
	public static BigDecimal buildCtrlSum(Cabeceraemisiones ce) {
		Double d = new Double(0);
		for (Emision em:ce.getEmisions()) {
			d+=em.getImporte();
		}
		return new BigDecimal(d).setScale(2,RoundingMode.HALF_UP);
	}
	
	/**
	 * TODO Review serialization. UTC format 
	 * @param auxTime
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar buildCreDtTm (LocalDateTime auxTime) throws DatatypeConfigurationException {
		/*
	     * The output will be one of the following ISO-8601 formats:
	         * <ul>
	         * <li>{@code uuuu-MM-dd'T'HH:mm}</li>
	         * <li>{@code uuuu-MM-dd'T'HH:mm:ss}</li>
	         * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSS}</li>
	         * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSS}</li>
	         * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSS}</li>
	         * </ul>
		*/
		/*
		 * Falla la hora con precisión reducida, es decir si se pone la hora sin segundos (formato reducido) el parser interno falla.
		 */
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(auxTime.format(formatter));
	}
	
	/**
	 * Builds XMLGregorianCalendar without Time
	 * @param auxTime
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar buildCreDtTm(Date auxTime) throws DatatypeConfigurationException {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDateUtil.toYYYYMMDDz(LocalDateUtil.dateToLocalDate(auxTime)));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void main(String[] args) throws Exception {
		LocalDateTime hora = LocalDateTime.now();
		LocalDate fecha = LocalDate.now();
		System.out.println(LocalDateUtil.toISOLocalDateTimeNoMillis(hora));
		System.out.println(LocalDateUtil.toYYYYMMDDz(fecha));
		System.out.println(buildCreDtTm(hora));
		System.out.println(buildCreDtTm(new Date()));
		System.out.println(ConverterUtils.buildCreDtTm(LocalDateUtil.dateToLocalDateTime(new Date())));
		
	}
	
}
