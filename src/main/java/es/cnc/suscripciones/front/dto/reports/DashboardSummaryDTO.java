package es.cnc.suscripciones.front.dto.reports;

import java.util.Formatter;

import es.cnc.suscripciones.front.dto.AbstractDTO;
import es.cnc.suscripciones.front.dto.DashboardDTO;

public class DashboardSummaryDTO extends AbstractDTO {

	private Short anyo;
	private Integer mes;
	private DashboardDTO anual;
	private DashboardDTO semestral;
	private DashboardDTO trimestral;
	private DashboardDTO mensual;
	
	public DashboardSummaryDTO(Short anyo, Integer mes) {
		super();
		this.anyo = anyo;
		this.mes = mes;
		anual = new DashboardDTO();
		semestral = new DashboardDTO();
		trimestral = new DashboardDTO();
		mensual = new DashboardDTO();
	}
	
	public Short getAnyo() {
		return anyo;
	}

	public Integer getMes() {
		return mes;
	}

	public Double getMensuales() {
		if (mensual != null) {
			return mensual.getEmitidos();
		} else
			return 0.0;
	}
	
	public Double getMensualesDevueltos() {
		if (mensual != null) {
			return mensual.getDevueltos();
		} else
			return 0.0;
	}
	
	public Long getNumMensuales() {
		if (mensual != null) {
			return mensual.getNumEmitidos();
		} else
			return 0l;
	}
	
	public Long getNumMensualesDevueltos() {
		if (mensual != null) {
			return mensual.getNumDevueltos();
		} else
			return 0l;
	}
	
	public Double getTrimestrales() {
		if (trimestral != null) {
			return trimestral.getEmitidos();
		} else
			return 0.0;
	}
	
	public Double getTrimestralesDevueltos() {
		if (trimestral != null) {
			return trimestral.getDevueltos();
		} else
			return 0.0;
	}
	
	public Long getNumTrimestrales() {
		if (trimestral != null) {
			return trimestral.getNumEmitidos();
		} else
			return 0l;
	}
	
	public Long getNumTrimestralesDevueltos() {
		if (trimestral != null) {
			return trimestral.getNumDevueltos();
		} else
			return 0l;
	}
	
	public Double getSemestrales() {
		if (semestral != null) {
			return semestral.getEmitidos();
		} else
			return 0.0;
	}
	
	public Double getSemestralesDevueltos() {
		if (semestral != null) {
			return semestral.getDevueltos();
		} else
			return 0.0;
	}
	
	public Long getNumSemestrales() {
		if (semestral != null) {
			return semestral.getNumEmitidos();
		} else
			return 0l;
	}
	
	public Long getNumSemestralesDevueltos() {
		if (semestral != null) {
			return semestral.getNumDevueltos();
		} else
			return 0l;
	}
	
	public Double getAnuales() {
		if (anual != null) {
			return anual.getEmitidos();
		} else
			return 0.0;
	}
	
	public Double getAnualesDevueltos() {
		if (anual != null) {
			return anual.getDevueltos();
		} else
			return 0.0;
	}
	
	public Long getNumAnuales() {
		if (anual != null) {
			return anual.getNumEmitidos();
		} else
			return 0l;
	}
	
	public Long getNumAnualesDevueltos() {
		if (anual != null) {
			return anual.getNumDevueltos();
		} else
			return 0l;
	}

	public Double getTotales() {
		return getMensuales() + getTrimestrales() + getSemestrales() + getAnuales();
	}
	
	public Double getTotalesDevueltos() {
		return getMensualesDevueltos() + getTrimestralesDevueltos() + getSemestralesDevueltos() + getAnualesDevueltos();
	}
	
	public Long getNumTotales() {
		return getNumMensuales() + getNumTrimestrales() + getNumSemestrales() + getNumAnuales();
	}
	
	public Long getNumTotalesDevueltos() {
		return getNumMensualesDevueltos() + getNumTrimestralesDevueltos() + getNumSemestralesDevueltos() + getNumAnualesDevueltos();
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	public void addDto(DashboardDTO mesPeriodoDto) {
		if (mesPeriodoDto != null) {
			if (mesPeriodoDto.getPeriodo().equals("M"))
				setMensual(mesPeriodoDto);
			else if (mesPeriodoDto.getPeriodo().equals("T"))
				setTrimestral(mesPeriodoDto);
			else if (mesPeriodoDto.getPeriodo().equals("S"))
				setSemestral(mesPeriodoDto);
			else if (mesPeriodoDto.getPeriodo().equals("A"))
				setAnual(mesPeriodoDto);
		}
	}

	public void setAnual(DashboardDTO anual) {
		this.anual = anual;
	}


	public void setSemestral(DashboardDTO semestral) {
		this.semestral = semestral;
	}


	public void setTrimestral(DashboardDTO trimestral) {
		this.trimestral = trimestral;
	}


	public void setMensual(DashboardDTO mensual) {
		this.mensual = mensual;
	}
	
	public static String calcKey(Short anyo, Integer mes) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format("%1$04d%2$1S%3$02d", anyo, "-", mes);
		formatter.close();
		return sb.toString();
	}
	
	public static Short parseAnyo(String key) {
		Short anyo=null;
		String [] aux = key.split("-");
		anyo = new Short(aux[0]);
		return anyo;
	}
	
	public static Integer parseMes(String key) {
		Integer mes=null;
		String [] aux = key.split("-");
		mes = new Integer(aux[1]);
		return mes;
	}
	
	public static void main(String[] args) {
		String aux;
		Short anyo;
		Integer mes;
		aux=calcKey((short)2018, 1);
		System.out.println(aux);
		anyo = parseAnyo(aux);
		mes = parseMes(aux);
		System.out.println(anyo + "/" + mes);
	}
}
