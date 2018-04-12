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
@Table(name = "devoluciones")
public class Devoluciones extends AbstractEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmision", referencedColumnName = "id")
    private Emision emision;
    
    @Column(name = "fechaDevolución", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private Date fechaDevolucion;

    @Column(name = "fechaBaja", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private Date fechaBaja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reasons_id", referencedColumnName = "id")
    private Reason reason;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMsgDevolucion", referencedColumnName = "id")
    private MsgDevolucion idMsgDevolucion;

    public Devoluciones() {
    	super(Devoluciones.class);
	}

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("idCabecera", "idSuscripcion").toString();
    }

	public Emision getEmision() {
		return emision;
	}

	public void setEmision(Emision emision) {
		this.emision = emision;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

	public MsgDevolucion getIdMsgDevolucion() {
		return idMsgDevolucion;
	}

	public void setIdMsgDevolucion(MsgDevolucion idMsgDevolucion) {
		this.idMsgDevolucion = idMsgDevolucion;
	}
}
