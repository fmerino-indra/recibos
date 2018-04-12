package es.cnc.suscripciones.front;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import es.cnc.suscripciones.domain.ParroquiaHasParroco;
import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.suscripciones.dto.FilterBaseDTO;
import es.cnc.suscripciones.dto.FilterHolder;
import es.cnc.suscripciones.front.dto.CertificadoDTO;
import es.cnc.suscripciones.front.export.pdf.itext.util.PDFBuilder;
import es.cnc.suscripciones.front.export.pdf.jasper.util.JasperPdfViewResolver;
import es.cnc.suscripciones.front.response.ResponseList;
import es.cnc.suscripciones.services.certificado.CertificadoService;
import es.cnc.util.LocalDateUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@RestController()
@RequestMapping("{contextPath}/certificados")
public class CertificadosController extends AbstractController<Suscripcion> {
	@Autowired
	CertificadoService certificadoService;

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", params = { "page", "start", "limit",
			"filter" })

	public ResponseList<List<CertificadoDTO>> listCertificados(@RequestParam("filter") String filter,
			@RequestParam("limit") String limit, @RequestParam("page") String page, @RequestParam("start") String start)
			throws JsonParseException, JsonMappingException, IOException {
		ResponseList<List<CertificadoDTO>> returnContainerList = new ResponseList<>();

		FilterBaseDTO<Integer> idPersona = null;
		FilterHolder<? extends Collection<FilterBaseDTO<?>>> filterHolder = null;
		;

		filterHolder = buildFilters(filter);
		if (filterHolder != null && !filterHolder.isEmpty() && filterHolder.isActive()) {
			idPersona = (FilterBaseDTO<Integer>) filterHolder.get("id");
		}
		List<CertificadoDTO> list = null;

		list = certificadoService.findListForCetificado(idPersona.getValue());
		returnContainerList.setData(list);
		returnContainerList.setTotalCount(list.size());
		returnContainerList.setSuccess(true);
		return returnContainerList;
	}

	/**
	 * Generates a PDF certificate using Jasper
	 * 
	 * @param idPersona
	 * @param idCertificado
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * 
	 */
	@RequestMapping(path = { "/{idPersona}/{idCertificado}" }, method = RequestMethod.GET, produces = "application/pdf")
	public ModelAndView detailCertificados(@PathVariable("idPersona") Integer idPersona,
			@PathVariable("idCertificado") Integer idCertificado)
			throws JsonParseException, JsonMappingException, IOException {
		ModelAndView mav = null;

		CertificadoDTO dto = null;
		dto = certificadoService.getCertificado(idPersona, idCertificado);
		mav = new ModelAndView(JasperPdfViewResolver.JASPER_PDF_CERTIFICATE_VIEW, "dto", dto);

		mav.addObject("parameters", certificadoService.obtainPHPData(dto));
		mav.addObject("fileName", "CertificadoDonaciones.pdf");

		return mav;
	}

