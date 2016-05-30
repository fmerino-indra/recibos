package es.cnc.suscripciones.services.persona;

import java.util.Collection;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.dao.spring.PersonaRepository;
import es.cnc.suscripciones.dto.FilterBaseDTO;
import es.cnc.suscripciones.dto.FilterHolder;

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
	public Persona createPersona(String nif, String nombre, String domicilio, String cp, String poblacion, String tfno) {
		Persona p = new Persona();
		p.setCp(cp);
		p.setDomicilio(domicilio);
		p.setNif(nif);
		p.setNombre(nombre);
		p.setPoblacion(poblacion);
		p.setTfno(tfno);
		return createPersona(p);
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
		FilterBaseDTO<String> nif = null;
		if (filter == null || filter.isEmpty() || !filter.isActive()) {
			response = findPersonas(page, start, limit);
		} else {//List<FilterBaseDTO<?>>
			nif = (FilterBaseDTO<String>)filter.get("nif");
			response = personaRepository.findPersonaByNif(nif.getValue(), pr);
		}
		return response;
	}

	@Override
	public Persona findPersonaById(Integer id) {
		return personaRepository.findFullById(id);
	}

	@Override
	public void deletePersona(Integer id) {
		personaRepository.delete(id);
	}

}
