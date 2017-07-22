package es.cnc.suscripciones.domain.dao.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
	
	/**
	 * Return the Sucursal with codSuc
	 * @param codSuc: Código Sucursal.
	 * @return
	 */
	@Query("SELECT s FROM Sucursal s"
			+ " INNER JOIN FETCH s.idBanco b"
			+ " WHERE b.codBco = :codBco"
			+ " AND b.activo = TRUE"
			+ " AND s.codSuc = :codSuc")
	public Sucursal findSucursalByCodBanAndCodSuc(@Param("codBco") String codBco, @Param("codSuc") String codSuc);
	
}
