package es.cnc.suscripciones.domain;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "suscripcion")
public class Suscripcion extends AbstractEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdSuscripcion")
    private Integer idSuscripcion;
    
	@Override
	public Integer getId() {
		return getIdSuscripcion();
	}

	@Override
	public void setId(Integer id) {
		setIdSuscripcion(id);
	}
	
    public Integer getIdSuscripcion() {
        return this.idSuscripcion;
    }
    
    public void setIdSuscripcion(Integer id) {
        this.idSuscripcion = id;
    }
    
    @OneToMany(mappedBy = "idSuscripcion", fetch = FetchType.LAZY)
    private Set<PSD> pSDs;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secuenciaAdeudo", referencedColumnName = "id")
    private Secuenciaadeudo secuenciaAdeudo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona", referencedColumnName = "id", nullable = false)
    private Persona persona;
    
    @Column(name = "FechaInicio")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date fechaInicio;
    
    @Column(name = "FechaBaja")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date fechaBaja;
    
    @Column(name = "PAGO", length = 1)
    private String pago;
    
    @Column(name = "PESETAS", precision = 22)
    private Double pesetas;
    
    @Column(name = "Divisa", length = 3)
    private String divisa;
    
    @Column(name = "PERIODO", length = 1)
    private String periodo;
    
    @Column(name = "DEVOLUCION", length = 10)
    private String devolucion;
    
    @Column(name = "Activo")
    private Boolean activo;
    
    @Column(name = "UltimaEmision")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date ultimaEmision;
    
    @Column(name = "IdTitular")
    private Integer idTitular;
    
    @Column(name = "Euros", precision = 22)
    private Double euros;
    
    @Column(name = "idAntigua")
    private Integer idAntigua;
    
    @Column(name = "concepto", length = 140)
    private String concepto;
    
    public Set<PSD> getPSDs() {
        return pSDs;
    }
    
    public void setPSDs(Set<PSD> pSDs) {
        this.pSDs = pSDs;
    }
    
    public Secuenciaadeudo getSecuenciaAdeudo() {
        return secuenciaAdeudo;
    }
    
    public void setSecuenciaAdeudo(Secuenciaadeudo secuenciaAdeudo) {
        this.secuenciaAdeudo = secuenciaAdeudo;
    }
    
    public Persona getPersona() {
        return persona;
    }
    
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public Date getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public Date getFechaBaja() {
        return fechaBaja;
    }
    
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    public String getPago() {
        return pago;
    }
    
    public void setPago(String pago) {
        this.pago = pago;
    }
    
    public Double getPesetas() {
        return pesetas;
    }
    
    public void setPesetas(Double pesetas) {
        this.pesetas = pesetas;
    }
    
    public String getDivisa() {
        return divisa;
    }
    
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }
    
    public String getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    public String getDevolucion() {
        return devolucion;
    }
    
    public void setDevolucion(String devolucion) {
        this.devolucion = devolucion;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public Date getUltimaEmision() {
        return ultimaEmision;
    }
    
    public void setUltimaEmision(Date ultimaEmision) {
        this.ultimaEmision = ultimaEmision;
    }
    
    public Integer getIdTitular() {
        return idTitular;
    }
    
    public void setIdTitular(Integer idTitular) {
        this.idTitular = idTitular;
    }
    
    public Double getEuros() {
        return euros;
    }
    
    public void setEuros(Double euros) {
        this.euros = euros;
    }
    
    public Integer getIdAntigua() {
        return idAntigua;
    }
    
    public void setIdAntigua(Integer idAntigua) {
        this.idAntigua = idAntigua;
    }
    
    public String getConcepto() {
        return concepto;
    }
    
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("pSDs", "secuenciaAdeudo", "persona").toString();
    }

}
