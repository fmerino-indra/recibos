package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.cnc.suscripciones.domain.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
	@Query("select p from Persona p where p.nif = ?1")
	public List<Persona> findPersonaByNif(String nif);

	@Query("select distinct p from Persona p "
			+ " INNER JOIN FETCH p.suscripcions s"
			+ " where p.id = ?1")
	public Persona findFullById(Integer id);
}
