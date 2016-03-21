package es.cnc.suscripciones.services.suscripcion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.suscripciones.domain.dao.spring.SuscripcionRepository;

@Component("suscripcionService")
public class SuscripcionServiceImpl implements SuscripcionService {

	@Autowired
	private SuscripcionRepository suscripcionRepository;
	
	public SuscripcionServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Page<Suscripcion> findSuscripcionesActivas(Integer page, Integer start, Integer limit) {
		PageRequest pr = new PageRequest(page, limit);
		Page<Suscripcion> response = suscripcionRepository.findSuscripcionesActivas(pr);
		return response;
	}

	@Override
	public Suscripcion findSuscripcionById(Integer id) {
		return suscripcionRepository.findSuscripcionById(id);
	}

}
