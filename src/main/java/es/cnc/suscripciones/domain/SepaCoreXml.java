package es.cnc.suscripciones.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "sepa_core_xml")
public class SepaCoreXml extends AbstractEntity<Integer> {
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
    public SepaCoreXml() {
    	super(SepaCoreXml.class);
	}
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cabeceraemisiones_id", referencedColumnName = "id")
    private Cabeceraemisiones idCabecera;
    
    @Column(name = "sepa_core_xml")
    private String xml;
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("idCabecera").toString();
    }

	/**
	 * @return the idCabecera
	 */
	public Cabeceraemisiones getIdCabecera() {
		return idCabecera;
	}

	/**
	 * @param idCabecera the idCabecera to set
	 */
	public void setIdCabecera(Cabeceraemisiones idCabecera) {
		this.idCabecera = idCabecera;
	}

	/**
	 * @return the xml
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * @param xml the xml to set
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}
}
