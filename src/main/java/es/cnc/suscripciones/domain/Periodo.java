package es.cnc.suscripciones.domain;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "periodo")
public class Periodo extends AbstractEntity<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Codigo", length = 1)
    private String codigo;
    
	@Override
	public String getId() {
		return getCodigo();
	}

	@Override
	public void setId(String id) {
		setCodigo(id);
	}
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(String id) {
        this.codigo = id;
    }
    
    @JsonIgnore
    @ManyToMany(mappedBy = "periodoes", fetch = FetchType.LAZY)
    private Set<Meses> meseses;
    
    @Column(name = "Nombre", length = 10)
    private String nombre;
    
    @Column(name = "Frecuencia")
    private Short frecuencia;
    
    @Column(name = "Anticipacion")
    private Short anticipacion;
    
    public Set<Meses> getMeseses() {
        return meseses;
    }
    
    public void setMeseses(Set<Meses> meseses) {
        this.meseses = meseses;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Short getFrecuencia() {
        return frecuencia;
    }
    
    public void setFrecuencia(Short frecuencia) {
        this.frecuencia = frecuencia;
    }
    
    public Short getAnticipacion() {
        return anticipacion;
    }
    
    public void setAnticipacion(Short anticipacion) {
        this.anticipacion = anticipacion;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("meseses").toString();
    }

}
