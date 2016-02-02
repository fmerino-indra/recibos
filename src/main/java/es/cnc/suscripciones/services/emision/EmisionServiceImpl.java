package es.cnc.suscripciones.services.emision;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.domain.Meses;
import es.cnc.suscripciones.domain.PSD;
import es.cnc.suscripciones.domain.Periodo;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.domain.dao.spring.EmisionRepository;
import es.cnc.suscripciones.domain.dao.spring.PSDRepository;
import es.cnc.suscripciones.domain.dao.spring.ParroquiaHasParrocoRepository;
import es.cnc.suscripciones.domain.dao.spring.PeriodoRepository;
import es.cnc.util.ConstantsCNC;
import es.cnc.util.LocalDateUtil;

@Component("emisionService")
public class EmisionServiceImpl implements EmisionService {

	@Autowired
	PeriodoRepository periodoRepository;

	@Autowired
	CabeceraRepository cabeceraRepository;
	
	@Autowired
	ParroquiaHasParrocoRepository phpRepository;

	@Autowired
	PSDRepository psdRepository;

	@Autowired
	EmisionRepository emisionRepository;
	
	public EmisionServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see es.cnc.suscripciones.services.emision.EmisionService#findMascara()
	 */
	@Override
	public List<Periodo> findMascara() {
		LocalDate ld = LocalDate.now();
		int month = ld.getMonthValue();
		return findMascara(month);
	}

	/**
	 * Returns a list of Periods (Annual, Six-monthly, T Quarter, Monthly) in
	 * function of the month passed as parameter
	 * 
	 * @param monthId
	 *            The month code (January -1, February - 2, etc.).
	 * @return The List of Periods configured for this month.
	 */
	public List<Periodo> findMascara(int monthId) {
		Meses mes = null;
		mes = new Meses();
		mes.setId(monthId);
		return periodoRepository.findPeriodosByMes(mes);
	}

	private Cabeceraemisiones generateDirectDebitHeader(Periodo p, LocalDateTime today, LocalDateTime fEnvio) {
		Cabeceraemisiones ce = new Cabeceraemisiones();
		ce.setCodigoMes(today.getMonthValue());
		ce.setEmisionManual(false);
		ce.setConcepto(ConstantsCNC.CONCEPTO);
		ce.setFechaEmision(LocalDateUtil.localDateTimeToDate(today));
		ce.setFechaEnvio(LocalDateUtil.localDateTimeToDate(fEnvio));
		ce.setParroquiaHasParroco(phpRepository.findActiveParroquiaHasParroco());
		ce.setPeriodo(p.getId());
		// TODO Where saving?
		cabeceraRepository.save(ce);
		return ce;
	}
	
	private List<Cabeceraemisiones> generateDirectDebitHeaders(int month) {
		List<Periodo> periodList = findMascara(month);
		return generateDirectDebitHeaders(periodList);
	}
	
	private List<Cabeceraemisiones> generateDirectDebitHeaders(List<Periodo> periodList) {
		List<Cabeceraemisiones> lista = null;
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime fEnvio = today.plusDays(4);
		lista = new ArrayList<>(periodList.size());
		for (Periodo p:periodList) {
			lista.add(generateDirectDebitHeader(p, today, fEnvio));
		}
		return lista;
	}
	
	private Set<Emision> generateDirectDebits(Cabeceraemisiones ce) {
		List<PSD> psdList = psdRepository.findByPeriod(ce.getPeriodo());
		Emision emision;
		Set<Emision> emisiones = new HashSet<>(psdList.size());
		for (PSD psd:psdList) {
			emision = generateDirectDebit(psd, ce);
			emisiones.add(emision);
		}
		return emisiones;
	}
	
	/**
	 * Generate and save Emision, ce must be saved.
	 * @param psd
	 * @param ce
	 * @return Generated Emision.
	 */
	private Emision generateDirectDebit(PSD psd, Cabeceraemisiones ce) {
		Emision em = null;
		em = new Emision();
		em.setDevuelto(false);
		em.setDivisa(psd.getIdSuscripcion().getDivisa());
		em.setIdCabecera(ce);
		em.setIdPersonaAntigua(psd.getIdPantiguo());
		em.setIdSuscripcion(psd);
		em.setIdSuscripcionAntigua(psd.getIdSantiguo());
		em.setImporte(psd.getIdSuscripcion().getEuros());
		em.setPrimero(psd.getIdSuscripcion().getSecuenciaAdeudo().equals(ConstantsCNC.FRST));
		em.setReenviado(false);
		em.setUltimo(psd.getIdSuscripcion().getSecuenciaAdeudo().equals(ConstantsCNC.FNAL));
		emisionRepository.save(em);
		return em;
	}
	
	@Override
	public List<Cabeceraemisiones> generate() {
		LocalDate today = LocalDate.now();
		return generate(today.getMonthValue());
	}
	
	/* (non-Javadoc)
	 * @see es.cnc.suscripciones.services.emision.EmisionService#generate()
	 */
	@Override
	public List<Cabeceraemisiones> generate(int mes) {
		List<Cabeceraemisiones> lista;
		lista = generateDirectDebitHeaders(mes);
		for (Cabeceraemisiones ce: lista) {
			ce.setEmisions(generateDirectDebits(ce));
		}
		return lista;
	}
	
	/* (non-Javadoc)
	 * @see es.cnc.suscripciones.services.emision.EmisionService#deleteCabecera(es.cnc.suscripciones.domain.Cabeceraemisiones)
	 */
	@Override
	public void deleteCabecera(Cabeceraemisiones ce) {
		ce = cabeceraRepository.findEmisionesByCabecera(ce);
		for (Emision e:ce.getEmisions()) {
			emisionRepository.delete(e);
		}
		cabeceraRepository.delete(ce);
	}
}
