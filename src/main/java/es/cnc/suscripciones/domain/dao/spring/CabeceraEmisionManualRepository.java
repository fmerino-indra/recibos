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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.cnc.suscripciones.domain.CabeceraEmisionManual;

@Repository
public interface CabeceraEmisionManualRepository extends JpaRepository<CabeceraEmisionManual, Integer> {
	@Query("select distinct cem from CabeceraEmisionManual cem "
			+ " INNER JOIN FETCH cem.solicitud s"
			+ " LEFT JOIN FETCH cem.emisionManuals em "
			+ " LEFT JOIN FETCH em.detalleSolicitudEmision det"
			+ " INNER JOIN FETCH cem.parroquiaHasParroco php "
			+ " INNER JOIN FETCH php.parrocoId p "
			+ " INNER JOIN FETCH php.parroquiaId par "
			+ " INNER JOIN FETCH par.parroquiaAux pAux "
			+ " INNER JOIN FETCH pAux.pais pais "
			+ " LEFT JOIN FETCH em.domiciliacion d"
			+ " LEFT JOIN FETCH d.idPersona"
			+ " INNER JOIN FETCH cem.domiciliacion domPar"
			+ " where cem = :idCabecera "
			+ " AND pAux.activo = :activo")
	public CabeceraEmisionManual findCabeceraManualByIdFull(@Param("idCabecera")CabeceraEmisionManual cabecera, @Param("activo") Boolean activo);

}
