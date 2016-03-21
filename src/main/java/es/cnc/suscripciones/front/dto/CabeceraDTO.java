package es.cnc.suscripciones.front.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class CabeceraDTO {

	public CabeceraDTO() {
		// TODO Auto-generated constructor stub
	}

	private Integer id;	
    private Date fechaEmision;
    private Integer codigoMes;
    private String periodo;
    private Boolean emisionManual;
    private Date fechaEnvio;
    private Double importe;
    private Double importeDevuelto;
    private String concepto;
    private List<EmisionDTO> emisiones;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Integer getCodigoMes() {
		return codigoMes;
	}

	public void setCodigoMes(Integer codigoMes) {
		this.codigoMes = codigoMes;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Boolean getEmisionManual() {
		return emisionManual;
	}

	public void setEmisionManual(Boolean emisionManual) {
		this.emisionManual = emisionManual;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getImporteDevuelto() {
		return importeDevuelto;
	}

	public void setImporteDevuelto(Double importeDevuelto) {
		this.importeDevuelto = importeDevuelto;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public List<EmisionDTO> getEmisiones() {
		return emisiones;
	}

	public void setEmisiones(List<EmisionDTO> emisiones) {
		this.emisiones = emisiones;
	}

}
