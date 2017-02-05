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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "parroquia_aux")
public class ParroquiaAux extends AbstractEntity<Integer> {
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
    
    public ParroquiaAux() {
    	super(Parroquia.class);
	}
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "pais", referencedColumnName = "idPais")
    private Pais pais;
    
    @OneToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "idParroquia", referencedColumnName = "Id")
    @JsonIgnore
    private Parroquia idParroquia;
    
    @Column(name = "direccion", length = 50)
    private String direccion;
    
    @Column(name = "cp", length = 5)
    private String cp;
    
    @Column(name = "poblacion", length = 44)
    private String poblacion;
    
    @Column(name = "provincia", length = 40)
    private String provincia;
    
    public Pais getPais() {
        return pais;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    public Parroquia getIdParroquia() {
        return idParroquia;
    }
    
    public void setIdParroquia(Parroquia idParroquia) {
        this.idParroquia = idParroquia;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getCp() {
        return cp;
    }
    
    public void setCp(String cp) {
        this.cp = cp;
    }
    
    public String getPoblacion() {
        return poblacion;
    }
    
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }
    
    public String getProvincia() {
        return provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("pais", "idParroquia").toString();
    }
    
}
