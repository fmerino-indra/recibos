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
	
	
	public DashboardDTO(Integer id,Integer anyo, Integer codigoMes, String periodo, Long numEmitidos, Long numDevueltos, Double emitidos, Double devueltos) {
		super();
		this.id=id;
		this.anyo=anyo.shortValue();
		this.mes=codigoMes;
		this.periodo = periodo;
		this.numEmitidos = numEmitidos;
		this.numDevueltos = numDevueltos;
		this.emitidos = emitidos;
		this.devueltos = devueltos;
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
}
