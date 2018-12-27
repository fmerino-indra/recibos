package es.cnc.suscripciones.services.domiciliacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.Domiciliacion;
import es.cnc.suscripciones.domain.dao.spring.DomiciliacionRepository;

@Component("domiciliacionService")
public class DomiciliacionServiceImpl implements DomiciliacionService {

	@Autowired
	DomiciliacionRepository domiciliacionRepository;
	
	@Override
	public Domiciliacion findDomiciliacion(String iban) {
		return domiciliacionRepository.findDomiciliacionByIban(iban);
	}

	@Override
	public Domiciliacion createDomiciliacion(Domiciliacion dom) {
		return domiciliacionRepository.save(dom);
	}

}
