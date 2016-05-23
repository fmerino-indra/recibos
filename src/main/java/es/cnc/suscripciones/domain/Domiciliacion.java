package es.cnc.suscripciones.domain;
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

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.stasiena.sepa.util.IBANUtil;

@Entity
@Table(name = "domiciliacion")
public class Domiciliacion extends AbstractEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdDomiciliacion")
    private Integer idDomiciliacion;
    

	@Override
	public Integer getId() {
		return getIdDomiciliacion();
	}

	@Override
	public void setId(Integer id) {
		setIdDomiciliacion(id);
	}
    public Integer getIdDomiciliacion() {
        return this.idDomiciliacion;
    }
    
    public void setIdDomiciliacion(Integer id) {
        this.idDomiciliacion = id;
    }
    
    @OneToMany(mappedBy = "idDomiciliacion", fetch = FetchType.LAZY)
    private Set<PSD> pSDs;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdPersona", referencedColumnName = "id")
    private Persona idPersona;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id", referencedColumnName = "id", nullable = false)
    private Sucursal sucursalId;
    
    @Column(name = "Cuenta", length = 10)
    private String cuenta;
    
    @Column(name = "DC_OLD", length = 2)
    private String dcOld;
    
    @Column(name = "DC", length = 2)
    private String dc;
    
    @Column(name = "idAntiguo")
    private Integer idAntiguo;
    
    @Column(name = "IBAN", length = 24)
    private String iban;
    
    public Set<PSD> getPSDs() {
        return pSDs;
    }
    
    public void setPSDs(Set<PSD> pSDs) {
        this.pSDs = pSDs;
    }
    
    public Persona getIdPersona() {
        return idPersona;
    }
    
    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }
    
    public Sucursal getSucursalId() {
        return sucursalId;
    }
    
    public void setSucursalId(Sucursal sucursalId) {
        this.sucursalId = sucursalId;
    }
    
    public String getCuenta() {
        return cuenta;
    }
    
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    
    public String getDcOld() {
        return dcOld;
    }
    
    public void setDcOld(String dcOld) {
        this.dcOld = dcOld;
    }
    
    public String getDc() {
        return dc;
    }
    
    public void setDc(String dc) {
        this.dc = dc;
    }
    
    public Integer getIdAntiguo() {
        return idAntiguo;
    }
    
    public void setIdAntiguo(Integer idAntiguo) {
        this.idAntiguo = idAntiguo;
    }
    
    public String getIban() {
        return iban;
    }
    
    /**
     * 
     * @param iban Must be a valid IBAN
     */
    public void setIban(String iban) {
    	if (!IBANUtil.validarIBAN(iban)) {
    		throw new RuntimeException("Not valid IBAN: " + iban);
    	} else {
    		this.iban = iban;
    	}
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("pSDs", "idPersona", "sucursalId").toString();
    }
}
