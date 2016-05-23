package es.cnc.suscripciones.domain.dao.spring;

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
				+ " WHERE s.fechaBaja is null"
				+ " and s.activo = TRUE"
				+ " ORDER BY p.nombre",
			countQuery = "SELECT COUNT(s) FROM Suscripcion s"
				+ " INNER JOIN s.persona p "
				+ " WHERE s.fechaBaja is null"
				+ " and s.activo = TRUE")
	public Page<Suscripcion> findActiveSuscripciones(Pageable pageable);
	
	@Query(value="SELECT s FROM Suscripcion s"
			+ " INNER JOIN FETCH s.persona p "
			+ " WHERE s.fechaBaja is not null"
			+ " and s.activo = FALSE"
			+ " ORDER BY p.nombre",
		countQuery = "SELECT COUNT(s) FROM Suscripcion s"
			+ " INNER JOIN s.persona p "
			+ " WHERE s.fechaBaja is not null"
			+ " and s.activo = FALSE")
public Page<Suscripcion> findInactiveSuscripciones(Pageable pageable);

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
	
}
