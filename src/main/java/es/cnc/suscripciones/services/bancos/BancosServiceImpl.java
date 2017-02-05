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

package es.cnc.suscripciones.services.bancos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import es.cnc.suscripciones.domain.Bancos;
import es.cnc.suscripciones.domain.dao.spring.BancosRepository;

@Component("bancosService")
@Transactional
class BancosServiceImpl implements BancosService {

	private final BancosRepository bancosRepository;

//	@Autowired
//	private SimpleJpaRepository<Bancos, Integer> jpaRepository;
	
	@Autowired
	public BancosServiceImpl(BancosRepository bancosRepository) {
		this.bancosRepository = bancosRepository;
	}

	@SuppressWarnings("unused")
	@Override
	public Page<Bancos> findBancos(BancosSearchCriteria criteria, Pageable pageable) {

//		Assert.notNull(criteria, "Criteria must not be null");
		String name = null;
//		name = criteria.getName();

		if (!StringUtils.hasLength(name)) {
			return this.bancosRepository.findAll(null);
		}

		String country = "";
		@SuppressWarnings("null")
		int splitPos = name.lastIndexOf(",");

		if (splitPos >= 0) {
			country = name.substring(splitPos + 1);
			name = name.substring(0, splitPos);
		}

//		return this.bancosRepository
//				.findByNameContainingAndCountryContainingAllIgnoringCase(name.trim(),
//						country.trim(), pageable);
		return this.bancosRepository.findAll(pageable);
	}

	@Override
	public List<Bancos> findBancos(String name, String country) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Bancos> findBancos(String codBco, String denBco) {
//		return jpaRepository.findAll();
//	}

}
