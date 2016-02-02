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
@Table(name = "parroco")
public class Parroco extends AbstractEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @OneToMany(mappedBy = "parrocoId", fetch = FetchType.LAZY)
    private Set<ParroquiaHasParroco> parroquiaHasParroco;
    
    @Column(name = "Nombre", length = 40)
    private String nombre;
    
    @Column(name = "NIF", length = 9)
    private String nif;
    
    public Set<ParroquiaHasParroco> getParroquias() {
        return parroquiaHasParroco;
    }
    
    public void setParroquias(Set<ParroquiaHasParroco> parroquiaHasParroco) {
        this.parroquiaHasParroco = parroquiaHasParroco;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNif() {
        return nif;
    }
    
    public void setNif(String nif) {
        this.nif = nif;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("parroquiaHasParrocoes").toString();
    }
    
}