//	private Map<String, Object> obtainPHPData(CertificadoDTO dto) {
//		Map<String, Object> parameters = new HashMap<>();
//		ParroquiaHasParroco php = null;
//
//		php = certificadoService.getParrocoActivo();
//		if (php != null) {
//			parameters.put("parroco", php.getParrocoId().getNombre());
//			parameters.put("nombreFirma", php.getParrocoId().getNombreFirma());
//			parameters.put("parroquia", php.getParroquiaId().getNombre());
//			parameters.put("suscriptor", dto.getNombre());
//			parameters.put("nifSuscriptor", dto.getNif());
//			parameters.put("anyo", dto.getYear().toString());
//			parameters.put("importe", new Float(dto.getSumImporte()));
//			parameters.put("fecha", LocalDateUtil.localDateToDate(LocalDate.now()));
//			parameters.put("poblacion", php.getParroquiaId().getParroquiaAux().getPoblacion());
//			parameters.put("cp", php.getParroquiaId().getParroquiaAux().getCp());
//			parameters.put("direccion", php.getParroquiaId().getParroquiaAux().getDireccion());
//			parameters.put("telefono", php.getParroquiaId().getTelefono());
//		} else {
//			throw new RuntimeException("[CertificadosController] Error al localizar datos de la Parroquia");
//		}
//		return parameters;
//	}
//
	// @RequestMapping(path = { "/{idPersona}/{idCertificado}" }, method =
	// RequestMethod.GET, produces="application/pdf")
	public ResponseEntity<String> anotherCertificados(@PathVariable("idPersona") Integer idPersona,
			@PathVariable("idCertificado") Integer idCertificado) throws Exception {

		CertificadoDTO dto = null;
		dto = certificadoService.getCertificado(idPersona, idCertificado);

		byte[] pdf = null;
		pdf = PDFBuilder.renderMergedOutputModel(dto);

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setContentDispositionFormData("attachment", "CertificadoDonaciones.pdf");
		headers.setContentLength(pdf.length);

		String base64 = null;
		base64 = Base64.getMimeEncoder().encodeToString(pdf);
		headers.setContentLength(base64.length());
		return new ResponseEntity<String>(base64, headers, HttpStatus.OK);
	}

	/**
	 * Generates a PDF certificate using Jasper, and return it on HttpResponse
	 * with Base64 encoding. The client side must do:
	 * window.open('data:application/pdf;base64,'+response.responseText,
	 * '_blank', 'resizable=no, status=no, location=no, scrollbars=no,
	 * height=650, width=840'); Doesn't work in Chrome since 2017.
	 * 
	 * @param idPersona
	 * @param idCertificado
	 * @return
	 */
	@Deprecated
	@RequestMapping(path = {
			"/base64/{idPersona}/{idCertificado}" }, method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<String> getCertificadoByResponseString(@PathVariable("idPersona") Integer idPersona,
			@PathVariable("idCertificado") Integer idCertificado) {

		CertificadoDTO dto = null;
		dto = certificadoService.getCertificado(idPersona, idCertificado);

		// JasperReports

		byte[] pdf = null;
		try {
			pdf = generate(dto);
		} catch (JRException e) {
			e.printStackTrace();
		}

		// FileOutputStream fos = null;
		//
		// try {
		// fos = new FileOutputStream("salida.pdf");
		// fos.write(pdf);
		// fos.close();
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		// headers.setContentType(new MediaType("application", "pdf"));
		headers.setContentDispositionFormData("attachment", "CertificadoDonaciones.pdf");
		headers.setContentLength(pdf.length);

		String base64 = null;
		base64 = Base64.getMimeEncoder().encodeToString(pdf);
		// base64 = Base64.getMimeDecoder().decode(pdf);
		headers.setContentLength(base64.length());
		return new ResponseEntity<String>(base64, headers, HttpStatus.OK);
	}

	private byte[] generate(CertificadoDTO dto) throws JRException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		JasperReport jr = null;
		Map<String, Object> parameters = new HashMap<>();
		JasperPrint jp = null;

		is = getClass().getResourceAsStream("/withholding-v3.jasper");
		jr = (JasperReport) JRLoader.loadObject(is);

		ParroquiaHasParroco php = null;
		php = certificadoService.getParrocoActivo();
		if (php != null) {
			parameters.put("parroco", php.getParrocoId().getNombre());
			parameters.put("parroquia", php.getParroquiaId().getNombre());
			parameters.put("suscriptor", dto.getNombre());
			parameters.put("nifSuscriptor", dto.getNif());
			parameters.put("anyo", dto.getYear().toString());
			parameters.put("importe", new Float(dto.getSumImporte()));
			parameters.put("fecha", LocalDateUtil.localDateToDate(LocalDate.now()));
			parameters.put("poblacion", php.getParroquiaId().getParroquiaAux().getPoblacion());
		} else {
			throw new RuntimeException("[CertificadosController] Error al localizar datos de la Parroquia");
		}

		// parameters.put("parroco", "Mauricio A. Palacios Gutiérrez");
		// parameters.put("parroquia", "Santa Catalina de Siena");
		// parameters.put("suscriptor", dto.getNombre());
		// parameters.put("anyo", dto.getYear().toString());
		// parameters.put("importe", new Float(dto.getSumImporte()));
		// parameters.put("fecha",
		// LocalDateUtil.localDateToDate(LocalDate.now()));
		// parameters.put("poblacion", "Madrid");

		JRDataSource ds = new JREmptyDataSource();
		jp = JasperFillManager.fillReport(jr, parameters, ds);

		JasperExportManager.exportReportToPdfStream(jp, baos);
		return baos.toByteArray();
	}
}