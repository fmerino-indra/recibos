package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.services.mod182.Mod182DeclaradosDTO;

public interface Mod182Repository extends Repository<Emision, Integer> {
	
	@Query(value = "SELECT new es.cnc.suscripciones.services.mod182.Mod182DeclaradosDTO(p.id, ce.anyo, p.nombre, sum(e.importe), p.nif) "
			+ "FROM Emision e"
			+ " INNER JOIN e.idSuscripcion psd"
			+ " INNER JOIN psd.idSuscripcion s"
			+ " INNER JOIN s.persona p"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE ce.anyo = :year"
			+ " GROUP BY (p)"
			+ " ORDER BY (p.nombre)")
	public List<Mod182DeclaradosDTO> findAllEmissionSummaryByYear(@Param("year") Integer year);
}
