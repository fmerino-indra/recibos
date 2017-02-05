package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.front.dto.CertificadoDTO;

public interface OtrosRepository extends Repository<Emision, Integer> {
	
	@Query(value = "SELECT year(ce.fechaEmision), p.nombre, sum(e.importe), count(e) "
			+ "FROM Emision e"
			+ " INNER JOIN e.idSuscripcion psd"
			+ " INNER JOIN psd.idSuscripcion s"
			+ " INNER JOIN s.persona p"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE p.id = :idPersona"
			+ " GROUP BY (YEAR(ce.fechaEmision))")
	public List<Object[]> findEmissionSummaryByPerson(@Param("idPersona") Integer id);

	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.CertificadoDTO(ce.anyo, p.nombre, sum(e.importe), count(e), p.nif) "
			+ "FROM Emision e"
			+ " INNER JOIN e.idSuscripcion psd"
			+ " INNER JOIN psd.idSuscripcion s"
			+ " INNER JOIN s.persona p"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE p.id = :idPersona"
			+ " GROUP BY (ce.anyo)"
			+ " ORDER BY (ce.anyo) DESC")
	public List<CertificadoDTO> findEmissionSummaryByPersonDTO(@Param("idPersona") Integer id);

//	@Deprecated
//	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.CertificadoDTO(ce.anyo, p.nombre, sum(e.importe), count(e), p.nif) "
//			+ "FROM Emision e"
//			+ " INNER JOIN e.idSuscripcion psd"
//			+ " INNER JOIN psd.idSuscripcion s"
//			+ " INNER JOIN s.persona p"
//			+ " INNER JOIN e.idCabecera ce"
//			+ " WHERE p.id = :idPersona"
//			+ " and ce.anyo = :year"
//			+ " GROUP BY (ce.anyo)"
//			+ " ORDER BY (ce.anyo) DESC")
//	public CertificadoDTO findEmissionSummaryByPersonAndYearDTO(@Param("idPersona") Integer id, @Param("year") Integer year);

	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.CertificadoDTO(ce.anyo, p.nombre, sum(e.importe), count(e), p.nif) "
			+ "FROM Emision e"
			+ " INNER JOIN e.idSuscripcion psd"
			+ " INNER JOIN psd.idSuscripcion s"
			+ " INNER JOIN s.persona p"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE p.nif = :nif"
			+ " and ce.anyo = :year"
			+ " GROUP BY ce.anyo, p.nif"
			+ " ORDER BY (ce.anyo) DESC")
	public CertificadoDTO findEmissionSummaryByNifAndYearDTO(@Param("nif") String nif, @Param("year") Integer year);

	@Query(value = "SELECT p  "
			+ "FROM Emision e"
			+ " INNER JOIN e.idSuscripcion psd"
			+ " INNER JOIN psd.idSuscripcion s"
			+ " INNER JOIN s.persona p"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE ce.anyo = :year"
			+ " GROUP BY (p.nif)"
			+ " ORDER BY (p.nombre) ")
	public List<Persona> findPersonasWithEmisionsByYear(@Param("year") Integer year);
}
