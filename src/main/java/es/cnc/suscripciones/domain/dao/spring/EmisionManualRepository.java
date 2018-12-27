package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cnc.suscripciones.domain.EmisionManual;

public interface EmisionManualRepository extends JpaRepository<EmisionManual, Integer> {
}
