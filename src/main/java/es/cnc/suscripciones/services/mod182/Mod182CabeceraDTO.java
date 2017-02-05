package es.cnc.suscripciones.services.mod182;

import es.cnc.util.StringUtil;

public class Mod182CabeceraDTO {

	private static String tipoRegistro = "1";
	private static String modelo = "182";
	private static String tipoSoporte = "T";
	private static Integer tipoDeclarante = 1;
	
	private Integer year;

	private String cif;
	
	private String nombre;
	private String tfno;
	private String contacto;
	
	private Integer numRegistros = 0;
	private Double sumImporte = 0.0d;
	
	
	private static String f1 = "%1$-1S"; //1
	private static String f2 = "%2$-3S"; //2-4
	private static String f3 = "%3$-4S"; //5-8
	private static String f4 = "%4$-9S"; //9-17
	private static String f5 = "%5$-40S"; //18-57
	private static String f6 = "%6$-1S"; //58
	private static String f7 = "%7$-9S"; //59-67
	private static String f8 = "%8$-40S"; //68-107
	private static String f9 = "%9$013d"; //108-120
	private static String f10 = "%10$-2S";//121-122
	private static String f11 = "%11$013d";//123-135
	private static String f12 = "%12$09d";//136-144
	private static String f13 = "%13$015d";//145-159
	private static String f14 = "%14$01d";//160
	private static String f15 = "%15$-9S";//161-169
	private static String f16 = "%16$-40S";//170-209
	private static String f17 = "%17$-28S";//210-237
	private static String f18 = "%18$-13S";//238-250

	private static String DECLARANTE_FORMAT = f1 + f2 + f3 + f4 + f5 + f6 + f7 + f8 + f9 + f10 + f11 + f12 + f13 + f14 + f15 + f16 + f17 + f18;

	public String formatCabecera() {
		String aux = null;

		Integer sumImporte = (int)(this.sumImporte.doubleValue()*100);
		aux = String.format(DECLARANTE_FORMAT, tipoRegistro, modelo, year, cif, 
				StringUtil.quitarAcentos(nombre), tipoSoporte, StringUtil.quitarAcentos(tfno), 
				StringUtil.quitarAcentos(contacto), 0, "", 0,
				numRegistros, sumImporte, tipoDeclarante, "", "", "", "");
		return aux;
	}
	public static void main(String[] args) {

		Mod182CabeceraDTO dto = new Mod182CabeceraDTO();
		dto.year = 2016;
		dto.cif = "R7800543F";
		dto.nombre = "Parroquia de Santa Catalina de Siena";
		dto.tfno = "915512505";
		dto.contacto = "Mauricio A. Palacios Gutiérrez";
		dto.numRegistros = 14;
		dto.sumImporte = 55000.33d;

		String aux = dto.formatCabecera();
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
	 * @return the cif
	 */
	public String getCif() {
		return cif;
	}
	/**
	 * @param cif the cif to set
	 */
	public void setCif(String cif) {
		this.cif = cif;
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
	 * @return the tfno
	 */
	public String getTfno() {
		return tfno;
	}
	/**
	 * @param tfno the tfno to set
	 */
	public void setTfno(String tfno) {
		this.tfno = tfno;
	}
	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}
	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	/**
	 * @return the numRegistros
	 */
	public Integer getNumRegistros() {
		return numRegistros;
	}
	/**
	 * @param numRegistros the numRegistros to set
	 */
	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
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
	
}
