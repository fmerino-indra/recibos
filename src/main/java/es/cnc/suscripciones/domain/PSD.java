package es.cnc.suscripciones.domain;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "p_s_d")
public class PSD extends AbstractEntity<Integer> {
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
    
    public PSD() {
    	super(PSD.class);
	}
    @OneToMany(mappedBy = "idSuscripcion", fetch = FetchType.LAZY)
    private Set<Emision> emisions;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDomiciliacion", referencedColumnName = "IdDomiciliacion", nullable = false)
    private Domiciliacion idDomiciliacion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdSuscripcion", referencedColumnName = "IdSuscripcion", nullable = false)
    private Suscripcion idSuscripcion;
    
    @Column(name = "FechaInicio")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date fechaInicio;
    
    @Column(name = "FechaBaja")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date fechaBaja;
    
    @Column(name = "idPAntiguo")
    private Integer idPantiguo;
    
    @Column(name = "idSAntiguo")
    private Integer idSantiguo;
    
    @Column(name = "idDAntiguo")
    private Integer idDantiguo;
    
    @Column(name = "FechaFirma")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date fechaFirma;
    
    public Set<Emision> getEmisions() {
        return emisions;
    }
    
    public void setEmisions(Set<Emision> emisions) {
        this.emisions = emisions;
    }
    
    public Domiciliacion getIdDomiciliacion() {
        return idDomiciliacion;
    }
    
    public void setIdDomiciliacion(Domiciliacion idDomiciliacion) {
        this.idDomiciliacion = idDomiciliacion;
    }
    
    public Suscripcion getIdSuscripcion() {
        return idSuscripcion;
    }
    
    public void setIdSuscripcion(Suscripcion idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
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
    
    public Integer getIdPantiguo() {
        return idPantiguo;
    }
    
    public void setIdPantiguo(Integer idPantiguo) {
        this.idPantiguo = idPantiguo;
    }
    
    public Integer getIdSantiguo() {
        return idSantiguo;
    }
    
    public void setIdSantiguo(Integer idSantiguo) {
        this.idSantiguo = idSantiguo;
    }
    
    public Integer getIdDantiguo() {
        return idDantiguo;
    }
    
    public void setIdDantiguo(Integer idDantiguo) {
        this.idDantiguo = idDantiguo;
    }
    
    public Date getFechaFirma() {
        return fechaFirma;
    }
    
    public void setFechaFirma(Date fechaFirma) {
        this.fechaFirma = fechaFirma;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("emisions", "idDomiciliacion", "idSuscripcion").toString();
    }
}
