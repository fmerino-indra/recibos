package es.cnc.suscripciones.services.certificado;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stasiena.sepa.util.NIFUtil;

import es.cnc.suscripciones.domain.ParroquiaHasParroco;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.dao.spring.DonativoRepository;
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
	@Autowired DonativoRepository donativoRepository;
	
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
		List<CertificadoDTO> donativos = null;
		Persona p = null;
		p = personaRepository.findByPrimaryKey(idPersona);
		
		aux = otrosRepository.findEmissionSummaryByNifDTO(p.getNif());
		donativos = donativoRepository.findDonativoSummaryByNifDTO(p.getNif());
		
		for (CertificadoDTO dto: aux) {
			dto.setPersona(p);
		}

		for (CertificadoDTO dto: donativos) {
			dto.setPersona(p);
		}
		
		aux = joinCertificatesList(aux, donativos);
		
		return aux;
	}

//	private List<CertificadoDTO> joinCertificatesList(List<CertificadoDTO> emisiones, List<CertificadoDTO> donativos) {
//		CertificadoDTO certificado;
//		for (CertificadoDTO dto: emisiones) {
//			certificado = buscarCertificadoEnLista(dto,donativos);
//			if (certificado != null) {
//				dto = joinCertificates(dto, certificado);
//				break;
//			}
//		}
//		return emisiones;
//	}
	
	private List<CertificadoDTO> joinCertificatesList(List<CertificadoDTO> emisionesList, List<CertificadoDTO> donativosList) {
		CertificadoDTO certificado;
		Map<String, CertificadoDTO> emisiones=null;
		Map<String, CertificadoDTO> donativos=null;
		emisiones=toMap(emisionesList);
		donativos=toMap(donativosList);
		List<CertificadoDTO> retorno = null;
		retorno = new ArrayList<>();
	
		for (String emisionKey: emisiones.keySet()) {
			if (donativos.containsKey(emisionKey)) {
				certificado = joinCertificates(emisiones.get(emisionKey), donativos.get(emisionKey));
			} else {
				certificado = emisiones.get(emisionKey);
			}
			retorno.add(certificado);
		}
		
		for (String donativoKey: donativos.keySet()) {
			if (!emisiones.containsKey(donativoKey)) {
				retorno.add(donativos.get(donativoKey));
			}
		}
		return retorno;
	}
	
	private Map<String, CertificadoDTO> toMap(List<CertificadoDTO> emisiones) {
		Map<String, CertificadoDTO> retorno = null;
		if (emisiones != null) {
			retorno = new LinkedHashMap<>();
			for (CertificadoDTO dto : emisiones) {
				retorno.put(generateCertificateKey(dto), dto);
			}
		}
		return retorno;
	}
	
	private String generateCertificateKey(CertificadoDTO cert) {
		return cert.getNif() + "-" + cert.getYear();
	}
	
	/**
	 * Returns a CertificadoDTO summary of 2 certificates: emisionDTO & donativoDTO
	 * @param emisionDTO
	 * @param donativoDTO must be != null
	 * @return
	 */
	private CertificadoDTO joinCertificates(CertificadoDTO emisionDTO, CertificadoDTO donativoDTO) {
		CertificadoDTO cert = null;
		if (emisionDTO.getYear().equals(donativoDTO.getYear()) && 
				emisionDTO.getNif().equals(donativoDTO.getNif())) {
			cert = new CertificadoDTO(emisionDTO.getYear(), emisionDTO.getNombre(), emisionDTO.getSumImporte()+donativoDTO.getSumImporte(), emisionDTO.getCount()+donativoDTO.getCount(), 
					emisionDTO.getNif(), emisionDTO.getIdPersona());
			cert.setPersona(emisionDTO.getPersona());
		}
		return cert;
	}
