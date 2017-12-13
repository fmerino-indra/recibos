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

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.SepaCoreXml;
@org.springframework.stereotype.Repository
public interface SepaCoreXmlRepository extends JpaRepository<SepaCoreXml, Integer> {
	@Query("Select x From SepaCoreXml x"
			+ " INNER JOIN FETCH x.idCabecera")
	public List<SepaCoreXml> findXmlWithCabecera();
	
	@Query("SELECT x FROM SepaCoreXml x"
			+ " INNER JOIN FETCH x.idCabecera c"
			+ " WHERE c = :cabecera "
			+ " AND "
			+ " x.activo = TRUE")
	public List<SepaCoreXml> findXmlByCabecera(@Param("cabecera") Cabeceraemisiones ce);
}
