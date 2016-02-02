package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cnc.suscripciones.domain.Emision;

public interface EmisionRepository extends JpaRepository<Emision, Integer> {

}
