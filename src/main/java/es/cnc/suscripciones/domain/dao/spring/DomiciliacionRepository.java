package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cnc.suscripciones.domain.Domiciliacion;

public interface DomiciliacionRepository extends JpaRepository<Domiciliacion, Integer> {
	
}
