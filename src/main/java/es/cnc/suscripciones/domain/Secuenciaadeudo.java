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
@Table(name = "secuenciaadeudo")
public class Secuenciaadeudo extends AbstractEntity<Integer> {
	
	public Secuenciaadeudo() {
    	this(null, null);
	}
    public Secuenciaadeudo(Integer id, String descripcion) {
    	super(Secuenciaadeudo.class);
		this.id = id;
		this.descripcion = descripcion;
	}

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
    
    @OneToMany(mappedBy = "secuenciaAdeudo", fetch = FetchType.LAZY)
    private Set<Suscripcion> suscripcions;
    
    @Column(name = "descripcion", length = 45)
    private String descripcion;
    
    public Set<Suscripcion> getSuscripcions() {
        return suscripcions;
    }
    
    public void setSuscripcions(Set<Suscripcion> suscripcions) {
        this.suscripcions = suscripcions;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("suscripcions").toString();
    }
}
