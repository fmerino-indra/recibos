package es.cnc.suscripciones.domain.dao.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Donativo;
import es.cnc.suscripciones.front.dto.CertificadoDTO;

public interface DonativoRepository extends JpaRepository<Donativo, Integer> {
	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.CertificadoDTO(year(d.fechaDonativo) as anyo, p.nombre, sum(d.importe), count(d.id), p.nif, p.id ) "
			+ " FROM Donativo d"
			+ " INNER JOIN d.idPersona p"
			+ " WHERE p.nif = :nif"
			+ " and year(d.fechaDonativo) = :year"
			+ " GROUP BY year(d.fechaDonativo), p.nif"	
			+ " ORDER BY (year(d.fechaDonativo)) DESC, p.nombre ASC")
	public CertificadoDTO findDonativoSummaryByNifAndYearDTO(@Param("nif") String nif, @Param("year") Integer year);

	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.CertificadoDTO(year(d.fechaDonativo) as anyo, p.nombre, sum(d.importe), count(d.id), p.nif, p.id ) "
			+ " FROM Donativo d"
			+ " INNER JOIN d.idPersona p"
			+ " WHERE p.nif = :nif"
			+ " GROUP BY year(d.fechaDonativo), p.nif"	
			+ " ORDER BY (year(d.fechaDonativo)) DESC")
	public List<CertificadoDTO> findDonativoSummaryByNifDTO(@Param("nif") String nif);

	@Query(value = "SELECT new es.cnc.suscripciones.front.dto.CertificadoDTO(year(d.fechaDonativo) as anyo, p.nombre, sum(d.importe), count(d.id), p.nif, p.id ) "
			+ " FROM Donativo d"
			+ " INNER JOIN d.idPersona p"
			+ " WHERE year(d.fechaDonativo) = :year"
			+ " GROUP BY year(d.fechaDonativo), p.nif"	
			+ " ORDER BY (year(d.fechaDonativo)) DESC")
	public List<CertificadoDTO> findDonativoSummaryByYearDTO(@Param("year") Integer year);
}
