package es.cnc.suscripciones.domain;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name="bancos")
public class Bancos extends AbstractEntity<Integer> {
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
    public Bancos() {
    	super(Bancos.class);
	}
    @OneToMany(mappedBy = "idBanco", fetch = FetchType.LAZY)
    private Set<Sucursal> sucursals;
    
    @Column(name = "COD_BCO", length = 4, nullable=false)
    private String codBco;
    
    @Column(name = "DEN_BCO", length = 40)
    private String denBco;
    
    @Column
    private boolean activo;
    
    public Set<Sucursal> getSucursals() {
        return sucursals;
    }
    
    public void setSucursals(Set<Sucursal> sucursals) {
        this.sucursals = sucursals;
    }
    
    public String getCodBco() {
        return codBco;
    }
    
    public void setCodBco(String codBco) {
        this.codBco = codBco;
    }
    
    public String getDenBco() {
        return denBco;
    }
    
    public void setDenBco(String denBco) {
        this.denBco = denBco;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("sucursals").toString();
    }

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
    
    
}
