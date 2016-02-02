package es.cnc.suscripciones.services.generacion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import org.stasiena.sepa.util.AT02Util;
import org.stasiena.sepa.util.BICUtil;
import org.stasiena.sepa.util.IBANUtil;
import org.stasiena.sepa.util.SEPAUtil;

import com.sepa.domain.AccountIdentification4Choice;
import com.sepa.domain.ActiveOrHistoricCurrencyAndAmount;
import com.sepa.domain.BranchAndFinancialInstitutionIdentification4;
import com.sepa.domain.CashAccount16;
import com.sepa.domain.ChargeBearerType1Code;
import com.sepa.domain.DirectDebitTransaction6;
import com.sepa.domain.DirectDebitTransactionInformation9;
import com.sepa.domain.FinancialInstitutionIdentification7;
import com.sepa.domain.GenericPersonIdentification1;
import com.sepa.domain.LocalInstrument2Choice;
import com.sepa.domain.MandateRelatedInformation6;
import com.sepa.domain.ObjectFactory;
import com.sepa.domain.Party6Choice;
import com.sepa.domain.PartyIdentification32;
import com.sepa.domain.PaymentIdentification1;
import com.sepa.domain.PaymentInstructionInformation4;
import com.sepa.domain.PaymentMethod2Code;
import com.sepa.domain.PaymentTypeInformation20;
import com.sepa.domain.PersonIdentification5;
import com.sepa.domain.RemittanceInformation5;
import com.sepa.domain.SequenceType1Code;
import com.sepa.domain.ServiceLevel8Choice;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.domain.Parroquia;
import es.cnc.suscripciones.domain.ParroquiaHasParroco;
import es.cnc.util.ConverterUtils;
import es.cnc.util.LocalDateUtil;

public class PaymentInstructionBuilder {
	private static ObjectFactory oF = new ObjectFactory();

	/**
	 * <PmtInf> <PmtInfId>...</PmtInfId> <PmtMtd>...</PmtMtd>
	 * <NbOfTxs>...</NbOfTxs> <CtrlSum>...</CtrlSum> <PmtTpInf>...</PmtTpInf>
	 * <ReqdColltnDt>YYYY-MM-DD</ReqdColltnDt> <Cdtr>...</Cdtr>
	 * <CdtrAcct>...</CdtrAcct> <CdtrAgt>...</CdtrAgt> <ChrgBr>...</ChrgBr>
	 * <CdtrSchmeId>...</CdtrSchmeId> <DrctDbtTxInf>...</DrctDbtTxInf>
	 * 
	 * @param cabAux
	 * @param ddo
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	public static PaymentInstructionInformation4 buildPmtInf(Cabeceraemisiones cabAux)
			throws DatatypeConfigurationException {
		PaymentInstructionInformation4 payment = oF.createPaymentInstructionInformation4();

		payment.setPmtInfId(buildPmtInfId(cabAux));
		// Payment Method: Only DD (Direct Debit)
		payment.setPmtMtd(PaymentMethod2Code.DD);
		payment.setNbOfTxs(ConverterUtils.buildNbOfTxs(cabAux.getEmisions().size()));
		payment.setCtrlSum(ConverterUtils.buildCtrlSum(cabAux));
		payment.setPmtTpInf(buildPmtTpInf());
		payment.setReqdColltnDt(ConverterUtils.buildCreDtTm(cabAux.getFechaEnvio()));
		payment.setCdtr(buildCdtr(cabAux));
		payment.setCdtrAcct(buildCdtrAcct(cabAux));
		payment.setCdtrAgt(buildCdtrAgt(cabAux));
		payment.setChrgBr(ChargeBearerType1Code.SLEV);
		payment.setCdtrSchmeId(buildCdtrSchmeId(cabAux.getParroquiaHasParroco()));

		payment.getDrctDbtTxInf().addAll(buildDrctDbtTxInfList(cabAux));
		return payment;
	}

	private static String buildPmtInfId(Cabeceraemisiones ce) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format("%1$1S%2$04d%3$TY%3$Tm%3$Td%3$TH%3$TM%3$TS", ce.getPeriodo(), ce.getId(), ce.getFechaEnvio());
		formatter.close();
		return sb.toString();
	}

	/**
	 *
	 * <PmtTpInf> <SvcLvl> <Cd>SEPA</Cd> </SvcLvl>
	 * <LclInstrm> <Cd>COR1</Cd> </LclInstrm> <SeqTp>RCUR</SeqTp> </PmtTpInf>
	 *
	 * @param cabAux
	 * @return
	 */
	private static PaymentTypeInformation20 buildPmtTpInf() {
		PaymentTypeInformation20 pti20 = oF.createPaymentTypeInformation20();
		ServiceLevel8Choice sl8 = oF.createServiceLevel8Choice();
		LocalInstrument2Choice li2 = oF.createLocalInstrument2Choice();

		sl8.setCd("SEPA");
		li2.setCd("COR1");
		pti20.setSvcLvl(sl8);
		pti20.setLclInstrm(li2);
		pti20.setSeqTp(SequenceType1Code.RCUR);
		return pti20;

	}

