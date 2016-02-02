package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.cnc.suscripciones.domain.PSD;

public interface PSDRepository extends JpaRepository<PSD, Integer> {
	
	@Query("SELECT DISTINCT psd FROM PSD psd"
			+ " JOIN FETCH psd.idSuscripcion s "
			+ " JOIN FETCH psd.idDomiciliacion d "
			+ " JOIN FETCH s.persona p"
			+ " JOIN FETCH s.secuenciaAdeudo sa"
			+ " WHERE psd.fechaBaja is null"
			+ " and s.activo = TRUE"
			+ " and s.periodo = ?1")
	public List<PSD> findByPeriod(String period);
}
