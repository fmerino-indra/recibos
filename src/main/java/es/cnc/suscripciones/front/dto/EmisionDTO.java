package es.cnc.suscripciones.front.dto;

public class EmisionDTO {

	public EmisionDTO() {
		// TODO Auto-generated constructor stub
	}
    private Integer id;
    private Boolean devuelto;
    
    private Double importe;
    
    private String divisa;
    
    private Boolean reenviado;
    
    private Boolean primero;
    
    private Boolean ultimo;

    private String titular;
    private Integer idTitular;
    
    private CabeceraDTO cabeceraDTO;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getDevuelto() {
		return devuelto;
	}
	public void setDevuelto(Boolean devuelto) {
		this.devuelto = devuelto;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public Boolean getReenviado() {
		return reenviado;
	}
	public void setReenviado(Boolean reenviado) {
		this.reenviado = reenviado;
	}
	public Boolean getPrimero() {
		return primero;
	}
	public void setPrimero(Boolean primero) {
		this.primero = primero;
	}
	public Boolean getUltimo() {
		return ultimo;
	}
	public void setUltimo(Boolean ultimo) {
		this.ultimo = ultimo;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	public Integer getIdTitular() {
		return idTitular;
	}
	public void setIdTitular(Integer idTitular) {
		this.idTitular = idTitular;
	}
	public CabeceraDTO getCabeceraDTO() {
		return cabeceraDTO;
	}
	public void setCabeceraDTO(CabeceraDTO cabeceraDTO) {
		this.cabeceraDTO = cabeceraDTO;
	}
}
