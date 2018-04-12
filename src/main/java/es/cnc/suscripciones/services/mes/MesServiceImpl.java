package es.cnc.suscripciones.services.mes;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.Meses;
import es.cnc.suscripciones.domain.dao.spring.MesesRepository;

@Component("mesesService")
public class MesServiceImpl implements MesService {

	Logger logger;

	@Autowired
	MesesRepository mesesRepository;

	@Override
	public List<Meses> findAll() {
		return mesesRepository.findAll();
	}

}
