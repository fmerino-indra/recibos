package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cnc.suscripciones.domain.Donativo;

public interface DonativoRepository extends JpaRepository<Donativo, Integer> {

}
