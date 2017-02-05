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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "parroquia_iban")
public class ParroquiaIban extends AbstractEntity<Integer> {
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
    
    public ParroquiaIban() {
    	super(ParroquiaIban.class);
	}

    @OneToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "parroquia", referencedColumnName = "Id")
    @JsonIgnore
    private Parroquia parroquia;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "domiciliacion", referencedColumnName = "IdDomiciliacion", nullable = false)
    private Domiciliacion domiciliacion;
    
    
    @Column(name = "Activo")
    private Boolean activo;
    
    @Column(name = "Inicio")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date inicio;
    
    @Column(name = "Fin")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date fin;
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("idParroquia").toString();
    }

	/**
	 * @return the parroquia
	 */
	public Parroquia getParroquia() {
		return parroquia;
	}

	/**
	 * @param parroquia the parroquia to set
	 */
	public void setParroquia(Parroquia parroquia) {
		this.parroquia = parroquia;
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

	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the inicio
	 */
	public Date getInicio() {
		return inicio;
	}

	/**
	 * @param inicio the inicio to set
	 */
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	/**
	 * @return the fin
	 */
	public Date getFin() {
		return fin;
	}

	/**
	 * @param fin the fin to set
	 */
	public void setFin(Date fin) {
		this.fin = fin;
	}
    
}
