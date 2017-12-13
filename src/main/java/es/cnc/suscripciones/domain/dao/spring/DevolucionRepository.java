package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Devoluciones;
import es.cnc.suscripciones.domain.Emision;

public interface DevolucionRepository extends JpaRepository<Devoluciones, Integer> {
	@Query("SELECT d "
			+ " FROM Devoluciones d"
			+ " LEFT JOIN FETCH d.idMsgDevolucion msg"
			+ " LEFT JOIN FETCH d.reason r"
			+ " WHERE d.emision = :emision"
			+ " AND d.fechaBaja is null")
	public Devoluciones findActiveReturnedByEmision(@Param("emision") Emision e);
}
