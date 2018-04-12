package es.cnc.suscripciones.front.dto.reports;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import es.cnc.suscripciones.front.dto.AbstractDTO;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="codigoMes")
public class DevolucionesReportDTO extends AbstractDTO {

	private Integer id;
	private Integer anyo;
    private Integer codigoMes;
    private String descMes;
    private String nombrePersona;
    private String telefono;
    private String movil;
    private Date fechaDevolucion;
    private Double importe;
    private String periodo;
    private String reasonCode;
    private String reasonDescription;
    

    public DevolucionesReportDTO() {
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getAnyo() {
		return anyo;
	}


	public void setAnyo(Integer anyo) {
		this.anyo = anyo;
	}


	public Integer getCodigoMes() {
		return codigoMes;
	}


	public void setCodigoMes(Integer codigoMes) {
		this.codigoMes = codigoMes;
	}


	public String getNombrePersona() {
		return nombrePersona;
	}


	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}


	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}


	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}


	public Double getImporte() {
		return importe;
	}


	public void setImporte(Double importe) {
		this.importe = importe;
	}


	public String getPeriodo() {
		return periodo;
	}


	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}


	public String getDescMes() {
		return descMes;
	}


	public void setDescMes(String descMes) {
		this.descMes = descMes;
	}


	public String getReasonCode() {
		return reasonCode;
	}


	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}


	public String getReasonDescription() {
		return reasonDescription;
	}


	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getMovil() {
		return movil;
	}


	public void setMovil(String movil) {
		this.movil = movil;
	}
}
