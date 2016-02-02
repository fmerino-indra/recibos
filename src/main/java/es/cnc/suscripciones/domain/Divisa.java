package es.cnc.suscripciones.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "divisa")
public class Divisa extends AbstractEntity<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdDivisa", length = 3)
    private String idDivisa;
    

	@Override
	public String getId() {
		return getIdDivisa();
	}

	@Override
	public void setId(String id) {
		setIdDivisa(id);
	}
	
    public String getIdDivisa() {
        return this.idDivisa;
    }
    
    public void setIdDivisa(String id) {
        this.idDivisa = id;
    }
    
    @Column(name = "NombreDivisa", length = 50)
    private String nombreDivisa;
    
    @Column(name = "UnidadesEuro", precision = 22)
    private Double unidadesEuro;
    
    public String getNombreDivisa() {
        return nombreDivisa;
    }
    
    public void setNombreDivisa(String nombreDivisa) {
        this.nombreDivisa = nombreDivisa;
    }
    
    public Double getUnidadesEuro() {
        return unidadesEuro;
    }
    
    public void setUnidadesEuro(Double unidadesEuro) {
        this.unidadesEuro = unidadesEuro;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
