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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.cnc.suscripciones.domain.Bancos;
@org.springframework.stereotype.Repository
public interface BancosRepository extends JpaRepository<Bancos, Long> {

	Page<Bancos> findAll(Pageable pageable);

	List<Bancos> findBancosByCodBco(String codBco);
	
	Bancos findBancosByCodBcoAndActivo(String codBco, Boolean active);
//	Page<Bancos> findByNameContainingAndCountryContainingAllIgnoringCase(String name,
//			String country, Pageable pageable);
//
//	Bancos findByNameAndCountryAllIgnoringCase(String name, String country);

}
