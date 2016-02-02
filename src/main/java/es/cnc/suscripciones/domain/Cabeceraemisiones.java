package es.cnc.suscripciones.domain;
import java.util.Date;
import java.util.HashSet;
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
@Table(name = "cabeceraemisiones")
public class Cabeceraemisiones extends AbstractEntity<Integer>{
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
    
    
    @OneToMany(mappedBy = "idCabecera", fetch = FetchType.LAZY)
    private Set<Emision> emisions;
    
    @Column(name = "FechaEmision", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date fechaEmision;
    
    @Column(name = "CodigoMes")
    private Integer codigoMes;
    
    @Column(name = "Periodo", length = 1)
    private String periodo;
    
    @Column(name = "EmisionManual")
    private Boolean emisionManual;
    
    @Column(name = "FechaEnvio")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date fechaEnvio;
    
    @Column(name = "Importe", precision = 22)
    private Double importe;
    
    @Column(name = "ImporteDevuelto", precision = 22)
    private Double importeDevuelto;
    
    @Column(name = "Concepto", length = 40)
    private String concepto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Parroquia_has_parroco", referencedColumnName = "id")
    private ParroquiaHasParroco parroquiaHasParroco;
    
    public Set<Emision> getEmisions() {
    	if (emisions == null)
    		emisions = new HashSet<>();
        return emisions;
    }
    
    public void setEmisions(Set<Emision> emisions) {
        this.emisions = emisions;
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
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("emisions").toString();
    }

	public ParroquiaHasParroco getParroquiaHasParroco() {
		return parroquiaHasParroco;
	}

	public void setParroquiaHasParroco(ParroquiaHasParroco parroquiaHasParroco) {
		this.parroquiaHasParroco = parroquiaHasParroco;
	}
}
