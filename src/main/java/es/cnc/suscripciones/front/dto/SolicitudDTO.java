package es.cnc.suscripciones.front.dto;

import es.cnc.suscripciones.domain.Domiciliacion;
import es.cnc.suscripciones.domain.Persona;

public class SolicitudDTO extends AbstractDTO {

	private Double importe;
	private Persona persona;
	private Domiciliacion domiciliacion;
	
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Domiciliacion getDomiciliacion() {
		return domiciliacion;
	}
	public void setDomiciliacion(Domiciliacion domiciliacion) {
		this.domiciliacion = domiciliacion;
	}
	
}
