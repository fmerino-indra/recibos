package es.cnc.suscripciones.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "parroquia_has_parroco")
public class ParroquiaHasParroco extends AbstractEntity<Integer> {

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

    
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "parroco_Id", referencedColumnName = "Id", nullable = false)
    private Parroco parrocoId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "parroquia_Id", referencedColumnName = "Id", nullable = false)
    private Parroquia parroquiaId;
    
    @Column(name = "Activo")
    private Boolean activo;
    
    @Column(name = "Inicio")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date inicio;
    
    @Column(name = "Fin")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date fin;
    
    public Parroco getParrocoId() {
        return parrocoId;
    }
    
    public void setParrocoId(Parroco parrocoId) {
        this.parrocoId = parrocoId;
    }
    
    public Parroquia getParroquiaId() {
        return parroquiaId;
    }
    
    public void setParroquiaId(Parroquia parroquiaId) {
        this.parroquiaId = parroquiaId;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public Date getInicio() {
        return inicio;
    }
    
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }
    
    public Date getFin() {
        return fin;
    }
    
    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("parrocoId", "parroquiaId").toString();
    }
}
