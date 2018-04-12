package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.front.dto.CertificadoDTO;
import es.cnc.suscripciones.front.dto.DashboardDTO;

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

	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.CertificadoDTO(ce.anyo, p.nombre, sum(case when e.devuelto = 0 then e.importe else 0 end), count(e), p.nif) "
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
	
//	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.DashboardDTO(ce.id, ce.anyo, ce.codigoMes, ce.periodo, sum(e.importe), sum(case when e.devuelto = 1 then e.importe else 0 end)) "
//			+ " FROM Emision e"
//			+ " INNER JOIN e.idCabecera ce"
//			+ " GROUP BY (ce.codigoMes, ce.periodo)")
//	public List<DashboardDTO> findNumEmisiones(@Param("desde") Date desde, @Param("hasta") Date hasta);

//	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.DashboardDTO(ce.id, ce.anyo, ce.codigoMes, m.mes, ce.periodo, p.nombre, sum(e.importe), sum(case when e.devuelto = 1 then e.importe else 0 end)) "
//			+ " FROM Emision e"
//			+ " INNER JOIN e.idCabecera ce"
//			+ " INNER JOIN ce.codigoMes m"
//			+ " LEFT JOIN FETCH ce.periodo p"
//			+ " WHERE ce.anyo >= :anyoFrom"
//			+ " AND ce.anyo <= :anyoTo"
//			+ " AND ce.codigoMes >= :mesFrom"
//			+ " AND ce.codigoMes <= :mesTo"
//			+ " GROUP BY (ce.codigoMes, ce.periodo)"
//			+ " ORDER BY ce.anyo, ce.codigoMes")
	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.DashboardDTO(ce.id, ce.anyo, ce.codigoMes, ce.periodo,"
			+ " count(1), sum(case when e.devuelto = 1 then 1 else 0 end),"
			+ " sum(e.importe), sum(case when e.devuelto = 1 then e.importe else 0 end)) "
			+ " FROM Emision e"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE ce.anyo >= :anyoFrom"
			+ " AND ce.anyo <= :anyoTo"
			+ " AND ce.codigoMes >= :mesFrom"
			+ " AND ce.codigoMes <= :mesTo"
			+ " GROUP BY ce.anyo, ce.codigoMes"
			+ " ORDER BY ce.anyo, ce.codigoMes")
	public List<DashboardDTO> findEmissionStatisticsSummary(@Param("anyoFrom") Integer anyoFrom, @Param("anyoTo") Integer anyoTo, @Param("mesFrom") Integer mesFrom, @Param("mesTo") Integer mesTo);

	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.DashboardDTO(ce.id, ce.anyo, ce.codigoMes, ce.periodo,"
			+ " count(1), sum(case when e.devuelto = 1 then 1 else 0 end),"
			+ " sum(e.importe), sum(case when e.devuelto = 1 then e.importe else 0 end)) "
			+ " FROM Emision e"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE ce.anyo >= :anyoFrom"
			+ " AND ce.anyo <= :anyoTo"
			+ " AND ce.codigoMes >= :mesFrom"
			+ " AND ce.codigoMes <= :mesTo"
			+ " GROUP BY ce.anyo, ce.codigoMes, ce.periodo"
			+ " ORDER BY ce.anyo, ce.codigoMes, ce.periodo")
	public List<DashboardDTO> findEmissionStatisticsSummaryNoGroup(@Param("anyoFrom") Integer anyoFrom, @Param("anyoTo") Integer anyoTo, @Param("mesFrom") Integer mesFrom, @Param("mesTo") Integer mesTo);

	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.DashboardDTO(ce.id, ce.anyo, ce.codigoMes, ce.periodo,"
			+ " count(1), sum(case when e.devuelto = 1 then 1 else 0 end),"
			+ " sum(e.importe), sum(case when e.devuelto = 1 then e.importe else 0 end)) "
			+ " FROM Emision e"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE ce.anyo >= :anyoFrom"
			+ " AND ce.anyo <= :anyoTo"
			+ " AND ce.codigoMes >= :mesFrom"
			+ " AND ce.codigoMes <= :mesTo"
			+ " GROUP BY ce.anyo, ce.codigoMes, ce.periodo")
	public List<DashboardDTO> findEmissionStatisticsSummaryNoGroupSort(@Param("anyoFrom") Integer anyoFrom, @Param("anyoTo") Integer anyoTo, @Param("mesFrom") Integer mesFrom, @Param("mesTo") Integer mesTo, Sort sort);
	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.DashboardDTO(ce.id, ce.anyo, ce.codigoMes, ce.periodo,"
			+ " count(1), sum(case when e.devuelto = 1 then 1 else 0 end),"
			+ " sum(e.importe), sum(case when e.devuelto = 1 then e.importe else 0 end)) "
			+ " FROM Emision e"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE ce.anyo >= :anyoFrom"
			+ " AND ce.anyo <= :anyoTo"
			+ " AND ce.codigoMes >= :mesFrom"
			+ " AND ce.codigoMes <= :mesTo"
			+ " AND ce.periodo in :periodos"
			+ " GROUP BY ce.anyo, ce.codigoMes, ce.periodo")
	public List<DashboardDTO> findEmissionStatisticsSummaryNoGroupSortPeriods(@Param("anyoFrom") Integer anyoFrom, @Param("anyoTo") Integer anyoTo, @Param("mesFrom") Integer mesFrom, @Param("mesTo") Integer mesTo, Sort sort, 
			@Param("periodos")List<String> periodos);
}
