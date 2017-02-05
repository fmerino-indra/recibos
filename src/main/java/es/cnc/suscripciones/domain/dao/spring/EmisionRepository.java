package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Emision;

public interface EmisionRepository extends JpaRepository<Emision, Integer> {

	@Query("select e.idCabecera, sum(e.importe) from Emision e")
	public Page<Object[]> sumImporteByCabecera(Pageable pageable);
	
	@Query("SELECT e FROM Emision e "
			+ " INNER JOIN FETCH e.idSuscripcion psd"
			+ " INNER JOIN FETCH psd.idDomiciliacion d"
			+ " INNER JOIN FETCH psd.idSuscripcion s"
			+ " INNER JOIN FETCH s.persona"
			+ " WHERE e.idCabecera = :cabecera"
			+ " ORDER BY s.persona.nombre")
	public List<Emision> findEmissionsByCabecera(@Param("cabecera") Cabeceraemisiones ce);

	@Query("SELECT e FROM Emision e "
			+ " INNER JOIN FETCH e.idSuscripcion psd"
			+ " INNER JOIN FETCH psd.idDomiciliacion d"
			+ " INNER JOIN FETCH psd.idSuscripcion s"
			+ " INNER JOIN FETCH s.persona"
			+ " WHERE e.idCabecera = :cabecera"
			+ " AND e.devuelto = true")
	public List<Emision> findRefundedEmissionsByCabecera(@Param("cabecera") Cabeceraemisiones ce);

}
