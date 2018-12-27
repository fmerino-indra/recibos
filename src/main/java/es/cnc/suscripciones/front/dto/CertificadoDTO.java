package es.cnc.suscripciones.front.dto;

import es.cnc.suscripciones.domain.Persona;

public class CertificadoDTO extends AbstractDTO {

	private Double sumImporte;
	private Integer year;
	private String nombre;
	private Long count;
	private Persona persona;
	private String nif;
	private Integer idPersona;
	
	public CertificadoDTO(Integer year, String nombre, Double sumImporte, Long count, String nif, Integer id) {
		super();
		this.sumImporte = sumImporte;
		this.year = year;
		this.nombre = nombre;
		this.count = count;
		this.setNif(nif);
		this.setIdPersona(id);
	}
	/**
	 * @return the sumImporte
	 */
	public Double getSumImporte() {
		return sumImporte;
	}
	/**
	 * @param sumImporte the sumImporte to set
	 */
	public void setSumImporte(Double sumImporte) {
		this.sumImporte = sumImporte;
	}
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.year);
		sb.append(" - ");
		sb.append(this.nombre);
		sb.append(" - ");
		sb.append(this.count);
		sb.append(" - ");
		sb.append(this.sumImporte);
		return sb.toString();
	}
	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}
	/**
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	public Integer getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}
}
