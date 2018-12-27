package es.cnc.suscripciones.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "emision_manual")
public class EmisionManual extends AbstractEntity<Integer> {
    @Id
    @Column(name = "idDetalleSolicitud")
    private Integer id;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public EmisionManual() {
    	super(EmisionManual.class);
	}
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCabecera", referencedColumnName = "id")
    private CabeceraEmisionManual idCabecera;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDetalleSolicitud", referencedColumnName = "id")
    private DetalleSolicitudEmision detalleSolicitudEmision;
    
	//bi-directional many-to-one association to Domiciliacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDomiciliacion", referencedColumnName = "idDomiciliacion")
	private Domiciliacion domiciliacion;

    @Column(name = "Importe", precision = 22)
	private Double importe;
    
    @Column(name = "Devuelto")
    private Boolean devuelto;
    
    @Column(name = "divisa", length=3)
	private String divisa;
    
    public String toString() {
//        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("idCabecera", "idSuscripcion").toString();
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

	public CabeceraEmisionManual getIdCabecera() {
		return idCabecera;
	}

	public void setIdCabecera(CabeceraEmisionManual idCabecera) {
		this.idCabecera = idCabecera;
	}

	public DetalleSolicitudEmision getDetalleSolicitudEmision() {
		return detalleSolicitudEmision;
	}

	public void setDetalleSolicitudEmision(DetalleSolicitudEmision detalleSolicitudEmision) {
		this.detalleSolicitudEmision = detalleSolicitudEmision;
	}

	public Boolean getDevuelto() {
		return devuelto;
	}

	public void setDevuelto(Boolean devuelto) {
		this.devuelto = devuelto;
	}

	public Domiciliacion getDomiciliacion() {
		return domiciliacion;
	}

	public void setDomiciliacion(Domiciliacion domiciliacion) {
		this.domiciliacion = domiciliacion;
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
}
