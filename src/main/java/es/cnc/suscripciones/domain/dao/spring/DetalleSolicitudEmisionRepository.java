package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cnc.suscripciones.domain.DetalleSolicitudEmision;

public interface DetalleSolicitudEmisionRepository extends JpaRepository<DetalleSolicitudEmision, Integer> {
}
