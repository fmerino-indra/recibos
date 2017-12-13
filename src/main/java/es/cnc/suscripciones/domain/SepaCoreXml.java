package es.cnc.suscripciones.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "sepa_core_xml")
public class SepaCoreXml extends AbstractEntity<Integer> {
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
    public SepaCoreXml() {
    	super(SepaCoreXml.class);
	}
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cabeceraemisiones_id", referencedColumnName = "id")
    private Cabeceraemisiones idCabecera;
    
    @Column(name = "fecha_envio")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private Date fechaEnvio;
    
    
    
    @Column(name = "sepa_core_xml")
    private String xml;
    
    @Column(name = "id_msg")
    private String idMsg;
    
    @Column(name = "hash")
    private String hash;
    
    @Column(name = "activo")
    private Boolean activo;
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("idCabecera").toString();
    }

	/**
	 * @return the idCabecera
	 */
	public Cabeceraemisiones getIdCabecera() {
		return idCabecera;
	}

	/**
	 * @param idCabecera the idCabecera to set
	 */
	public void setIdCabecera(Cabeceraemisiones idCabecera) {
		this.idCabecera = idCabecera;
	}

	/**
	 * @return the xml
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * @param xml the xml to set
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getIdMsg() {
		return idMsg;
	}

	public void setIdMsg(String idMsg) {
		this.idMsg = idMsg;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
