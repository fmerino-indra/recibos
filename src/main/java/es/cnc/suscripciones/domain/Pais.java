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
@Table(name="pais")
public class Pais extends AbstractEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idPais")
    private Integer idPais;
    
    public Integer getIdPais() {
        return this.idPais;
    }
    
    public void setIdPais(Integer id) {
        this.idPais = id;
    }
    public Pais() {
    	super(Pais.class);
	}
	@Override
	public Integer getId() {
		return getIdPais();
	}

	@Override
	public void setId(Integer id) {
		setIdPais(id);
	}
    
    @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)
    private Set<ParroquiaAux> parroquiaAuxes;
    
    @Column(name = "codigoISO3", length = 3)
    private String codigoIso3;
    
    @Column(name = "codigoISO2", length = 2)
    private String codigoIso2;
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @Column(name = "nacionalidad", length = 50)
    private String nacionalidad;
    
    @Column(name = "gentilicio", length = 50)
    private String gentilicio;
    
    public Set<ParroquiaAux> getParroquiaAuxes() {
        return parroquiaAuxes;
    }
    
    public void setParroquiaAuxes(Set<ParroquiaAux> parroquiaAuxes) {
        this.parroquiaAuxes = parroquiaAuxes;
    }
    
    public String getCodigoIso3() {
        return codigoIso3;
    }
    
    public void setCodigoIso3(String codigoIso3) {
        this.codigoIso3 = codigoIso3;
    }
    
    public String getCodigoIso2() {
        return codigoIso2;
    }
    
    public void setCodigoIso2(String codigoIso2) {
        this.codigoIso2 = codigoIso2;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getNacionalidad() {
        return nacionalidad;
    }
    
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    
    public String getGentilicio() {
        return gentilicio;
    }
    
    public void setGentilicio(String gentilicio) {
        this.gentilicio = gentilicio;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("parroquiaAuxes").toString();
    }

}
