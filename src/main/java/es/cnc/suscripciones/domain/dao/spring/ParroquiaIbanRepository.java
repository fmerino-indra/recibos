package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.cnc.suscripciones.domain.ParroquiaIban;

public interface ParroquiaIbanRepository extends JpaRepository<ParroquiaIban, Integer> {
	@Query("SELECT DISTINCT pi FROM ParroquiaIban pi "
			+ " JOIN FETCH pi.domiciliacion d"
			+ " WHERE pi.activo = TRUE")
	public ParroquiaIban findActiveParroquiaIban();

}
