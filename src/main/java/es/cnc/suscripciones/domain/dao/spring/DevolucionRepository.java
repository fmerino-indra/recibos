package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.cnc.suscripciones.domain.Devoluciones;
import es.cnc.suscripciones.domain.Emision;

public interface DevolucionRepository extends JpaRepository<Devoluciones, Integer> {
	@Query("SELECT d FROM Devoluciones d"
			+ " WHERE d.emision = ?"
			+ " AND d.fechaBaja is null")
	public Devoluciones findActiveReturnedByEmision(Emision e);
}
