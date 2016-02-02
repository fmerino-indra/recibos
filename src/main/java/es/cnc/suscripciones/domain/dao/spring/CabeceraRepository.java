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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.Cabeceraemisiones;

@Repository
public interface CabeceraRepository extends JpaRepository<Cabeceraemisiones, Integer> {

	@Query("select distinct ce from Cabeceraemisiones ce JOIN FETCH ce.emisions"
			+ " where ce.fechaEmision = ?1 order by ce.fechaEmision desc")
	public List<Cabeceraemisiones> findCabeceraAndEmisionesByFecha(Date fecha);

	// TODO Hacer paginable
	@Query("select ce from Cabeceraemisiones ce order by ce.fechaEmision desc")
	public List<Cabeceraemisiones> findCabecerasDesc();
	
	@Query("select distinct ce from Cabeceraemisiones ce LEFT JOIN FETCH ce.emisions em"
			+ " INNER JOIN FETCH ce.parroquiaHasParroco"
			+ " INNER JOIN FETCH em.idSuscripcion psd"
			+ " INNER JOIN FETCH psd.idDomiciliacion d"
			+ " INNER JOIN FETCH psd.idSuscripcion s"
			+ " INNER JOIN FETCH s.persona"
			+ " where ce = ?1 ")
	public Cabeceraemisiones findEmisionesByCabecera(Cabeceraemisiones cabecera);
}