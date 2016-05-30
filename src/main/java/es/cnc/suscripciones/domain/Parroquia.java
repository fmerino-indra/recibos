package es.cnc.suscripciones.domain;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "parroquia")
public class Parroquia extends AbstractEntity<Integer> {
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
    
    public Parroquia() {
    	super(Parroquia.class);
	}
    @OneToOne(mappedBy = "idParroquia", fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private ParroquiaAux parroquiaAux;
    
    @OneToMany(mappedBy = "parroquiaId", fetch = FetchType.LAZY)
    private Set<ParroquiaHasParroco> parroquiaHasParroco;
    
    @Column(name = "Nombre", length = 50)
    private String nombre;
    
    @Column(name = "NIF", length = 15)
    private String nif;
    
    @Column(name = "Banco", length = 4)
    private String banco;
    
    @Column(name = "Sucursal", length = 4)
    private String sucursal;
    
    @Column(name = "DC", length = 2)
    private String dc;
    
    @Column(name = "Cuenta", length = 10)
    private String cuenta;
    
    public ParroquiaAux getParroquiaAux() {
        return parroquiaAux;
    }
    
    public void setParroquiaAuxes(ParroquiaAux parroquiaAux) {
        this.parroquiaAux = parroquiaAux;
    }
    
    public Set<ParroquiaHasParroco> getParroquiaHasParrocos() {
        return parroquiaHasParroco;
    }
    
    public void setParroquiaHasParrocos(Set<ParroquiaHasParroco> parroquiaHasParrocos) {
        this.parroquiaHasParroco = parroquiaHasParrocos;
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
    
    public String getBanco() {
        return banco;
    }
    
    public void setBanco(String banco) {
        this.banco = banco;
    }
    
    public String getSucursal() {
        return sucursal;
    }
    
    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    
    public String getDc() {
        return dc;
    }
    
    public void setDc(String dc) {
        this.dc = dc;
    }
    
    public String getCuenta() {
        return cuenta;
    }
    
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("parroquiaAuxes", "parroquiaHasParrocoes").toString();
    }
}
