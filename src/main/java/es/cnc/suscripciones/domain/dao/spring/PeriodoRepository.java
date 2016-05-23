package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.cnc.suscripciones.domain.Meses;
import es.cnc.suscripciones.domain.Periodo;

public interface PeriodoRepository extends JpaRepository<Periodo, String> {
	@Query("select p from Periodo p JOIN FETCH p.meseses m where m.idMes = ?1")
	public List<Periodo> findPeriodosByMes(Integer idMes);

	@Query("select p from Periodo p JOIN FETCH p.meseses m where m = ?1")
	public List<Periodo> findPeriodosByMes(Meses mes);
}
