package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Reason;

public interface ReasonRepository extends JpaRepository<Reason, Integer> {
	@Query ("SELECT r FROM Reason r"
			+ " WHERE r.reasonCode = :rcode")
	public Reason findByReasonCode(@Param("rcode")String rcode);
}
