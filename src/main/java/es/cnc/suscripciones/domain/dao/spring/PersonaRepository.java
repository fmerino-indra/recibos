package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
	@Query("select p from Persona p where p.nif = :nif")
	public List<Persona> findAllPersonasByNif(@Param("nif") String nif);

	@Query(value="select p from Persona p where p.nif = :nif", 
			countQuery = "select COUNT(p) from Persona p where p.nif = :nif")
	public Page<Persona> findAllPersonasByNif(@Param("nif") String nif, Pageable pageable);

	@Query(value="select p from Persona p where (:nif is null or :nif = '' or p.nif like concat(:nif ,'%') ) and ( :nombre is null or p.nombre like concat(:nombre ,'%') )" //, 
//			countQuery = "select COUNT(p) from Persona p where (:nif is null or :nif = '' or p.nif like :nif%)"
			)
	public Page<Persona> findAllPersonasLikeNif(@Param("nif") String nif, @Param("nombre") String nombre, Pageable pageable);
//	@Query(value="select p from Persona p where (:nif = '' or p.nif like :nif%) and (:nombre = '' or p.nombre like :nombre%)", 
//			countQuery = "select COUNT(p) from Persona p where (:nif = '' or p.nif like :nif%) and (:nombre = '' or p.nombre like :nombre%)")
//	public Page<Persona> findAllPersonasLikeNif(@Param("nif") String nif, @Param("nombre") String nombre, Pageable pageable);

	@Query("select distinct p from Persona p "
			+ " LEFT JOIN FETCH p.suscripcions s"
			+ " where p.id = ?1")
	@Deprecated
	public Persona findFullById(Integer id);
	
	@Query("SELECT p FROM Persona p"
			+ " WHERE p.id = :id")
	public Persona findByPrimaryKey(@Param("id") Integer id);


	@Query(value="SELECT DISTINCT p FROM Persona p"
			+ " LEFT JOIN FETCH p.suscripcions s"
			+ " LEFT JOIN FETCH s.pSDs psd"
			+ " LEFT JOIN FETCH psd.idDomiciliacion d"
			+ " LEFT JOIN FETCH d.sucursalId suc"
			+ " LEFT JOIN FETCH suc.idBanco b"
			+ " WHERE s.activo = TRUE and psd.fechaBaja is null"
			+ " ORDER BY p.nombre")
//		countQuery = "SELECT COUNT(p) FROM Persona p")
	public List<Persona> findPersonasWithActiveSuscripcionesDomiciliacionBancos();


}
