package es.cnc.util;

import es.cnc.suscripciones.domain.Secuenciaadeudo;

public class ConstantsCNC {

	public ConstantsCNC() {
		// TODO Auto-generated constructor stub
	}

	public static final String CONCEPTO = "Financiación de la Iglesia Católica";
	public static final String DIVISA = "Eur";
	/**
	 * Secuencia Adeudo
	 */
	public static final Secuenciaadeudo FRST = new Secuenciaadeudo(1, "FRST");
	public static final Secuenciaadeudo RCUR = new Secuenciaadeudo(1, "RCUR");
	public static final Secuenciaadeudo FNAL = new Secuenciaadeudo(1, "FNAL");
	public static final Secuenciaadeudo OOFF = new Secuenciaadeudo(1, "OOFF");
	
}
