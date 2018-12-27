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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "cabecera_emision_manual")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class CabeceraEmisionManual extends AbstractEntity<Integer>{
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
    
    public CabeceraEmisionManual() {
    	super(CabeceraEmisionManual.class);
	}

    @Column(name = "FechaEmision", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private Date fechaEmision;
    
    @Column(name = "FechaEnvio")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private Date fechaEnvio;
    
    @OneToMany(mappedBy = "idCabecera", fetch = FetchType.LAZY)
    private List<EmisionManual> emisionManuals;
    
//    @OneToMany(mappedBy = "idCabecera", fetch = FetchType.LAZY)
//    @OrderColumn(name="id")
//    @OrderBy("id ASC")
//    private List<SepaCoreXml> sepaCoreXMLs;
    
    @Column(name = "CodigoMes")
    private Integer codigoMes;
    
    @Column(name="anyo")
    private Integer anyo;
    
    @Column(name = "Concepto", length = 80)
    private String concepto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Parroquia_has_parroco", referencedColumnName = "id")
    private ParroquiaHasParroco parroquiaHasParroco;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domiciliacion", referencedColumnName = "idDomiciliacion")
    private Domiciliacion domiciliacion;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSolicitud", referencedColumnName = "id")
    private SolicitudEmisionManual solicitud;
    
    public List<EmisionManual> getEmisionManuals() {
    	if (emisionManuals == null)
    		emisionManuals = new ArrayList<>();
        return emisionManuals;
    }
    
    public void setEmisionManuals(List<EmisionManual> emisionManuals) {
        this.emisionManuals = emisionManuals;
    }
    
    public Date getFechaEmision() {
        return fechaEmision;
    }
    
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    public Date getFechaEnvio() {
        return fechaEnvio;
    }
    
    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    
    public Integer getCodigoMes() {
        return codigoMes;
    }
    
    public void setCodigoMes(Integer codigoMes) {
        this.codigoMes = codigoMes;
    }
    
    public String getConcepto() {
        return concepto;
    }
    
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("emisionManuals").toString();
    }

	public ParroquiaHasParroco getParroquiaHasParroco() {
		return parroquiaHasParroco;
	}

	public void setParroquiaHasParroco(ParroquiaHasParroco parroquiaHasParroco) {
		this.parroquiaHasParroco = parroquiaHasParroco;
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

	public SolicitudEmisionManual getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudEmisionManual solicitud) {
		this.solicitud = solicitud;
	}

//	public List<SepaCoreXml> getSepaCoreXMLs() {
//		return sepaCoreXMLs;
//	}
//
//	public void setSepaCoreXMLs(List<SepaCoreXml> sepaCoreXMLs) {
//		this.sepaCoreXMLs = sepaCoreXMLs;
//	}
}
