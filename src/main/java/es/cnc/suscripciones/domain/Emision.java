package es.cnc.suscripciones.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "emision")
public class Emision extends AbstractEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Emision() {
    	super(Emision.class);
	}
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCabecera", referencedColumnName = "id")
    private Cabeceraemisiones idCabecera;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSuscripcion", referencedColumnName = "id")
    private PSD idSuscripcion;
    
    @Column(name = "Devuelto")
    private Boolean devuelto;
    
    @Column(name = "Importe", precision = 22)
    private Double importe;
    
    @Column(name = "Divisa", length = 3)
    private String divisa;
    
    @Column(name = "Reenviado")
    private Boolean reenviado;
    
    @Column(name = "primero")
    private Boolean primero;
    
    @Column(name = "ultimo")
    private Boolean ultimo;
    
    @Column(name = "idPersonaAntigua")
    private Integer idPersonaAntigua;
    
    @Column(name = "idSuscripcionAntigua")
    private Integer idSuscripcionAntigua;
    
    public Cabeceraemisiones getIdCabecera() {
        return idCabecera;
    }
    
    public void setIdCabecera(Cabeceraemisiones idCabecera) {
        this.idCabecera = idCabecera;
    }
    
    public PSD getIdSuscripcion() {
        return idSuscripcion;
    }
    
    public void setIdSuscripcion(PSD idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
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
    
    public Integer getIdPersonaAntigua() {
        return idPersonaAntigua;
    }
    
    public void setIdPersonaAntigua(Integer idPersonaAntigua) {
        this.idPersonaAntigua = idPersonaAntigua;
    }
    
    public Integer getIdSuscripcionAntigua() {
        return idSuscripcionAntigua;
    }
    
    public void setIdSuscripcionAntigua(Integer idSuscripcionAntigua) {
        this.idSuscripcionAntigua = idSuscripcionAntigua;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("idCabecera", "idSuscripcion").toString();
    }
}
