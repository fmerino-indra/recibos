package es.cnc.suscripciones.domain;
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

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "detalle_solicitud_emision")
public class DetalleSolicitudEmision extends AbstractEntity<Integer> {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private Integer id;

    @Column(name = "Importe", precision = 22)
	private Double importe;

    @Column(name = "divisa", length=3)
	private String divisa;

	//bi-directional many-to-one association to Domiciliacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDomiciliacion", referencedColumnName = "idDomiciliacion")
	private Domiciliacion domiciliacion;

	//bi-directional many-to-one association to SolicitudEmisionManual
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSolicitud", referencedColumnName = "id")
	private SolicitudEmisionManual solicitudEmisionManual;

	//bi-directional one-to-one association to EmisionManual
    @OneToOne(mappedBy="detalleSolicitudEmision")
	private EmisionManual emisionManual;

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public DetalleSolicitudEmision() {
    	super(DetalleSolicitudEmision.class);
	}
    
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Domiciliacion getDomiciliacion() {
		return domiciliacion;
	}

	public void setDomiciliacion(Domiciliacion domiciliacion) {
		this.domiciliacion = domiciliacion;
	}

	public SolicitudEmisionManual getSolicitudEmisionManual() {
		return solicitudEmisionManual;
	}

	public void setSolicitudEmisionManual(SolicitudEmisionManual solicitudEmisionManual) {
		this.solicitudEmisionManual = solicitudEmisionManual;
	}

	public EmisionManual getEmisionManual() {
		return emisionManual;
	}

	public void setEmisionManual(EmisionManual emisionManual) {
		this.emisionManual = emisionManual;
	}

	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

}
