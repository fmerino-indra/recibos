package es.cnc.suscripciones.services.mod182;

import es.cnc.util.StringUtil;

public class Mod182DeclaradosDTO {

	private static String tipoRegistro = "2";
	private static String modelo = "182";
	private Integer year;

	private Integer id;
	private String nombre;
	private Double sumImporte;
	
	private String nif;
	private Integer codProv;
	private String clave;
	private Float deduccion;
	private String especie;
	private Integer CCAA;
	private Float deduccionCCAA;
	private String naturaleza;
	private String revocacion;
	private Integer anyoRevocacion;
	private String tipoBien;
	private String identificacionBien;
	private String recurrencia;
	
	private static String f1 = "%1$-1S"; //1
	private static String f2 = "%2$-3S"; //2-4
	private static String f3 = "%3$-4S"; //5-8
	private static String f4 = "%4$-9S"; //9-17
	private static String f5 = "%5$-9S"; //18-26
	private static String f6 = "%6$-9S"; //27-35
	private static String f7 = "%7$-40S"; //36-75
	private static String f8 = "%8$2d"; //76-77
	private static String f9 = "%9$-1S"; //78
	private static String f10 = "%10$05d";//79-83
	private static String f11 = "%11$013d";//84-96
	private static String f12 = "%12$-1S";//97
	private static String f13 = "%13$02d";//98-99
	private static String f14 = "%14$05d";//100-104
	private static String f15 = "%15$-1S";//105
	private static String f16 = "%16$-1S";//106
	private static String f17 = "%17$04d";//107-110
	private static String f18 = "%18$-1S";//111
	private static String f19 = "%19$-20S";//112-131
	private static String f20 = "%20$1S";//132
	private static String f21 = "%21$-118S";//133-250

	private static String DECLARANTE_FORMAT = f1 + f2 + f3 + f4 + f5 + f6 + f7 + f8 + f9 + f10 + f11 + f12 + f13 + f14 + f15 + f16 + f17 + f18 + f19 + f20 + f21;
	public Mod182DeclaradosDTO() {
		super();
	}
	public Mod182DeclaradosDTO(Integer id, Integer year, String nombre, Double sumImporte, String nif) {
		super();
		this.id = id;
		this.year = year;
		this.nombre = nombre;
		this.sumImporte = sumImporte;
		this.nif = nif;
	}
	public String formatDeclarante(String cif) {
		String aux = null;

		int deduccion = (int)(this.deduccion.floatValue()*100);
		int importe = (int)(this.sumImporte.doubleValue()*100);
		int deduccionCCAA = (int)(this.deduccionCCAA.floatValue()*100);
		
		aux = String.format(DECLARANTE_FORMAT, tipoRegistro, modelo, year, cif, nif, "", StringUtil.quitarAcentos(nombre), codProv, clave, deduccion, importe, especie, CCAA, deduccionCCAA, naturaleza, revocacion, anyoRevocacion, tipoBien, StringUtil.quitarAcentos(identificacionBien), getRecurrencia(), "");
		return aux;
	}
	public static void main(String[] args) {

		Mod182DeclaradosDTO dto = new Mod182DeclaradosDTO();
		dto.year = 2016;
		dto.nif = "02423257T";
		dto.nombre = "Merino Martínez de Pinillos, Félix";
		dto.codProv = 28;
		dto.clave = "A";
		dto.deduccion = 50.99f;
		dto.sumImporte = 55.33d;
		dto.especie = "";
		dto.CCAA = 12;
		dto.deduccionCCAA = 0.0f;
		dto.naturaleza = "F";
		dto.revocacion = "";
		dto.anyoRevocacion = 0;
		dto.tipoBien = "";
		dto.identificacionBien = "";
		dto.setRecurrencia("");

		String aux = dto.formatDeclarante("R7800543F");
		System.out.println(aux);
	}
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the sumImporte
	 */
	public Double getSumImporte() {
		return sumImporte;
	}
	/**
	 * @param sumImporte the sumImporte to set
	 */
	public void setSumImporte(Double sumImporte) {
		this.sumImporte = sumImporte;
	}
	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	/**
	 * @return the codProv
	 */
	public Integer getCodProv() {
		return codProv;
	}
	/**
	 * @param codProv the codProv to set
	 */
	public void setCodProv(Integer codProv) {
		this.codProv = codProv;
	}
	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}
	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	/**
	 * @return the deduccion
	 */
	public Float getDeduccion() {
		return deduccion;
	}
	/**
	 * @param deduccion the deduccion to set
	 */
	public void setDeduccion(Float deduccion) {
		this.deduccion = deduccion;
	}
	/**
	 * @return the especie
	 */
	public String getEspecie() {
		return especie;
	}
	/**
	 * @param especie the especie to set
	 */
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	/**
	 * @return the cCAA
	 */
	public Integer getCCAA() {
		return CCAA;
	}
	/**
	 * @param cCAA the cCAA to set
	 */
	public void setCCAA(Integer cCAA) {
		CCAA = cCAA;
	}
	/**
	 * @return the deduccionCCAA
	 */
	public Float getDeduccionCCAA() {
		return deduccionCCAA;
	}
	/**
	 * @param deduccionCCAA the deduccionCCAA to set
	 */
	public void setDeduccionCCAA(Float deduccionCCAA) {
		this.deduccionCCAA = deduccionCCAA;
	}
	/**
	 * @return the naturaleza
	 */
	public String getNaturaleza() {
		return naturaleza;
	}
	/**
	 * @param naturaleza the naturaleza to set
	 */
	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}
	/**
	 * @return the revocacion
	 */
	public String getRevocacion() {
		return revocacion;
	}
	/**
	 * @param revocacion the revocacion to set
	 */
	public void setRevocacion(String revocacion) {
		this.revocacion = revocacion;
	}
	/**
	 * @return the anyoRevocacion
	 */
	public Integer getAnyoRevocacion() {
		return anyoRevocacion;
	}
	/**
	 * @param anyoRevocacion the anyoRevocacion to set
	 */
	public void setAnyoRevocacion(Integer anyoRevocacion) {
		this.anyoRevocacion = anyoRevocacion;
	}
	/**
	 * @return the tipoBien
	 */
	public String getTipoBien() {
		return tipoBien;
	}
	/**
	 * @param tipoBien the tipoBien to set
	 */
	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}
	/**
	 * @return the identificacionBien
	 */
	public String getIdentificacionBien() {
		return identificacionBien;
	}
	/**
	 * @param identificacionBien the identificacionBien to set
	 */
	public void setIdentificacionBien(String identificacionBien) {
		this.identificacionBien = identificacionBien;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(nif!=null?nif:"");
		sb.append("-");
		sb.append(nombre!=null?nombre:"");
		sb.append("-");
		sb.append(sumImporte!=null?sumImporte:"");
		sb.append("-");
		sb.append(year!=null?year:"");
				
		return sb.toString(); 
	}
	/**
	 * @return the recurrencia
	 */
	public String getRecurrencia() {
		return recurrencia;
	}
	/**
	 * @param recurrencia the recurrencia to set
	 */
	public void setRecurrencia(String recurrencia) {
		this.recurrencia = recurrencia;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
}
