package es.cnc.suscripciones.services.mod182;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.stasiena.sepa.util.NIFUtil;

import es.cnc.suscripciones.domain.Parroco;
import es.cnc.suscripciones.domain.Parroquia;
import es.cnc.suscripciones.domain.ParroquiaHasParroco;
import es.cnc.suscripciones.domain.dao.spring.Mod182Repository;
import es.cnc.suscripciones.domain.dao.spring.OtrosRepository;
import es.cnc.suscripciones.domain.dao.spring.ParroquiaHasParrocoRepository;
import es.cnc.suscripciones.front.dto.CertificadoDTO;

@Component("mod182Service")
public class Mod182ServiceImpl implements Mod182Service {
	Logger logger;

	@Autowired
	private Mod182Repository mod182Repository;
	
	@Autowired
	private OtrosRepository certificadosRepository;
	
	@Autowired
	private ParroquiaHasParrocoRepository phpRepository;
	
	@Value("${app.xml.file.dir}")
	private String dir;
	
	public Mod182ServiceImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@Override
	public void generateMod182(Integer anyo) throws IOException {
		ParroquiaHasParroco php = null;
		Parroquia pqia = null;
		Parroco pco = null;
		Double total = 0.0d;
 
		Mod182CabeceraDTO cabecera = new Mod182CabeceraDTO();
		List<Mod182DeclaradosDTO> lista = mod182Repository.findAllEmissionSummaryByYear(anyo);
		lista = validarLista(lista);
		boolean recursivo = false;
		Integer recursivoClave = 0;
		php = phpRepository.findActiveParroquiaHasParroco();
		
		if (lista != null) {
			if (php != null) {
				pqia = php.getParroquiaId();
				pco = php.getParrocoId();
				if (pqia != null && pco != null) {
					cabecera.setCif(pqia.getNif());
					cabecera.setNombre(pqia.getNombre());
					cabecera.setYear(anyo);
					cabecera.setContacto(pco.getNombre());
					cabecera.setTfno("915512507");
					cabecera.setNumRegistros(lista.size());
				}
			}

			
			for (Mod182DeclaradosDTO declarado:lista) {
				declarado.setCodProv(28);
				declarado.setClave("A");
				recursivo = calculateRecursividad(declarado.getId(), declarado.getSumImporte(), anyo);
				if (recursivo) {
					recursivoClave = 1;
				} else {
					recursivoClave = 2;
				}
				declarado.setRecurrencia(recursivoClave.toString());
				if (declarado.getSumImporte()<=150.0d) {
					declarado.setDeduccion(75.0f); // TODO
				} else {
					if (recursivo) {
						declarado.setDeduccion(35.0f);
					} else {
						declarado.setDeduccion(30.0f);
					}
				}
				declarado.setEspecie("");
				declarado.setCCAA(12);
				declarado.setDeduccionCCAA(0.0f);
				declarado.setNaturaleza("F");
				declarado.setRevocacion("");
				declarado.setAnyoRevocacion(0);
				declarado.setTipoBien("");
				declarado.setIdentificacionBien("");
				total += declarado.getSumImporte();
				logger.debug("[Mod182ServiceImpl]"+declarado);
			}
			try {
				cabecera.setSumImporte(total);
				writeMod182(cabecera, lista, anyo);
			} catch (IOException e) {
				logger.error("[Mod182ServiceImpl] Error al crear el fichero:" + e.toString());
				throw e;
			}
			
		}
	}
	
	private List<Mod182DeclaradosDTO> validarLista(List<Mod182DeclaradosDTO> lista) {
		List<Mod182DeclaradosDTO> aux = null;
		aux = new ArrayList<>();
		for (Mod182DeclaradosDTO dto : lista) {
			if (NIFUtil.isNIFValid(dto.getNif()))
				aux.add(dto);
		}
		return aux;
	}
	private void writeMod182(Mod182CabeceraDTO cabecera, List<Mod182DeclaradosDTO> declarados, Integer anyo) throws IOException {
		BufferedWriter writer = null;
		Path ficheroPath = null;
		String line = null;
		
		Path camino = calcPathname(anyo);
		String fichero = calcFilename(anyo);

		ficheroPath = Paths.get(camino.toAbsolutePath().toString(), fichero);
		if (Files.deleteIfExists(ficheroPath)) {
			logger.info("[Mod182ServiceImpl] Se ha eliminado el fichero");
		}
		ficheroPath = Files.createFile(ficheroPath);
		writer = Files.newBufferedWriter(ficheroPath, StandardCharsets.ISO_8859_1);
		line = cabecera.formatCabecera();
		logger.info(line);
		writer.write(line);
		writer.newLine();
		for (Mod182DeclaradosDTO dto:declarados) {
			line = dto.formatDeclarante(cabecera.getCif());
			logger.info(line);
			writer.write(line);
			writer.newLine();
		}
		writer.flush();
		writer.close();
	}
	
	private String calcFilename(Integer year) {
		String aux = "AEAT182_";
		aux = aux.concat(year.toString());
		aux = aux.concat(".txt");
		return aux;
	}
	
	private Path calcPathname(Integer year) {
		Path camino = Paths.get("xmlDevelopment", year.toString());
		if (Files.exists(camino)) {
			
		} else {
			try {
				Files.createDirectories(camino);
			} catch (IOException e) {
				logger.error("[Mod182ServiceImpl] Error al crear el directorio:" + camino.toString());
				camino = null;
			}
		}
		return camino;
	}
	
	private boolean calculateRecursividad(Integer id, Double d, Integer year) {
		List<CertificadoDTO> lista = null;
		lista = certificadosRepository.findEmissionSummaryByPersonDTO(id);
		CertificadoDTO dto = null;
		boolean recursivo = (lista != null) && (lista.size() > 0) ;
		
		if (recursivo) {
			dto = buscarCertificadoMenor(lista, 1, year);
			if (dto != null) {
				recursivo = recursivo && dto.getSumImporte() >= d;
				if (lista.size() > 1) {
					dto = buscarCertificadoMenor(lista, 2, year);
					if (dto != null) {
						recursivo = recursivo && dto.getSumImporte() >= d;
					}
				}
			}
		}
		return recursivo;
	}
	
	private CertificadoDTO buscarCertificadoMenor(List<CertificadoDTO> lista, int num, Integer year) {
		CertificadoDTO aux = null;
		if (lista != null) {
			for (CertificadoDTO dto : lista) {
				if (dto.getYear() == (year-num)) {
					aux = dto;
				}
			}
		}
		return aux;
	}
}
