package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.Suscripcion;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Integer> {
	
	@Query(value="SELECT s FROM Suscripcion s"
				+ " INNER JOIN FETCH s.persona p "
				+ " WHERE ( :name is null or p.nombre like CONCAT(:name,'%') )"
				+ " and s.fechaBaja is null"
				+ " and s.activo = TRUE"
				+ " ORDER BY p.nombre",
			countQuery = "SELECT COUNT(s) FROM Suscripcion s"
				+ " INNER JOIN s.persona p "
				+ " WHERE ( :name is null or p.nombre like CONCAT(:name,'%') )"
				+ " and s.fechaBaja is null"
				+ " and s.activo = TRUE")
	public Page<Suscripcion> findActiveSuscripciones(Pageable pageable, @Param("name") String name);
	
	@Query(value="SELECT s FROM Suscripcion s"
			+ " INNER JOIN FETCH s.persona p "
			+ " WHERE ( :name is null or p.nombre like CONCAT(:name,'%') )"
			+ " and s.fechaBaja is not null"
			+ " and s.activo = FALSE"
			+ " ORDER BY p.nombre",
		countQuery = "SELECT COUNT(s) FROM Suscripcion s"
			+ " INNER JOIN s.persona p "
			+ " WHERE ( :name is null or p.nombre like CONCAT(:name,'%') )"
			+ " and s.fechaBaja is not null"
			+ " and s.activo = FALSE")
	public Page<Suscripcion> findInactiveSuscripciones(Pageable pageable, @Param("name") String name);

	@Query(value="SELECT s FROM Suscripcion s"
			+ " INNER JOIN FETCH s.persona p "
			+ " WHERE :name is null or p.nombre like CONCAT('%',:name,'%') "
			+ " ORDER BY p.nombre",
		countQuery = "SELECT COUNT(s) FROM Suscripcion s"
			+ " INNER JOIN s.persona p "
			+ " WHERE :name is null or p.nombre like CONCAT('%',:name,'%')")
	public Page<Suscripcion> findActiveInactiveSuscripciones(Pageable pageable, @Param("name") String name);

	@Query(value="SELECT s FROM Suscripcion s"
			+ " INNER JOIN FETCH s.persona p "
			+ " WHERE p.id = :idPersona "
			+ " ORDER BY s.fechaInicio desc",
		countQuery = "SELECT COUNT(s) FROM Suscripcion s"
			+ " INNER JOIN s.persona p "
			+ " WHERE p.id = :idPersona ")
	public List<Suscripcion> findAllById(@Param("idPersona") Integer idPersona);

	/**
	 * Return the Suscripcion with PSD, Domiciliacion and Persona
	 * @param id
	 * @return
	 */
	@Query("SELECT s FROM Suscripcion s"
			+ " INNER JOIN FETCH s.persona p"
			+ " LEFT JOIN FETCH s.pSDs psd"
			+ " LEFT JOIN FETCH psd.idDomiciliacion"
			+ " WHERE s.id = :id")
	public Suscripcion findSuscripcionById(@Param("id") Integer id);
	
	@Query("SELECT s FROM Suscripcion s"
			+ " INNER JOIN FETCH s.persona p"
			+ " WHERE p = :per"
			+ " AND s.activo = TRUE")
	public Suscripcion findActiveSuscripcionByPersona(@Param("per") Persona p);
	
	@Query(value = "SELECT year(ce.fechaEmision), p.nombre, sum(e.importe), count(e) "
			+ "FROM Emision e"
			+ " INNER JOIN e.idSuscripcion psd"
			+ " INNER JOIN psd.idSuscripcion s"
			+ " INNER JOIN s.persona p"
			+ " INNER JOIN e.idCabecera ce"
			+ " WHERE p.id = :idPersona"
			+ " GROUP BY (YEAR(ce.fechaEmision))")
	public List<Object[]> findEmissionSummaryByPerson(@Param("idPersona") Integer id);
}
