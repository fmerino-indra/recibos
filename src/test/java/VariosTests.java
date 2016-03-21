
/*
 * Copyright 2012-2014 the original author or authors.
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cnc.Application;
import es.cnc.suscripciones.domain.Bancos;
import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.domain.Meses;
import es.cnc.suscripciones.domain.ParroquiaHasParroco;
import es.cnc.suscripciones.domain.Periodo;
import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.domain.dao.spring.EmisionRepository;
import es.cnc.suscripciones.domain.dao.spring.ParroquiaHasParrocoRepository;
import es.cnc.suscripciones.domain.dao.spring.PeriodoRepository;
import es.cnc.suscripciones.domain.dao.spring.SuscripcionRepository;
import es.cnc.suscripciones.services.bancos.BancosService;
import es.cnc.suscripciones.services.cabecera.CabeceraService;
import es.cnc.suscripciones.services.generacion.GeneracionService;
import es.cnc.suscripciones.services.suscripcion.SuscripcionService;

/**
 * Integration test to run the application.
 *
 * @author Félix Merino Martínez de Pinillos
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class VariosTests {
	Logger logger;
//	@Autowired
//	private PeriodoDAO dao;
	
	@Autowired
	private BancosService bancosService;

	@Autowired
	private GeneracionService generacionService;

	@Autowired
	private CabeceraService cabeceraService;

	@Autowired
	private CabeceraRepository cabeceraRepository;
	
	@Autowired
	private EmisionRepository emisionRepository;

	@Autowired
	private ParroquiaHasParrocoRepository parroquiaHasParrocoRepository;
	
	@Autowired
	private PeriodoRepository periodoRepository;
	
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private SuscripcionRepository suscripcionRepository;

	@Autowired
	private SuscripcionService suscripcionService;
	
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

//	@Test
//	@Transactional
	public void testDao() throws Exception {
		List<Periodo> lista = null; 
//		lista = dao.findAll();
		assertNotNull(lista);
	}

//	@Test
//	@Transactional
	public void testBancos() throws Exception {
		Page<Bancos> lista = bancosService.findBancos(null, (Pageable)null); 
		assertNotNull(lista);
	}
	
//	@Test
	public void testCabeceraEmisiones() throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateInString = "20/01/2009";
		List<Cabeceraemisiones> lista = cabeceraRepository.findCabeceraAndEmisionesByFecha(formatter.parse(dateInString));
		assertNotNull(lista);
		assertTrue(lista.size()>0);
	}
	
	//@Test
	public void testMascara() throws Exception {
		List<Periodo> lista = periodoRepository.findPeriodosByMes(1);
		assertNotNull(lista);
		assertTrue(lista.size()>0);

		Meses mes = new Meses();
		mes.setId(1);
		lista = periodoRepository.findPeriodosByMes(mes);
		assertNotNull(lista);
		assertTrue(lista.size()>0);

		lista = generacionService.findMascara();
		assertNotNull(lista);
		assertTrue(lista.size()>0);
	}
	
//	@Test
	public void testCabecera() throws Exception {
		Cabeceraemisiones cabecera = null;
		cabecera = new Cabeceraemisiones();
		cabecera.setId(3785);
		
		cabecera = cabeceraRepository.findCabeceraByIdWithEmisiones(cabecera);
		assertNotNull(cabecera);
		
		ParroquiaHasParroco php = null;
		php = parroquiaHasParrocoRepository.findActiveParroquiaHasParroco();
		assertNotNull(php);
	}
	
//	@Test
	public void testGeneracion() throws Exception {
		Cabeceraemisiones cabecera = null;
		cabecera = new Cabeceraemisiones();
		cabecera.setId(3785);
		
		generacionService.generateISO20022(cabecera);
	}
//	@Test
	public void testCabPag() throws Exception {
		Page<Cabeceraemisiones> valor = cabeceraService.findCabecerasDesc(0, 0, 16);
		valor.toString();
	}
	
//	@Test
	public void testDevueltos()  throws Exception {
		Cabeceraemisiones cabecera = new Cabeceraemisiones();
		cabecera.setId(3785);
		
		List<Emision> lista = emisionRepository.findRefundedEmissionsByCabecera(cabecera);
		assertNotNull(lista);
		assertTrue(lista.size()>0);
		
	}
	
	@Test
	public void testSumCriteria() throws Exception {
		
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//	    CriteriaQuery<Emision> cq = cb.createQuery(Emision.class);
	    CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
	    Root<Emision> emisionRoot = cq.from(Emision.class);
//	    TypedQuery<Emision> query = null; 
	    TypedQuery<Object[]> query = null; 

//	    Expression<Double> impDevuelto;
//	    impDevuelto = cb.<Double>selectCase().when(cb.equal(emisionRoot.get("devuelto"), Boolean.TRUE), emisionRoot.get("importe"));
//	    cb.sum(impDevuelto).alias("importeDevuelto");
//	    cb.sum(emisionRoot.get("importe")).alias("importe");
	    
	    cq.multiselect(emisionRoot.get("idCabecera"), 
	    		cb.sum(emisionRoot.get("importe")).alias("importe"),
	    		cb.sum(cb.<Double>selectCase().when(
	    				cb.equal(emisionRoot.get("devuelto"), 1), emisionRoot.get("importe")
	    				).otherwise(0d)
	    				).alias("importeDevuelto"));
	    cq.orderBy(cb.desc(emisionRoot.get("idCabecera")));
	    cq.groupBy(emisionRoot.get("idCabecera"));

	    query = entityManager.createQuery(cq);
	    query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSumJPQL() {
		Query query = null;
		Map<Integer, Double> sumas = null;
		List<Object[]> resultSum = null;

		query = entityManager.createQuery("SELECT e.idCabecera.id, SUM(e.importe),"
				+ " SUM( CASE WHEN (e.devuelto = 1) THEN e.importe ELSE 0.0 END) AS importeDevuelto"
				+ " FROM Emision e "
//					+ " WHERE e.idCabecera.id IN :ids"
				+ " group by e.idCabecera"
				+ " ORDER BY e.idCabecera desc");
		resultSum = query.getResultList();
		sumas = new HashMap<>(resultSum.size());
		for (Object[] fila: resultSum) {
			sumas.put((Integer)fila[0], (Double)fila[1]);
			logger.debug(fila[0] + " " + fila [1] + " " + fila[2]);
		}
	}
	
	@Test
	public void testSuscripciones() {
		Page<Suscripcion> lista = null;
		Integer page = 0;
		Integer start = 0;
		Integer limit = 16;
		Suscripcion s = null;
		
		PageRequest pr = new PageRequest(page, limit);

		lista = suscripcionRepository.findSuscripcionesActivas(pr);
		assertNotNull(lista);
		assertTrue(lista.getSize() > 0);

		lista = suscripcionService.findSuscripcionesActivas(page, start, limit);
		assertNotNull(lista);
		assertTrue(lista.getSize() > 0);
		
		s=suscripcionService.findSuscripcionById(lista.getContent().get(0).getId());
		assertNotNull(s);
	}
}