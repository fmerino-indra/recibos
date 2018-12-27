package es.cnc.suscripciones.services.generacionmanual;

import java.time.LocalDateTime;
import java.util.Formatter;

import javax.xml.datatype.DatatypeConfigurationException;

import org.stasiena.sepa.util.AT02Util;

import com.sepa.domain.v008.v001.v02.GenericOrganisationIdentification1;
import com.sepa.domain.v008.v001.v02.GenericPersonIdentification1;
import com.sepa.domain.v008.v001.v02.GroupHeader39;
import com.sepa.domain.v008.v001.v02.ObjectFactory;
import com.sepa.domain.v008.v001.v02.OrganisationIdentification4;
import com.sepa.domain.v008.v001.v02.Party6Choice;
import com.sepa.domain.v008.v001.v02.PartyIdentification32;
import com.sepa.domain.v008.v001.v02.PersonIdentification5;

import es.cnc.suscripciones.domain.CabeceraEmisionManual;
import es.cnc.suscripciones.domain.ParroquiaHasParroco;
import es.cnc.util.LocalDateUtil;
import es.cnc.util.sepa.ConverterUtils;

public class GroupHeaderBuilder {
	private static ObjectFactory oF = new ObjectFactory();

	/**
	 * Builds a GroupHeader with id, datetime, etc.
	 * @param cem
	 * @param order
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	public static GroupHeader39 buildGroupHeader(CabeceraEmisionManual cem) throws DatatypeConfigurationException {
		GroupHeader39 grpHdr = new GroupHeader39();
		
		// TODO [FMM] 2017-11-06 Ahora se actualiza la fecha de envío en la generación del XML
		LocalDateTime ldt = null;
		ldt = LocalDateUtil.dateToLocalDateTime(cem.getFechaEnvio());
		
		
		
		// Id
		grpHdr.setMsgId(buildGrpHdrId(cem.getParroquiaHasParroco().getParroquiaId().getNif(), cem.getId(), ldt));
		
		// CreDtTm
		try {
			grpHdr.setCreDtTm(ConverterUtils.buildCreDtTm(ldt));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		// NoOfTxs
		grpHdr.setNbOfTxs(ConverterUtils.buildNbOfTxs(cem.getEmisionManuals().size()));
		
		// CtrlSum
		grpHdr.setCtrlSum(ConverterUtils.buildCtrlSum(cem));
		
		grpHdr.setInitgPty(buildInitgPty(cem.getParroquiaHasParroco()));
		return grpHdr;
	}
		
	/**
	 * Build the String Identifier for the Direct Debit Initiation
	 *  (9)-()        -
	 *  NIF-NNNNNNNNNN-20160112235959
	 * @param nif
	 * @param idCabecera
	 * @param fechaEnvio
	 * @return
	 */
	private static String buildGrpHdrId(String nif, Integer idCabecera, LocalDateTime fechaEnvio) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		//Excede por 1 carácter
//		formatter.format("%1$9S%2$03d%3$TY%3$Tm%3$Td%3$TH%3$TM%3$TS%3$TN", nif.length()>9?nif.substring(0,8):nif, idCabecera, fechaEnvio);
		formatter.format("%1$9S%2$03d%3$TY%3$Tm%3$Td%3$TH%3$TM%3$TS", nif.length()>9?nif.substring(0,8):nif, idCabecera, fechaEnvio);
		formatter.close();
		return sb.toString();
	}
	
	/**
	 * Builds the 
	 * @param php
	 * @return
	 */
	private static PartyIdentification32 buildInitgPty(ParroquiaHasParroco php) {
		PartyIdentification32 init = oF.createPartyIdentification32();
		Party6Choice p6c = oF.createParty6Choice();
		
		PersonIdentification5 pi5 = oF.createPersonIdentification5();
		GenericPersonIdentification1 gpi1 = oF.createGenericPersonIdentification1();

		OrganisationIdentification4 oi4 = oF.createOrganisationIdentification4();
		GenericOrganisationIdentification1 goi1 = oF.createGenericOrganisationIdentification1();
		
		init.setNm(php.getParroquiaId().getNombre());
		
		// Private
		gpi1.setId(AT02Util.calcular(php.getParroquiaId().getParroquiaAux().getPais().getCodigoIso2(),
				"000", php.getParroquiaId().getNif()));
		pi5.getOthr().add(gpi1);
		
		// Organization
		goi1.setId(AT02Util.calcular(php.getParroquiaId().getParroquiaAux().getPais().getCodigoIso2(),
				"000", php.getParroquiaId().getNif()));
		oi4.getOthr().add(goi1);
		
		p6c.setOrgId(oi4);
//		p6c.setPrvtId(pi5);
		init.setId(p6c);
		return init;
	}

	public static void main(String[] args) {
//		buildGrpHdrId("0262568q", 3, LocalDateTime.now()); // 'R7800543F'
		
		System.out.println(buildGrpHdrId("R7800543F", 3, LocalDateTime.now())); // 'R7800543F'
	}

}
