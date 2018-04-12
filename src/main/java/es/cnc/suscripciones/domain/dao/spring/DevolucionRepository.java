package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

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

	@Query("SELECT DISTINCT d"
			+ " FROM Devoluciones d"
			+ " LEFT JOIN FETCH d.reason r"
			+ " LEFT JOIN FETCH d.idMsgDevolucion xml"
			+ " INNER JOIN FETCH d.emision e"
			+ " INNER JOIN FETCH e.idCabecera ce"
			+ " INNER JOIN FETCH e.idSuscripcion psd"
			+ " INNER JOIN FETCH psd.idSuscripcion s"
			+ " INNER JOIN FETCH s.persona p"
			+ " WHERE ce.anyo >= :fromYear"
			+ " AND ce.anyo <= :toYear"
			+ " AND d.fechaBaja is null")
	public List<Devoluciones> findDevolucionesByPeriod(@Param("fromYear") Integer fromYear, @Param("toYear") Integer toYear);
}