	/**
	 * <Cdtr> <Nm>PARROQUIA STA. CATALINA DE SIENA</Nm> </Cdtr>
	 * 
	 * @param ce
	 * @return
	 */
	private static PartyIdentification32 buildCdtr(Cabeceraemisiones ce) {
		PartyIdentification32 pi32 = oF.createPartyIdentification32();
		pi32.setNm(ce.getConcepto());
		return pi32;
	}

	/**
	 * <CdtrAcct> <Id> <IBAN>ES1000810591350001251035</IBAN> </Id> </CdtrAcct>
	 * 
	 * @return
	 */
	private static CashAccount16 buildCdtrAcct(Cabeceraemisiones ce) {
		CashAccount16 ca16 = oF.createCashAccount16();
		AccountIdentification4Choice ai4 = oF.createAccountIdentification4Choice();
		String iban = null;

		Parroquia p = ce.getParroquiaHasParroco().getParroquiaId();
		String banco = p.getBanco();
		String sucursal = p.getSucursal();
		String DC = p.getDc();
		String cuenta = p.getCuenta();
		iban = IBANUtil.calcular(p.getParroquiaAux().getPais().getCodigoIso2(), banco, sucursal, DC, cuenta);
		ai4.setIBAN(iban);

		ca16.setId(ai4);
		return ca16;
	}

	/**
	 * <CdtrAgt> <FinInstnId> <BIC>BSABESBBXXX</BIC> </FinInstnId> </CdtrAgt>
	 */
	private static BranchAndFinancialInstitutionIdentification4 buildCdtrAgt(Cabeceraemisiones ce) {
		BranchAndFinancialInstitutionIdentification4 bafii4 = oF.createBranchAndFinancialInstitutionIdentification4();
		FinancialInstitutionIdentification7 fii7 = oF.createFinancialInstitutionIdentification7();

		fii7.setBIC(BICUtil.getBIC(ce.getParroquiaHasParroco().getParroquiaId().getBanco()));
		bafii4.setFinInstnId(fii7);
		return bafii4;
	}

	/**
	 * <CdtrSchmeId> <Id> <PrvtId> <Othr> <Id>ES63000R7800543F</Id> </Othr>
	 * </PrvtId> </Id> </CdtrSchmeId>
	 * 
	 * @return
	 */
	private static PartyIdentification32 buildCdtrSchmeId(ParroquiaHasParroco php) {
		PartyIdentification32 pi32 = oF.createPartyIdentification32();
		Party6Choice p6 = oF.createParty6Choice();
		PersonIdentification5 pi5 = oF.createPersonIdentification5();
		List<GenericPersonIdentification1> lista = new ArrayList<>(1);
		GenericPersonIdentification1 gpi1 = oF.createGenericPersonIdentification1();
		lista.add(gpi1);
		gpi1.setId(AT02Util.calcular(php.getParroquiaId().getParroquiaAux().getPais().getCodigoIso2(), "000",
				php.getParroquiaId().getNif()));

		pi5.getOthr().addAll(lista);
		p6.setPrvtId(pi5);
		pi32.setId(p6);
		return pi32;

	}

	/**
	 * <DrctDbtTxInf> List
	 * 
	 * @param cabecera
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private static List<DirectDebitTransactionInformation9> buildDrctDbtTxInfList(Cabeceraemisiones cabecera)
			throws DatatypeConfigurationException {
		List<DirectDebitTransactionInformation9> drctDbtTxInfList = new ArrayList<>();
		for (Emision em : cabecera.getEmisions()) {
			drctDbtTxInfList.add(buildDrctDbtTxInf(em));
		}
		return drctDbtTxInfList;
	}

	/**
	 * Builds DirectDebitTransactionInformation9
	 * 
	 * @param em
	 * @return
	 * 
	 * 		<DrctDbtTxInf> <PmtId> ... </PmtId> <InstdAmt> ... </InstdAmt>
	 *         <DrctDbtTx> ... </DrctDbtTx> <DbtrAgt>...</DbtrAgt>
	 *         <Dbtr>...</Dbtr> <DbtrAcct>...</DbtrAcct>
	 *         <RmtInf>...</RmtInf> </DrctDbtTxInf>
	 * 
	 * @throws DatatypeConfigurationException
	 */
	private static DirectDebitTransactionInformation9 buildDrctDbtTxInf(Emision em)
			throws DatatypeConfigurationException {
		DirectDebitTransactionInformation9 ddti9 = oF.createDirectDebitTransactionInformation9();

		ddti9.setPmtId(buildPmtId(em));
		ddti9.setInstdAmt(buildInstdAmt(em));
		ddti9.setDrctDbtTx(buildDrctDbtTx(em));
		ddti9.setDbtrAgt(buildDbtrAgt(em));
		ddti9.setDbtr(buildDbtr(em));
		ddti9.setDbtrAcct(buildDbtrAcct(em));
		ddti9.setRmtInf(buildRmtInf(em));
		return ddti9;
	}

