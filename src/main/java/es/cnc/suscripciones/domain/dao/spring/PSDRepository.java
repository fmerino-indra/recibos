package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.PSD;
import es.cnc.suscripciones.domain.Suscripcion;

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
	
	@Query("SELECT DISTINCT psd"
			+ " FROM PSD psd"
			+ " JOIN FETCH psd.idDomiciliacion d"
			+ " JOIN FETCH d.sucursalId s"
			+ " JOIN FETCH s.idBanco b"
			+ " WHERE psd.idSuscripcion = :s")
	public List<PSD> findBySuscripcion(@Param("s") Suscripcion s);
}
