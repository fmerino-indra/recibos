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

@Entity
@Table(name = "persona")
public class Persona extends AbstractEntity<Integer> {
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
    
    public Persona() {
    	super(Persona.class);
	}
    
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY)
    private Set<Domiciliacion> domiciliacions;
    
    @OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
    private Set<Suscripcion> suscripcions;
    
    @Column(name = "NOMBRE", length = 40)
    private String nombre;
    
    @Column(name = "NIF", length = 15)
    private String nif;
    
    @Column(name = "SUFIJO", length = 3)
    private String sufijo;
    
    @Column(name = "DOMICILIO", length = 35)
    private String domicilio;
    
    @Column(name = "CP", length = 5)
    private String cp;
    
    @Column(name = "POBLACION", length = 35)
    private String poblacion;
    
    @Column(name = "TFNO", length = 10)
    private String tfno;
    
    @Column(name = "movil", length = 15)
    private String movil;
    
    @Column(name = "correo", length = 45)
    private String correo;
    
    @Column(name = "idAntigua")
    private Integer idAntigua;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "antecesor", referencedColumnName = "id", nullable = true)
    private Persona antecesor;
    
    public Set<Domiciliacion> getDomiciliacions() {
        return domiciliacions;
    }
    
    public void setDomiciliacions(Set<Domiciliacion> domiciliacions) {
        this.domiciliacions = domiciliacions;
    }
    
    public Set<Suscripcion> getSuscripcions() {
        return suscripcions;
    }
    
    public void setSuscripcions(Set<Suscripcion> suscripcions) {
        this.suscripcions = suscripcions;
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
    
    public String getSufijo() {
        return sufijo;
    }
    
    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }
    
    public String getDomicilio() {
        return domicilio;
    }
    
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
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
    
    public String getTfno() {
        return tfno;
    }
    
    public void setTfno(String tfno) {
        this.tfno = tfno;
    }
    
    public Integer getIdAntigua() {
        return idAntigua;
    }
    
    public void setIdAntigua(Integer idAntigua) {
        this.idAntigua = idAntigua;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("domiciliacions", "suscripcions").toString();
    }
    
    public static Persona fromJSON(String json) {
    	return (Persona)AbstractEntity.fromJSONToEntity(json, Persona.class);
    }

	public Persona getAntecesor() {
		return antecesor;
	}

	public void setAntecesor(Persona antecesor) {
		this.antecesor = antecesor;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
}
