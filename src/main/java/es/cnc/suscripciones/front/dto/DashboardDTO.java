package es.cnc.suscripciones.front.dto;

public class DashboardDTO extends AbstractDTO {

	private Integer id;
	private Short anyo;
	private Integer mes;
	private String nombreMes;
	private String periodo;
	private String nombrePeriodo;
	private Long numEmitidos;
	private Long numDevueltos;
	private Double emitidos;
	private Double devueltos;
	
	public DashboardDTO() {
		super();
		reset();
	}
	
	public DashboardDTO(Integer id,Integer anyo, Integer codigoMes, String periodo, Long numEmitidos, Long numDevueltos, Double emitidos, Double devueltos) {
		super();
		this.id=id;
		this.anyo=anyo.shortValue();
		this.mes=codigoMes;
		this.periodo = periodo;
		this.numEmitidos = numEmitidos;
		this.numDevueltos = numDevueltos;
		this.emitidos = round(emitidos,2);
		this.devueltos = round(devueltos,2);
	}

	private void reset() {
		anyo = 0;
		mes = 0;
		nombreMes = "";
		periodo = "";
		nombrePeriodo = "";
		numEmitidos = 0l;
		numDevueltos = 0l;
		emitidos = 0.0;
		devueltos = 0.0;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public String getNombreMes() {
		return nombreMes;
	}
	public void setNombreMes(String nombreMes) {
		this.nombreMes = nombreMes;
	}
	public Short getAnyo() {
		return anyo;
	}
	public void setAnyo(Short anyo) {
		this.anyo = anyo;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getNombrePeriodo() {
		return nombrePeriodo;
	}
	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
	}
	@Override
	public String toString() {
		return anyo + "-" + mes + "-" + periodo + "-" + numEmitidos + "-" + numDevueltos + "-" + emitidos + "-" + devueltos;
	}

	public Long getNumEmitidos() {
		return numEmitidos;
	}

	public void setNumEmitidos(Long numEmitidos) {
		this.numEmitidos = numEmitidos;
	}

	public Long getNumDevueltos() {
		return numDevueltos;
	}

	public void setNumDevueltos(Long numDevueltos) {
		this.numDevueltos = numDevueltos;
	}

	public Double getEmitidos() {
		return emitidos;
	}

	public void setEmitidos(Double emitidos) {
		this.emitidos = emitidos;
	}

	public Double getDevueltos() {
		return devueltos;
	}

	public void setDevueltos(Double devueltos) {
		this.devueltos = devueltos;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
