package es.cnc.suscripciones.front.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="codigoMes")
public class ResumenDTO extends AbstractDTO {

	public ResumenDTO() {
		// TODO Auto-generated constructor stub
	}

    private Integer codigoMes;
    private String mes;
    private Date fechaMes;
    private Double importeMes;
    private Double devueltoMes;
    private Date fechaTrimestre;
    private Double importeTrimestre;
    private Double devueltoTrimestre;
    private Date fechaSemestre;
    private Double importeSemestre;
    private Double devueltoSemestre;
    private Date fechaAnyo;
    private Double importeAnyo;
    private Double devueltoAnyo;
    private Date fechaOtros;
    private Double importeOtros;
    private Double devueltoOtros;
   
	public Integer getCodigoMes() {
		return codigoMes;
	}

	public void setCodigoMes(Integer codigoMes) {
		this.codigoMes = codigoMes;
	}

	/**
	 * @return the mes
	 */
	public String getMes() {
		return mes;
	}

	/**
	 * @param mes the mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}

	/**
	 * @return the fechaMes
	 */
	public Date getFechaMes() {
		return fechaMes;
	}

	/**
	 * @param fechaMes the fechaMes to set
	 */
	public void setFechaMes(Date fechaMes) {
		this.fechaMes = fechaMes;
	}

	/**
	 * @return the importeMes
	 */
	public Double getImporteMes() {
		return importeMes;
	}

	/**
	 * @param importeMes the importeMes to set
	 */
	public void setImporteMes(Double importeMes) {
		this.importeMes = importeMes;
	}

	/**
	 * @return the fechaTrimestre
	 */
	public Date getFechaTrimestre() {
		return fechaTrimestre;
	}

	/**
	 * @param fechaTrimestre the fechaTrimestre to set
	 */
	public void setFechaTrimestre(Date fechaTrimestre) {
		this.fechaTrimestre = fechaTrimestre;
	}

	/**
	 * @return the importeTrimestre
	 */
	public Double getImporteTrimestre() {
		return importeTrimestre;
	}

	/**
	 * @param importeTrimestre the importeTrimestre to set
	 */
	public void setImporteTrimestre(Double importeTrimestre) {
		this.importeTrimestre = importeTrimestre;
	}

	/**
	 * @return the fechaSemestre
	 */
	public Date getFechaSemestre() {
		return fechaSemestre;
	}

	/**
	 * @param fechaSemestre the fechaSemestre to set
	 */
	public void setFechaSemestre(Date fechaSemestre) {
		this.fechaSemestre = fechaSemestre;
	}

	/**
	 * @return the importeSemestre
	 */
	public Double getImporteSemestre() {
		return importeSemestre;
	}

	/**
	 * @param importeSemestre the importeSemestre to set
	 */
	public void setImporteSemestre(Double importeSemestre) {
		this.importeSemestre = importeSemestre;
	}

	/**
	 * @return the fechaAnyo
	 */
	public Date getFechaAnyo() {
		return fechaAnyo;
	}

	/**
	 * @param fechaAnyo the fechaAnyo to set
	 */
	public void setFechaAnyo(Date fechaAnyo) {
		this.fechaAnyo = fechaAnyo;
	}

	/**
	 * @return the importeAnyo
	 */
	public Double getImporteAnyo() {
		return importeAnyo;
	}

	/**
	 * @param importeAnyo the importeAnyo to set
	 */
	public void setImporteAnyo(Double importeAnyo) {
		this.importeAnyo = importeAnyo;
	}

	/**
	 * @return the fechaOtros
	 */
	public Date getFechaOtros() {
		return fechaOtros;
	}

	/**
	 * @param fechaOtros the fechaOtros to set
	 */
	public void setFechaOtros(Date fechaOtros) {
		this.fechaOtros = fechaOtros;
	}

	/**
	 * @return the importeOtros
	 */
	public Double getImporteOtros() {
		return importeOtros;
	}

	/**
	 * @param importeOtros the importeOtros to set
	 */
	public void setImporteOtros(Double importeOtros) {
		this.importeOtros = importeOtros;
	}

	/**
	 * @return the devueltoTrimestre
	 */
	public Double getDevueltoTrimestre() {
		return devueltoTrimestre;
	}

	/**
	 * @param devueltoTrimestre the devueltoTrimestre to set
	 */
	public void setDevueltoTrimestre(Double devueltoTrimestre) {
		this.devueltoTrimestre = devueltoTrimestre;
	}

	/**
	 * @return the devueltoMes
	 */
	public Double getDevueltoMes() {
		return devueltoMes;
	}

	/**
	 * @param devueltoMes the devueltoMes to set
	 */
	public void setDevueltoMes(Double devueltoMes) {
		this.devueltoMes = devueltoMes;
	}

	/**
	 * @return the devueltoSemestre
	 */
	public Double getDevueltoSemestre() {
		return devueltoSemestre;
	}

	/**
	 * @param devueltoSemestre the devueltoSemestre to set
	 */
	public void setDevueltoSemestre(Double devueltoSemestre) {
		this.devueltoSemestre = devueltoSemestre;
	}

	/**
	 * @return the devueltoAnyo
	 */
	public Double getDevueltoAnyo() {
		return devueltoAnyo;
	}

	/**
	 * @param devueltoAnyo the devueltoAnyo to set
	 */
	public void setDevueltoAnyo(Double devueltoAnyo) {
		this.devueltoAnyo = devueltoAnyo;
	}

	/**
	 * @return the devueltoOtros
	 */
	public Double getDevueltoOtros() {
		return devueltoOtros;
	}

	/**
	 * @param devueltoOtros the devueltoOtros to set
	 */
	public void setDevueltoOtros(Double devueltoOtros) {
		this.devueltoOtros = devueltoOtros;
	}

}