	/**
	 * Builds PaymentIdentification1.EndToEnd
	 * 
	 * @param em
	 * @return
	 * 
	 * 		<PmtId> <InstrId/> <EndToEndId>00000248422 00958657V
	 *         20160112</EndToEndId> </PmtId>
	 * 
	 * 
	 */
	private static PaymentIdentification1 buildPmtId(Emision em) {
		PaymentIdentification1 pi1 = oF.createPaymentIdentification1();
		StringBuilder sb = new StringBuilder();
		sb.append(SEPAUtil.lPad(em.getId().toString(), 11, '0'));
		sb.append(" ");
		sb.append(SEPAUtil.lPad(em.getIdSuscripcion().getIdSuscripcion().getPersona().getNif(), 9, ' '));
		sb.append(" ");

		Formatter formatter = new Formatter(sb);
		formatter.format("%1$TY%1$Tm%1$Td", LocalDateUtil.dateToLocalDate(em.getIdCabecera().getFechaEnvio()));
		formatter.close();

		pi1.setEndToEndId(sb.toString());
		return pi1;
	}

	/**
	 * Builds ActiveOrHistoricCurrencyAndAmount.InstdAmt
	 * 
	 * @param em
	 * @return
	 * 
	 * 		<InstdAmt Ccy="EUR"> <Value>601.01</Value> </InstdAmt>
	 */
	private static ActiveOrHistoricCurrencyAndAmount buildInstdAmt(Emision em) {
		ActiveOrHistoricCurrencyAndAmount amount = oF.createActiveOrHistoricCurrencyAndAmount();
		amount.setCcy(em.getIdSuscripcion().getIdSuscripcion().getDivisa().toUpperCase());
		amount.setValue(BigDecimal.valueOf(em.getImporte()).setScale(2, RoundingMode.HALF_UP));
		return amount;
	}

	/**
	 * <DrctDbtTx> <MndtRltdInf> <MndtId>000000000024</MndtId>
	 * <DtOfSgntr>2009-10-31</DtOfSgntr> </MndtRltdInf> </DrctDbtTx>
	 * 
	 * @param em
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private static DirectDebitTransaction6 buildDrctDbtTx(Emision em) throws DatatypeConfigurationException {
		DirectDebitTransaction6 ddt6 = oF.createDirectDebitTransaction6();
		MandateRelatedInformation6 mri6 = oF.createMandateRelatedInformation6();
		mri6.setMndtId(SEPAUtil.lPad(em.getIdSuscripcion().getId().toString(), 35, '0'));
		mri6.setDtOfSgntr(ConverterUtils.buildCreDtTm(em.getIdSuscripcion().getFechaFirma()));

		ddt6.setMndtRltdInf(mri6);
		return ddt6;
	}

	/**
	 * <DbtrAgt> <FinInstnId> </FinInstnId> </DbtrAgt>
	 * 
	 * This tag is mandatory but is empty.
	 * 
	 * @param em
	 * @return
	 */
	private static BranchAndFinancialInstitutionIdentification4 buildDbtrAgt(Emision em) {
		BranchAndFinancialInstitutionIdentification4 bfii4 = oF.createBranchAndFinancialInstitutionIdentification4();
		bfii4.setFinInstnId(oF.createFinancialInstitutionIdentification7());
		return bfii4;
	}

	/**
	 * <Dbtr> <Nm>AP1 AP2, NOMBRE</Nm> </Dbtr>
	 * 
	 * @param em
	 * @return
	 */
	private static PartyIdentification32 buildDbtr(Emision em) {
		PartyIdentification32 pi32 = oF.createPartyIdentification32();
		pi32.setNm(em.getIdSuscripcion().getIdSuscripcion().getPersona().getNombre());
		return pi32;
	}

	private static CashAccount16 buildDbtrAcct(Emision em) {
		CashAccount16 ca16 = oF.createCashAccount16();
		AccountIdentification4Choice ai4 = oF.createAccountIdentification4Choice();

		ai4.setIBAN(em.getIdSuscripcion().getIdDomiciliacion().getIban());
		ca16.setId(ai4);
		return ca16;
	}

	private static RemittanceInformation5 buildRmtInf(Emision em) {
		RemittanceInformation5 ri5 = oF.createRemittanceInformation5();
		ri5.getUstrd().add(em.getIdSuscripcion().getIdSuscripcion().getConcepto());
		return ri5;
	}
}
