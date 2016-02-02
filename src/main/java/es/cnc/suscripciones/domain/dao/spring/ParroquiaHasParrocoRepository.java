package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.cnc.suscripciones.domain.ParroquiaHasParroco;

public interface ParroquiaHasParrocoRepository extends JpaRepository<ParroquiaHasParroco, Integer> {
//	@Query("SELECT DISTINCT php FROM ParroquiaHasParroco php JOIN FETCH php.parrocoId pco"
//			+ " JOIN FETCH php.parroquiaId p"
//			+ " JOIN FETCH p.parroquiaAuxes pa"
//			+ " JOIN FETCH pa.pais"
//			+ " WHERE php.activo = TRUE")
	@Query("SELECT DISTINCT php FROM ParroquiaHasParroco php "
			+ " WHERE php.activo = TRUE")
	public ParroquiaHasParroco findActiveParroquiaHasParroco();

}
