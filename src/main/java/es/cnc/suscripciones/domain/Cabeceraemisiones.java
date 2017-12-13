package es.cnc.suscripciones.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "cabeceraemisiones")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
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
    
    public Cabeceraemisiones() {
    	super(Cabeceraemisiones.class);
	}
    @OneToMany(mappedBy = "idCabecera", fetch = FetchType.LAZY)
    @OrderColumn(name="id")
    @OrderBy("id ASC")
    private List<Emision> emisions;
    
    @OneToMany(mappedBy = "idCabecera", fetch = FetchType.LAZY)
    @OrderColumn(name="id")
    @OrderBy("id ASC")
    private List<SepaCoreXml> sepaCoreXMLs;
    
    @Column(name = "FechaEmision", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
//  @DateTimeFormat(style = "M-")
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private Date fechaEmision;
    
    @Column(name = "CodigoMes")
    private Integer codigoMes;
    
    @Column(name = "Periodo", length = 1)
    private String periodo;
    
    @Column(name = "EmisionManual")
    private Boolean emisionManual;
    
    @Column(name = "FechaEnvio")
    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(style = "M-")
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private Date fechaEnvio;
    
    @Transient
    @JsonProperty("importe")
    private Double importe;
    
    @Transient
    @JsonProperty("importeDevuelto")
    private Double importeDevuelto;
    
    @Transient
    @JsonProperty("devueltos")
    private Long devueltos;
    
    @Column(name = "Concepto", length = 40)
    private String concepto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Parroquia_has_parroco", referencedColumnName = "id")
    private ParroquiaHasParroco parroquiaHasParroco;
    
    @Column(name="anyo")
    private Integer anyo;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domiciliacion", referencedColumnName = "idDomiciliacion")
    private Domiciliacion domiciliacion;
    
    public List<Emision> getEmisions() {
    	if (emisions == null)
    		emisions = new ArrayList<>();
        return emisions;
    }
    
    public void setEmisions(List<Emision> emisions) {
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

	public Long getDevueltos() {
		return devueltos;
	}

	public void setDevueltos(Long devueltos) {
		this.devueltos = devueltos;
	}

	/**
	 * @return the anyo
	 */
	public Integer getAnyo() {
		return anyo;
	}

	/**
	 * @param anyo the anyo to set
	 */
	public void setAnyo(Integer anyo) {
		this.anyo = anyo;
	}

	/**
	 * @return the domiciliacion
	 */
	public Domiciliacion getDomiciliacion() {
		return domiciliacion;
	}

	/**
	 * @param domiciliacion the domiciliacion to set
	 */
	public void setDomiciliacion(Domiciliacion domiciliacion) {
		this.domiciliacion = domiciliacion;
	}

	public List<SepaCoreXml> getSepaCoreXMLs() {
		return sepaCoreXMLs;
	}

	public void setSepaCoreXMLs(List<SepaCoreXml> sepaCoreXMLs) {
		this.sepaCoreXMLs = sepaCoreXMLs;
	}
}
