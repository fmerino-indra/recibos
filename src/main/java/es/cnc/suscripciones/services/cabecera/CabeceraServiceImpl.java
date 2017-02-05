package es.cnc.suscripciones.services.cabecera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.domain.dao.spring.EmisionRepository;

@Component("cabeceraService")
public class CabeceraServiceImpl implements CabeceraService {

	@Autowired
	CabeceraRepository cabeceraRepository;
	
	@Autowired
	EmisionRepository emisionRepository;
	
	@Autowired
	EntityManager entityManager;
	
	public CabeceraServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Cabeceraemisiones> findCabecerasDesc() {
		return cabeceraRepository.findCabecerasDesc();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Cabeceraemisiones> findCabecerasDesc(Integer page, Integer start, Integer limit) {
		PageRequest pr = new PageRequest(page, limit);
		Page<Cabeceraemisiones> response = cabeceraRepository.findCabecerasDesc(pr);
		List<Integer> ids = null;
		List<Object[]> resultSum = null;
		Query query = null;
		Map<Integer, Double> sumas = null;
		Map<Integer, Double> devoluciones = null;
		Map<Integer, Long> devueltos = null;
		if (response != null) {
			ids = new ArrayList<>();
			query = entityManager.createQuery("SELECT e.idCabecera.id, SUM(e.importe),"
					+ "SUM( CASE WHEN (e.devuelto = 1) THEN e.importe ELSE 0.0 END) AS importeDevuelto, "
					+ "SUM( CASE WHEN (e.devuelto = 1) THEN 1 ELSE 0 END) AS devueltos "
					+ " FROM Emision e "
					+ " WHERE e.idCabecera.id IN :ids"
					+ " group by e.idCabecera");
			for (Cabeceraemisiones ce : response) {
				ids.add(ce.getId());
			}
			query.setParameter("ids", ids);
			resultSum = query.getResultList();
			sumas = new HashMap<>(resultSum.size());
			devoluciones = new HashMap<>(resultSum.size());
			devueltos = new HashMap<>(resultSum.size());
			for (Object[] fila: resultSum) {
				sumas.put((Integer)fila[0], (Double)fila[1]);
				devoluciones.put((Integer)fila[0], (Double)fila[2]);
				devueltos.put((Integer)fila[0], (Long)fila[3]);
			}
			for (Cabeceraemisiones ce : response) {
				ce.setImporte(sumas.get(ce.getId()));
				ce.setImporteDevuelto(devoluciones.get(ce.getId()));
				ce.setDevueltos(devueltos.get(ce.getId()));
				
			}
		}
		return response; 
	}
	
	public void find () {
		
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Emision> cq = cb.createQuery(Emision.class);
	    Root<Emision> emisionRoot = cq.from(Emision.class);
//	    Expression<Boolean> isDevuelto = null; 
	    Expression<Double> impDevuelto;
	    TypedQuery<Emision> query = null; 

	    impDevuelto = cb.<Double>selectCase().when(cb.equal(emisionRoot.get("Devuelto"), Boolean.TRUE), emisionRoot.get("Importe"));
	    cb.sum(impDevuelto).alias("importeDevuelto");
	    cb.sum(emisionRoot.get("Importe")).alias("Importe");
	    
	    cq.select(emisionRoot);
	    cq.orderBy(cb.desc(emisionRoot.get("idCabecera")),cb.desc(emisionRoot.get("id")));
	    cq.groupBy(emisionRoot.get("idCabecera"));

	    query = entityManager.createQuery(cq);
	    query.getResultList();
		
	}

	@Override
	@Deprecated // This is a heavy query
	public Cabeceraemisiones findCabeceraDetail(Cabeceraemisiones ce) {
		return cabeceraRepository.findCabeceraByIdWithEmisiones(ce);
	}

	@Override
	public Cabeceraemisiones findCabeceraDetailSimple(Cabeceraemisiones ce) {
		return cabeceraRepository.findCabeceraByIdWithoutEmisiones(ce);
	}

	@Override
	public List<Emision> findEmissionList(Cabeceraemisiones ce) {
		return emisionRepository.findEmissionsByCabecera(ce);
	}

	@Override
	public List<Emision> findRefundedEmissionList(Cabeceraemisiones ce) {
		return emisionRepository.findRefundedEmissionsByCabecera(ce);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cabeceraemisiones> findCabecerasByYear(Integer anyo) {
		List<Cabeceraemisiones> response = cabeceraRepository.findCabecerasByYear(anyo);
		List<Integer> ids = null;
		List<Object[]> resultSum = null;
		Query query = null;
		Map<Integer, Double> sumas = null;
		Map<Integer, Double> devoluciones = null;
		Map<Integer, Long> devueltos = null;
		if (response != null && response.size() > 0) {
			ids = new ArrayList<>();
			query = entityManager.createQuery("SELECT e.idCabecera.id, SUM(e.importe),"
					+ "SUM( CASE WHEN (e.devuelto = 1) THEN e.importe ELSE 0.0 END) AS importeDevuelto, "
					+ "SUM( CASE WHEN (e.devuelto = 1) THEN 1 ELSE 0 END) AS devueltos "
					+ " FROM Emision e "
					+ " WHERE e.idCabecera.id IN :ids"
					+ " group by e.idCabecera");
			for (Cabeceraemisiones ce : response) {
				ids.add(ce.getId());
			}
			query.setParameter("ids", ids);
			resultSum = query.getResultList();
			sumas = new HashMap<>(resultSum.size());
			devoluciones = new HashMap<>(resultSum.size());
			devueltos = new HashMap<>(resultSum.size());
			for (Object[] fila: resultSum) {
				sumas.put((Integer)fila[0], (Double)fila[1]);
				devoluciones.put((Integer)fila[0], (Double)fila[2]);
				devueltos.put((Integer)fila[0], (Long)fila[3]);
			}
			for (Cabeceraemisiones ce : response) {
				ce.setImporte(sumas.get(ce.getId()));
				ce.setImporteDevuelto(devoluciones.get(ce.getId()));
				ce.setDevueltos(devueltos.get(ce.getId()));
				
			}
		}
		return response; 
	}
}
