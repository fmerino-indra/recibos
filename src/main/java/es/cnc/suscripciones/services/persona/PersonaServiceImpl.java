package es.cnc.suscripciones.services.persona;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.stasiena.sepa.util.FileUtils;

import es.cnc.jasper.data.FichaPersonaCollectionDataSource;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.dao.spring.PersonaRepository;
import es.cnc.suscripciones.dto.FilterBaseDTO;
import es.cnc.suscripciones.dto.FilterHolder;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Component("personaService")
public class PersonaServiceImpl implements PersonaService {
	Logger logger;

	@Autowired
	private PersonaRepository personaRepository;
	
	public PersonaServiceImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	@Transactional
	public Persona createPersona(Persona p) {
		p = personaRepository.save(p);
		return p;
	}
	
	@Override
	public Persona createPersona(String nif, String nombre, String domicilio, String cp, String poblacion,
			String tfno) {
		return createPersona(nif, nombre, domicilio, cp, poblacion,tfno, null);
	}

	@Override
	public Persona createPersona(String nif, String nombre, Persona antecesor) {
		if (antecesor == null)
			throw new RuntimeException("El antecesor es nulo.");
		return createPersona(nif, nombre, antecesor.getDomicilio(), antecesor.getCp(), antecesor.getPoblacion(), antecesor.getTfno(), antecesor);
	}

	@Override
	public Page<Persona> findPersonas(Integer page, Integer start, Integer limit) {
		PageRequest pr = new PageRequest(page, limit);
		Page<Persona> response = personaRepository.findAll(pr);
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Persona> findPersonasWithCriteria(FilterHolder<? extends Collection<FilterBaseDTO<?>>> filter, Integer page, Integer start, Integer limit) {
		PageRequest pr = new PageRequest(page, limit);
		Page<Persona> response = null;
		String nif = null;
		String nombre = null;
		FilterBaseDTO<String> nifDTO = null;
		FilterBaseDTO<String> nombreDTO = null;
//		if (filter == null || filter.isEmpty() || !filter.isActive()) {
//			response = findPersonas(page, start, limit);
//		} else {//List<FilterBaseDTO<?>>
			nifDTO = (FilterBaseDTO<String>)filter.get("nif");
			nombreDTO = (FilterBaseDTO<String>)filter.get("nombre");
			if (nifDTO != null)
				nif = nifDTO.getValue();
			else
				nif = "";
			if (nombreDTO != null)
				nombre = nombreDTO.getValue();
			else
				nombre = "";
			response = personaRepository.findAllPersonasLikeNif(nif, nombre, pr);
//		}
		return response;
	}

	@Override
	public Persona findPersonaById(Integer id) {
		return personaRepository.findFullById(id);
//		return personaRepository.findOne(id);
	}

	@Override
	public void deletePersona(Integer id) {
		personaRepository.delete(id);
	}

	@Override
	public List<Persona> findPersonasByNif(String nif) {
		return personaRepository.findAllPersonasByNif(nif);
	}

	private Persona createPersona(String nif, String nombre, String domicilio, String cp, String poblacion, String tfno, Persona antecesor) {
		Persona p = new Persona();
		p.setCp(cp);
		p.setDomicilio(domicilio);
		p.setNif(nif);
		p.setNombre(nombre);
		p.setPoblacion(poblacion);
		p.setTfno(tfno);
		if (antecesor != null)
			p.setAntecesor(antecesor);
		return createPersona(p);
	}


	@Override
	public void generateFichaSuscriptores() {
		List<Persona> personas = personaRepository.findPersonasWithActiveSuscripcionesDomiciliacionBancos();
		
		Path ficheroPath = null;
		Path camino = null;
		String ficheroName = null;
		int year;
		
		byte [] ficha = null;
		FileOutputStream fos = null;
		
		year = LocalDate.now().getYear();
		try {
			camino = calcPath(year);
		} catch (IOException e1) {
			throw new RuntimeException("No se ha podido crear el directorio:"+year,e1);
		}
		int i=1;
		for (Persona p:personas) {
			try {
				ficha = generate(p);
				if (ficha != null) {
				try {
					try {
						ficheroName = calcFilename(p);
						ficheroPath = Paths.get(camino.toAbsolutePath().toString(), ficheroName);
						if (Files.exists(ficheroPath)) {
							ficheroName = calcFileNameDup(p);
							ficheroPath = Paths.get(camino.toAbsolutePath().toString(), ficheroName);
						}
					} catch (InvalidPathException ipe) {
						ficheroName = calcFilenameWithId(p);
						ficheroPath = Paths.get(camino.toAbsolutePath().toString(), ficheroName);
					}
					ficheroPath = Files.createFile(ficheroPath);

					fos = new FileOutputStream(ficheroPath.toFile());

					fos.write(ficha);
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
			} catch (JRException e) {
				logger.error("[CertificadoServiceImpl] Error generating PDF Certificate:{}", ficheroName);
			}
			
		}
	}

	private Path calcPath(Integer year) throws IOException {
		Path camino = Paths.get("pdf/pdfProduction/", year.toString(), "/fichaSuscriptor");
		try {
			if (Files.exists(camino)) {
				emptyDir(camino);
			}
//			Files.deleteIfExists(camino);
			Files.createDirectories(camino);
		} catch (IOException e) {
			logger.error("[PersonaServiceImpl] Error al crear el directorio:" + camino.toString());
			throw e;
		}
		return camino;
	}
	
	private String calcFilename(Persona dto) {
		StringBuffer aux = new StringBuffer();
		aux = aux.append(dto.getNombre()!=null?dto.getNombre():"NO NAME");
		aux = aux.append("-");
		aux = aux.append(dto.getNif()!=null?dto.getNif():"NO NIF");
		aux = aux.append(".pdf");
		return aux.toString();
	}

	
	private String calcFileNameDup(Persona dto) {
		StringBuffer aux = new StringBuffer();
		aux = aux.append(dto.getNombre()!=null?dto.getNombre():"NO NAME");
		aux = aux.append("-");
		aux = aux.append(dto.getNif()!=null?dto.getNif():"NO NIF");
		aux = aux.append("-");
		aux = aux.append(dto.getId()!=null?dto.getId():"NO ID");
		aux = aux.append(".pdf");
		return aux.toString();
	}

	private String calcFilenameWithId(Persona dto) {
		StringBuffer aux = new StringBuffer();
		aux = aux.append(dto.getId()!=null?dto.getId():"NO ID");
		aux = aux.append("-");
		aux = aux.append(dto.getNif()!=null?dto.getNif():"NO NIF");
		aux = aux.append("-");
		aux = aux.append(System.currentTimeMillis());
		aux = aux.append(".pdf");
		return aux.toString();
	}

	private byte[] generate(Persona dto) throws JRException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		JasperReport jr = null;
		Map<String, Object> parameters = new HashMap<>();
		JasperPrint jp = null; 

		
		is = getClass().getResourceAsStream("/ficha-suscriptor.jasper");
		jr = (JasperReport)JRLoader.loadObject(is);
		
		parameters.put("parroquia", "Santa Catalina de Siena");

		Set<Persona> mapa = new HashSet<>();
		mapa.add(dto);
		JRDataSource ds = new FichaPersonaCollectionDataSource(mapa);
		jp = JasperFillManager.fillReport(jr, parameters, ds);
		
		JasperExportManager.exportReportToPdfStream(jp, baos);
		return baos.toByteArray();
	}
	private void emptyDir(Path camino) throws IOException {
		FileUtils.clearDirectory(camino.toFile(), true);
	}
}
