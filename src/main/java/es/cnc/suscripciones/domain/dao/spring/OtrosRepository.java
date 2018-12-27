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
	/**
	 * Returns a list of CertificadoDTO by person id
	 * It's used in the process of generate an online certificate and
	 * in the process of Mod182
	 * @param id
	 * @return
	 */
	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.CertificadoDTO(ce.anyo, p.nombre, sum(case when e.devuelto = 0 then e.importe else 0 end), count(e), p.nif, p.id) "
			+ "FROM Emision e"
			+ " INNER JOIN e.idSuscripcion psd"
			+ " INNER JOIN psd.idSuscripcion s"
			+ " INNER JOIN s.persona p"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE p.nif = :nif"
			+ " GROUP BY (ce.anyo)"
			+ " ORDER BY (ce.anyo) DESC")
	public List<CertificadoDTO> findEmissionSummaryByNifDTO(@Param("nif") String nif);

	/**
	 * This query is used in getCertificate method that generates the 
	 * certificate of a person on a year
	 * @param nif
	 * @param year
	 * @return
	 */
	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.CertificadoDTO(ce.anyo, p.nombre, sum(e.importe), count(e), p.nif, p.id) "
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

	/**
	 * This query is used in getCertificate method that generates the 
	 * certificate of a person on a year
	 * @param nif
	 * @param year
	 * @return
	 */
	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.CertificadoDTO(ce.anyo, p.nombre, sum(e.importe), count(e), p.nif, p.id) "
			+ "FROM Emision e"
			+ " INNER JOIN e.idSuscripcion psd"
			+ " INNER JOIN psd.idSuscripcion s"
			+ " INNER JOIN s.persona p"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE ce.anyo = :year"
			+ " GROUP BY p.nif"	
			+ " ORDER BY (p.nombre) ASC")
	public List<CertificadoDTO> findEmissionCertificatesByYearDTO(@Param("year") Integer year);

	/**
	 * Returns a Person list of people with emissions. It was used in generateCertificates (by year)
	 * @param year
	 * @return
	 * @deprecated
	 */
	@Deprecated
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
