package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	public Page<Suscripcion> findSuscripcionesActivas(Pageable pageable);
	
	@Query("SELECT s FROM Suscripcion s"
			+ " INNER JOIN FETCH s.persona p"
			+ " LEFT JOIN FETCH s.pSDs"
			+ " WHERE s.id = :id")
	public Suscripcion findSuscripcionById(@Param("id") Integer id);
	
}
