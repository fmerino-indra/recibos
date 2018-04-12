package es.cnc.suscripciones.dto;

public enum PeriodoEnum {

	MENSUAL("M","Mensual"),
	TRIMESTRAL("T","Trimestral"),
	SEMESTRAL("S","Semestral"),
	ANUAL("A","Anual");
	
	PeriodoEnum(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	private String id;
	private String nombre;
}