//	/**
//	 * Return the CertificadoDTO of a list of donations
//	 * @param emisionDTO
//	 * @param donativos
//	 * @return
//	 */
//	private CertificadoDTO buscarCertificadoEnLista(CertificadoDTO emisionDTO, List<CertificadoDTO> donativos) {
//		for (CertificadoDTO dto : donativos) {
//			if (dto.getYear().equals(emisionDTO.getYear()) && 
//					dto.getNif().equals(emisionDTO.getNif()))
//				return dto;
//		}
//		return null;
//	}
	
	@Override
	public CertificadoDTO getCertificado(Integer idPersona, Integer idCertificadoYear) {
		Persona p = null;
		CertificadoDTO retorno = null;
		CertificadoDTO emisionDTO = null, donativoDTO = null;
		
		p = personaRepository.findByPrimaryKey(idPersona);
		
		emisionDTO = otrosRepository.findEmissionSummaryByNifAndYearDTO(p.getNif(), idCertificadoYear);
		donativoDTO = donativoRepository.findDonativoSummaryByNifAndYearDTO(p.getNif(), idCertificadoYear);
		
		emisionDTO.setPersona(p);

		if (donativoDTO != null) {
			donativoDTO.setPersona(p);
			retorno = joinCertificates(emisionDTO, donativoDTO);
		} else {
			retorno = emisionDTO;
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

			parameters = obtainPHPData(dto);
//			ParroquiaHasParroco php = null;
//			php = getParrocoActivo();
//			if (php != null) {
//				parameters.put("parroco", php.getParrocoId().getNombre());
//				parameters.put("nombreFirma", php.getParrocoId().getNombreFirma());
//				parameters.put("parroquia", php.getParroquiaId().getNombre());
//				parameters.put("suscriptor", dto.getNombre());
//				parameters.put("nifSuscriptor", dto.getNif());
//				parameters.put("anyo", dto.getYear().toString());
//				parameters.put("importe", new Float(dto.getSumImporte()));
//				parameters.put("fecha", LocalDateUtil.localDateToDate(LocalDate.now()));
//				parameters.put("poblacion", php.getParroquiaId().getParroquiaAux().getPoblacion());
//				parameters.put("cp", php.getParroquiaId().getParroquiaAux().getCp());
//				parameters.put("direccion", php.getParroquiaId().getParroquiaAux().getDireccion());
//				parameters.put("telefono", php.getParroquiaId().getTelefono());
//
//			} else {
//				throw new RuntimeException("[CertificadosController] Error al localizar datos de la Parroquia");
//			}
			
			JRDataSource ds = new JREmptyDataSource();
			jp = JasperFillManager.fillReport(jr, parameters, ds);
			
			JasperExportManager.exportReportToPdfStream(jp, baos);
			return baos.toByteArray();
		}

	@Override
	public Map<String, Object> obtainPHPData(CertificadoDTO dto) {
		Map<String, Object> parameters = new HashMap<>();
		ParroquiaHasParroco php = null;

		php = this.getParrocoActivo();
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
		return parameters;
	}

	
	
	@Override
	public void generateCertificates(Integer year) {
		List<CertificadoDTO> listaEmisionesDonatios = null;
		
		List<CertificadoDTO> listaEmisiones = null;
		List<CertificadoDTO> listaDonativos = null;
		Persona persona = null;
		byte [] certificado = null;
		FileOutputStream fos = null;

		listaEmisiones = null;
		listaEmisiones=otrosRepository.findEmissionCertificatesByYearDTO(year);
		for (CertificadoDTO aux: listaEmisiones) {
			persona = personaRepository.findByPrimaryKey(aux.getIdPersona());
			if (persona==null) {
				String message = MessageFormat. format("[CertificadoService][generateCertificates] No person with NIF: {}", aux.getNif());
				logger.error(message);
				throw new RuntimeException(message);
			}
			aux.setPersona(persona);
		}

		listaDonativos = donativoRepository.findDonativoSummaryByYearDTO(year);
		for (CertificadoDTO aux: listaDonativos) {
			persona = personaRepository.findByPrimaryKey(aux.getIdPersona());
			if (persona==null) {
				String message = MessageFormat. format("[CertificadoService][generateCertificates] No person with NIF: {}", aux.getNif());
				logger.error(message);
				throw new RuntimeException(message);
			}
			aux.setPersona(persona);
		}
		
		listaEmisionesDonatios=joinCertificatesList(listaEmisiones, listaDonativos);
		
		Path ficheroPath = null;
		Path camino = null;
		Path wrongPath = null;
		String ficheroName = null;
		
		try {
			camino = calcPath(year);
			wrongPath = calcWrongPath(year);
		} catch (IOException e1) {
			throw new RuntimeException("No se ha podido crear el directorio:"+year,e1);
		}
		for (CertificadoDTO dto : listaEmisionesDonatios) {
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
				} else {
					certificado = generate(dto);
					if (certificado != null) {
					try {
						ficheroName = calcFilename(dto);
						ficheroPath = Paths.get(wrongPath.toAbsolutePath().toString(), ficheroName);
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
	
	private Path calcWrongPath(Integer year) throws IOException {
		Path camino = Paths.get("pdf/pdfProduction/", year.toString(), "/certificados/wrong");
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
