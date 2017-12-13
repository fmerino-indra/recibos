package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cnc.suscripciones.domain.MsgDevolucion;

public interface MsgDevolucionRepository extends JpaRepository<MsgDevolucion, Integer> {
}
