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
@Table(name = "sucursal")
public class Sucursal extends AbstractEntity<Integer> {

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

    
    @OneToMany(mappedBy = "sucursalId", fetch = FetchType.LAZY)
    private Set<Domiciliacion> domiciliacions;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBanco", referencedColumnName = "id", nullable = false)
    private Bancos idBanco;
    
    @Column(name = "COD_SUC", length = 4, nullable=false)
    private String codSuc;
    
    @Column(name = "DEN_SUC", length = 40)
    private String denSuc;
    
    public Set<Domiciliacion> getDomiciliacions() {
        return domiciliacions;
    }
    
    public void setDomiciliacions(Set<Domiciliacion> domiciliacions) {
        this.domiciliacions = domiciliacions;
    }
    
    public Bancos getIdBanco() {
        return idBanco;
    }
    
    public void setIdBanco(Bancos idBanco) {
        this.idBanco = idBanco;
    }
    
    public String getCodSuc() {
        return codSuc;
    }
    
    public void setCodSuc(String codSuc) {
        this.codSuc = codSuc;
    }
    
    public String getDenSuc() {
        return denSuc;
    }
    
    public void setDenSuc(String denSuc) {
        this.denSuc = denSuc;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("domiciliacions", "idBanco").toString();
    }
}
