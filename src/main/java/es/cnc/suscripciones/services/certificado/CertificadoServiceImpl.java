package es.cnc.suscripciones.services.certificado;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stasiena.sepa.util.NIFUtil;

import es.cnc.suscripciones.domain.ParroquiaHasParroco;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.dao.spring.OtrosRepository;
import es.cnc.suscripciones.domain.dao.spring.ParroquiaHasParrocoRepository;
import es.cnc.suscripciones.domain.dao.spring.PersonaRepository;
import es.cnc.suscripciones.front.dto.CertificadoDTO;
import es.cnc.util.LocalDateUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Component("certificadoService")
public class CertificadoServiceImpl implements CertificadoService {

	@Autowired
	private OtrosRepository otrosRepository;
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private ParroquiaHasParrocoRepository phpRepository;

	Logger logger;

	public CertificadoServiceImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}
	@Override
	public List<CertificadoDTO> findListForCetificado(Integer idPersona) {
		List<CertificadoDTO> aux = null;
		Persona p = null;
		aux = otrosRepository.findEmissionSummaryByPersonDTO(idPersona);
		p = personaRepository.findByPrimaryKey(idPersona);
		for (CertificadoDTO dto: aux) {
			dto.setPersona(p);
		}
		return aux;
	}

	@Override
	public CertificadoDTO getCertificado(Integer idPersona, Integer idCertificado) {
		List<CertificadoDTO> aux = null;
		Persona p = null;
		CertificadoDTO retorno = null;
		
		aux = otrosRepository.findEmissionSummaryByPersonDTO(idPersona);
		p = personaRepository.findByPrimaryKey(idPersona);
		for (CertificadoDTO dto: aux) {
			if (dto.getYear().equals(idCertificado)) {
				retorno = dto;
				retorno.setPersona(p);
				break;
			}
		}
		return retorno;
	}

	@Override
	public ParroquiaHasParroco getParrocoActivo() {
		ParroquiaHasParroco php = null;
		php = phpRepository.findActiveParroquiaHasParroco();
		return php;
	}

	private byte[] generate(CertificadoDTO dto) throws JRException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream is = null;
			JasperReport jr = null;
			Map<String, Object> parameters = new HashMap<>();
			JasperPrint jp = null; 
	
			
			is = getClass().getResourceAsStream("/withholding-v3.jasper");
			jr = (JasperReport)JRLoader.loadObject(is);
			
	//		parameters.put("parroco", "Mauricio A. Palacios Gutiérrez-Ballón");
	//		parameters.put("parroquia", "Santa Catalina de Siena");
	//		parameters.put("suscriptor", "Félix Merino Martínez de Pinillos");
	//		parameters.put("anyo", "2017");
	//		parameters.put("importe", new Float(9999.56));
	//		parameters.put("fecha", LocalDateUtil.localDateToDate(LocalDate.now()));
	//		parameters.put("poblacion", "Madrid");
	//		
			ParroquiaHasParroco php = null;
			php = getParrocoActivo();
			if (php != null) {
				parameters.put("parroco", php.getParrocoId().getNombre());
				parameters.put("nombreFirma", php.getParrocoId().getNombreFirma());
				parameters.put("parroquia", php.getParroquiaId().getNombre());
				parameters.put("suscriptor", dto.getNombre());
				parameters.put("nifSuscriptor", dto.getNif());
				parameters.put("anyo", dto.getYear().toString());
				parameters.put("importe", new Float(dto.getSumImporte()));
				parameters.put("fecha", LocalDateUtil.localDateToDate(LocalDate.now()));
				parameters.put("poblacion", php.getParroquiaId().getParroquiaAux().getPoblacion());
				parameters.put("cp", php.getParroquiaId().getParroquiaAux().getCp());
				parameters.put("direccion", php.getParroquiaId().getParroquiaAux().getDireccion());
				parameters.put("telefono", php.getParroquiaId().getTelefono());

			} else {
				throw new RuntimeException("[CertificadosController] Error al localizar datos de la Parroquia");
			}
			
	//		parameters.put("parroco", "Mauricio A. Palacios Gutiérrez");
	//		parameters.put("parroquia", "Santa Catalina de Siena");
	//		parameters.put("suscriptor", dto.getNombre());
	//		parameters.put("anyo", dto.getYear().toString());
	//		parameters.put("importe", new Float(dto.getSumImporte()));
	//		parameters.put("fecha", LocalDateUtil.localDateToDate(LocalDate.now()));
	//		parameters.put("poblacion", "Madrid");
	
			JRDataSource ds = new JREmptyDataSource();
			jp = JasperFillManager.fillReport(jr, parameters, ds);
			
			JasperExportManager.exportReportToPdfStream(jp, baos);
			return baos.toByteArray();
		}

	@Override
	public void generateCertificates(Integer year) {
		List<CertificadoDTO> lista = null;
		List<Persona> listaPersonas = null;
		byte [] certificado = null;
		FileOutputStream fos = null;

		listaPersonas = otrosRepository.findPersonasWithEmisionsByYear(year);
		lista = new ArrayList<>(listaPersonas.size());
		for (Persona p : listaPersonas) {
			lista.add(otrosRepository.findEmissionSummaryByNifAndYearDTO(p.getNif(), year));
		}

		Path ficheroPath = null;
		Path camino = null;
		String ficheroName = null;
		
		try {
			camino = calcPath(year);
		} catch (IOException e1) {
			throw new RuntimeException("No se ha podido crear el directorio:"+year,e1);
		}
		int i=1;
		for (CertificadoDTO dto : lista) {
			try {
				if (NIFUtil.isNIFValid(dto.getNif())) {
					certificado = generate(dto);
					if (certificado != null) {
					try {
						ficheroName = calcFilename(dto);
						ficheroPath = Paths.get(camino.toAbsolutePath().toString(), ficheroName);
						ficheroPath = Files.createFile(ficheroPath);

						fos = new FileOutputStream(ficheroPath.toFile());

						fos.write(certificado);
						fos.close();
						logger.info("[CertificadoServiceImpl] Generated PDF Certificate:{}", ficheroName);
					} catch (FileNotFoundException e) {
						logger.error("[CertificadoServiceImpl] Error saving PDF Certificate:FileNotFoundException:{}", ficheroName);
					} catch (IOException e) {
						logger.error("[CertificadoServiceImpl] Error saving PDF Certificate:IOException:{}", ficheroName);
					}
					} else {
						logger.warn("[CertificadoServiceImpl] PDF Certificate is null:{}", ficheroName);
					}
				}
			} catch (JRException e) {
				logger.error("[CertificadoServiceImpl] Error generating PDF Certificate:{}", ficheroName);
			}
		}
	}

	private Path calcPath(Integer year) throws IOException {
		Path camino = Paths.get("pdf/pdfProduction/", year.toString(), "/certificados");
		try {
			Files.deleteIfExists(camino);
			Files.createDirectories(camino);
		} catch (IOException e) {
			logger.error("[CertificadoServiceImpl] Error al crear el directorio:" + camino.toString());
			throw e;
		}
		return camino;
	}
	
	private String calcFilename(CertificadoDTO dto) {
		StringBuffer aux = new StringBuffer();
		aux = aux.append(dto.getNombre());
		aux = aux.append("-");
		aux = aux.append(dto.getNif());
		aux = aux.append(".pdf");
		return aux.toString();
	}
	
}
