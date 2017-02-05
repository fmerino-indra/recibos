package es.cnc.suscripciones.domain;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "meses")
public class Meses extends AbstractEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdMes")
    private Integer idMes;
    
	@Override
	public Integer getId() {
		return getIdMes();
	}

	@Override
	public void setId(Integer id) {
		setIdMes(id);
	}
	
	public Meses() {
    	super(Meses.class);
	}
    public Integer getIdMes() {
        return this.idMes;
    }
    
    public void setIdMes(Integer id) {
        this.idMes = id;
    }
    
    
    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "mascara_emisiones", schema = "suscrDb", joinColumns = { @JoinColumn(name = "meses_IdMes", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "periodo_Codigo", nullable = false) })
    @JoinTable(name = "mascara_emisiones", joinColumns = { @JoinColumn(name = "meses_IdMes", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "periodo_Codigo", nullable = false) })
    @OrderColumn(name = "`order`")
    private List<Periodo> periodoes;
    
    @Column(name = "Mes", length = 15)
    private String mes;
    
    public List<Periodo> getPeriodoes() {
        return periodoes;
    }
    
    public void setPeriodoes(List<Periodo> periodoes) {
        this.periodoes = periodoes;
    }
    
    public String getMes() {
        return mes;
    }
    
    public void setMes(String mes) {
        this.mes = mes;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("periodoes").toString();
    }

}
