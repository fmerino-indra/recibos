package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Domiciliacion;

public interface DomiciliacionRepository extends JpaRepository<Domiciliacion, Integer> {
	@Query("SELECT d "
			+ " FROM Domiciliacion d"
			+ " WHERE d.iban = :iban")
	public Domiciliacion findDomiciliacionByIban(@Param("iban") String iban);
	
}
