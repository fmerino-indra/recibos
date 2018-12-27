package es.cnc.suscripciones.domain;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "solicitud_emision_manual")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class SolicitudEmisionManual extends AbstractEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "FechaSolicitud", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private Date fechaSolicitud;
    
    @Column(name = "Concepto", length = 40)
    private String concepto;
    
    @OneToMany(mappedBy = "solicitudEmisionManual", fetch = FetchType.LAZY)
    private List<DetalleSolicitudEmision> solicitudEmisiones;
    
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public SolicitudEmisionManual() {
    	super(SolicitudEmisionManual.class);
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

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public List<DetalleSolicitudEmision> getSolicitudEmisiones() {
		return solicitudEmisiones;
	}

	public void setSolicitudEmisiones(List<DetalleSolicitudEmision> solicitudEmisiones) {
		this.solicitudEmisiones = solicitudEmisiones;
	}

}
