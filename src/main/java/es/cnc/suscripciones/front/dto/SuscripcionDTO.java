package es.cnc.suscripciones.front.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SuscripcionDTO extends AbstractDTO {

	private Integer id;
    private Date fechaInicio;
    private Date fechaBaja;
	private String nombre;
	private Double euros;
	private String divisa;
	private String periodo;
    private Boolean activo;
	private String concepto;
	private String iban;
	private String telefono;
	private List<SuscripcionDTO> others;
	
	public SuscripcionDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getEuros() {
		return euros;
	}

	public void setEuros(Double euros) {
		this.euros = euros;
	}

	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the others
	 */
	public List<SuscripcionDTO> getOthers() {
		return others;
	}

	/**
	 * @param others the others to set
	 */
	public void setOthers(List<SuscripcionDTO> others) {
		this.others = others;
	}
	
	public void addSuscripcionDtoHistory(SuscripcionDTO dto) {
		if (others == null)
			others = new ArrayList<>();
		others.add(dto);
	}
}
