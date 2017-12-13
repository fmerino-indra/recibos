/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.cnc.suscripciones.domain.dao.spring;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Cabeceraemisiones;

@Repository
public interface CabeceraRepository extends JpaRepository<Cabeceraemisiones, Integer> {

	@Query("select distinct ce from Cabeceraemisiones ce JOIN FETCH ce.emisions"
			+ " where ce.fechaEmision = ?1 order by ce.fechaEmision desc")
	public List<Cabeceraemisiones> findCabeceraAndEmisionesByFecha(Date fecha);

	@Query("select ce from Cabeceraemisiones ce order by ce.fechaEmision desc")
	public List<Cabeceraemisiones> findCabecerasDesc();
	
	@Query("select ce from Cabeceraemisiones ce "
			+ "where ce.anyo = :anyo "
			+ "order by ce.codigoMes asc")
	public List<Cabeceraemisiones> findCabecerasByYear(@Param("anyo")Integer anyo);
	
	@Query("select ce from Cabeceraemisiones ce order by ce.fechaEmision desc")
	public Page<Cabeceraemisiones> findCabecerasDesc(Pageable pageable);
	
	@Query("select distinct ce from Cabeceraemisiones ce LEFT JOIN FETCH ce.emisions em"
			+ " INNER JOIN FETCH ce.parroquiaHasParroco"
			+ " INNER JOIN FETCH em.idSuscripcion psd"
			+ " INNER JOIN FETCH psd.idDomiciliacion d"
			+ " INNER JOIN FETCH psd.idSuscripcion s"
			+ " INNER JOIN FETCH s.persona"
			+ " INNER JOIN FETCH ce.domiciliacion domPar"
			+ " where ce = ?1 ")
	public Cabeceraemisiones findCabeceraByIdFull(Cabeceraemisiones cabecera);
	
	@Query("select distinct ce from Cabeceraemisiones ce "
			+ " LEFT JOIN FETCH ce.emisions em"
			+ " INNER JOIN FETCH em.idSuscripcion psd"
			+ " INNER JOIN FETCH psd.idDomiciliacion d"
			+ " INNER JOIN FETCH psd.idSuscripcion s"
			+ " INNER JOIN FETCH s.persona"
			+ " where ce = ?1 "
			+ " ORDER BY em.id")
	@Deprecated // This is a heavy query
	public Cabeceraemisiones findCabeceraByIdWithEmisiones(Cabeceraemisiones cabecera);

	@Query("select distinct ce from Cabeceraemisiones ce "
			+ " where ce = ?1 ")
	public Cabeceraemisiones findCabeceraByIdWithoutEmisiones(Cabeceraemisiones cabecera);
	
	@Query("select distinct ce from Cabeceraemisiones ce "
			+ " inner join fetch ce.emisions em "
			+ " INNER JOIN FETCH em.idSuscripcion psd"
			+ " INNER JOIN FETCH psd.idSuscripcion s"
			+ " INNER JOIN FETCH s.persona"
			+ " WHERE em.devuelto = TRUE"
			+ " AND ce.fechaEmision >= :from"
			+ " AND ce.fechaEmision <= :to")
	public List<Cabeceraemisiones> findRefundedCabeceraBetweenDatesFull(@Param("from") Date from, @Param("to") Date to);

	@Query("SELECT ce FROM Cabeceraemisiones ce "
			+ " WHERE ce.anyo = :anyo "
			+ " AND ce.codigoMes = :mes")
	public List<Cabeceraemisiones> findCabeceraByYearMonth(@Param("anyo")Integer year, @Param("mes")Integer codigoMes);

	@Query("select distinct ce from Cabeceraemisiones ce "
			+ " LEFT JOIN FETCH ce.emisions em"
			+ " INNER JOIN FETCH ce.parroquiaHasParroco"
			+ " INNER JOIN FETCH em.idSuscripcion psd"
			+ " INNER JOIN FETCH psd.idDomiciliacion d"
			+ " INNER JOIN FETCH psd.idSuscripcion s"
			+ " INNER JOIN FETCH s.persona"
			+ " INNER JOIN FETCH ce.domiciliacion domPar"
			+ " LEFT JOIN FETCH ce.sepaCoreXMLs x"
			+ " where x.idMsg = :msgId "
			+ " AND x.activo = TRUE")
	public Cabeceraemisiones findCabeceraByMsgIdFull(@Param("msgId") String msgId);
	
	
}
