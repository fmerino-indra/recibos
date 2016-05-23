package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.cnc.suscripciones.domain.Meses;

public interface MesesRepository extends JpaRepository<Meses, Integer> {
	@Query("select m from Meses m JOIN FETCH m.periodoes p")
	public List<Meses> findMesesWithPeriodos();
}