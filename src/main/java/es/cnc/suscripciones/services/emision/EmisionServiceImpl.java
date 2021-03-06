package es.cnc.suscripciones.services.emision;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.domain.Meses;
import es.cnc.suscripciones.domain.PSD;
import es.cnc.suscripciones.domain.Periodo;
import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.suscripciones.domain.dao.spring.CabeceraRepository;
import es.cnc.suscripciones.domain.dao.spring.DevolucionRepository;
import es.cnc.suscripciones.domain.dao.spring.EmisionRepository;
import es.cnc.suscripciones.domain.dao.spring.PSDRepository;
import es.cnc.suscripciones.domain.dao.spring.ParroquiaHasParrocoRepository;
import es.cnc.suscripciones.domain.dao.spring.ParroquiaIbanRepository;
import es.cnc.suscripciones.domain.dao.spring.PeriodoRepository;
import es.cnc.suscripciones.domain.dao.spring.ReasonRepository;
import es.cnc.suscripciones.domain.dao.spring.SuscripcionRepository;
import es.cnc.util.LocalDateUtil;
import es.cnc.util.sepa.ConstantsCNC;

@Component("emisionService")
public class EmisionServiceImpl implements EmisionService {

	@Autowired
	PeriodoRepository periodoRepository;

	@Autowired
	CabeceraRepository cabeceraRepository;

	@Autowired
	ParroquiaHasParrocoRepository phpRepository;

	@Autowired
	ParroquiaIbanRepository ibanRepository;

	@Autowired
	PSDRepository psdRepository;

	@Autowired
	EmisionRepository emisionRepository;

	@Autowired
	DevolucionRepository devolucionRepository;

	@Autowired
	SuscripcionRepository suscripcionRepository;

	@Autowired
	ReasonRepository reasonRepository;

	public EmisionServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
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

	@Override
	@Transactional
	public List<Cabeceraemisiones> generate() {
		LocalDate today = LocalDate.now();
		return generate(today.getMonthValue(), today.getYear());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.cnc.suscripciones.services.emision.EmisionService#generate()
	 */
	@Override
	@Transactional
	public List<Cabeceraemisiones> generate(int year, int month) {
		List<Cabeceraemisiones> lista = null;
		lista = generateDirectDebtHeaders(year, month);
		for (Cabeceraemisiones ce : lista) {
			ce.setEmisions(generateDirectDebts(ce));
		}
		return lista;
	}

	/**
	 * Regular or normal group of generating methods.
	 */

	/**
	 * Generate a list of Cabeceraemisiones for the month passed as parameter.
	 * Search de period pattern for the month passed as parameter.
	 * 
	 * @param month
	 * @return
	 */
	private List<Cabeceraemisiones> generateDirectDebtHeaders(int year, int month) {
		List<Periodo> periodList = findMascara(month);
		return generateDirectDebtHeaders(periodList, year, month);
	}

	/**
	 * Generate a list of Cabeceraemisiones for the period pattern list passed
	 * as parameter and for the current date like issue date and payment date
	 * (issue date + 4)
	 * 
	 * @param periodList
	 * @return
	 */
	private List<Cabeceraemisiones> generateDirectDebtHeaders(List<Periodo> periodList, Integer year, Integer month) {
		List<Cabeceraemisiones> lista = null;
		LocalDateTime today = null;
		LocalDateTime fEnvio = null;

		today = LocalDateTime.now();
		fEnvio = today.plusDays(4);
		
		lista = new ArrayList<>(periodList.size());
		for (Periodo p : periodList) {
			lista.add(generateDirectDebtHeader(p, today, fEnvio, year, month));
		}
		return lista;
	}

	/**
	 * Generate a Cabeceraemisiones for the period pattern passed as parameter.
	 * 
	 * @param p
	 *            -> Period pattern.
	 * @param today
	 *            -> Issue date.
	 * @param fEnvio
	 *            -> Payment date.
	 * @return
	 */
	private Cabeceraemisiones generateDirectDebtHeader(Periodo p, LocalDateTime today, LocalDateTime fEnvio, Integer year, Integer month) {
		Cabeceraemisiones ce = new Cabeceraemisiones();
		ce.setCodigoMes(month);
		ce.setAnyo(year);
		ce.setEmisionManual(false);
		String mesCorrespondiente = null;
		
		LocalDateTime fecha = LocalDateTime.now();
		fecha = fecha.withMonth(month);

		mesCorrespondiente = LocalDateUtil.obtainTextualMonth(fecha);
		
		ce.setConcepto(ConstantsCNC.CONCEPTO + "-" + mesCorrespondiente + "-" + p.getNombre());
		ce.setFechaEmision(LocalDateUtil.localDateTimeToDate(today));
		//TODO [FMM] Revisar si la fecha de envío está bien actualizada aquí o mejor en la generación del XML
//		ce.setFechaEnvio(LocalDateUtil.localDateTimeToDate(fEnvio));
		// FMM
		ce.setParroquiaHasParroco(phpRepository.findActiveParroquiaHasParroco());
		ce.setDomiciliacion(ibanRepository.findActiveParroquiaIban().getDomiciliacion());
		ce.setPeriodo(p.getId());
		cabeceraRepository.save(ce);
		return ce;
	}

	/**
	 * Generate a set of Emision (issue)
	 * 
	 * @param ce
	 * @return
	 */
	private List<Emision> generateDirectDebts(Cabeceraemisiones ce) {
		List<PSD> psdList = psdRepository.findByPeriod(ce.getPeriodo());
		Emision emision;
		List<Emision> emisiones = new ArrayList<>(psdList.size());
		for (PSD psd : psdList) {
			emision = generateDirectDebt(psd, ce);
			emisiones.add(emision);
		}
		return emisiones;
	}

	/**
	 * Generate and save a Emision (direct debt issue).
	 * 
	 * @param psd
	 * @param ce
	 * @return Generated Emision.
	 */
	private Emision generateDirectDebt(PSD psd, Cabeceraemisiones ce) {
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

	/**
	 * Re-issue (re-emission) group of generating methods.
	 */

	@Override
	@Transactional
	public List<Cabeceraemisiones> generateRefunded(int year, int month) {
		List<Cabeceraemisiones> lista;
		lista = generateRefundedDirectDebtHeader(year, month);
		// for (Cabeceraemisiones ce: lista) {
		// ce.setEmisions(generateRefundedDirectDebits(ce));
		// }
		return lista;
	}

	private List<Cabeceraemisiones> generateRefundedDirectDebtHeader(int year, int month) {
		List<Cabeceraemisiones> refundedList = null;
		LocalDateTime from = null;
		LocalDateTime to = null;
		from = LocalDateUtil.fromInitialYearMonth(year, month);
		to = LocalDateUtil.fromLastYearMonth(year, month);

		refundedList = cabeceraRepository.findRefundedCabeceraBetweenDatesFull(LocalDateUtil.localDateTimeToDate(from),
				LocalDateUtil.localDateTimeToDate(to));

		return generateRefundedDirectDebtHeader(refundedList);
	}

	private List<Cabeceraemisiones> generateRefundedDirectDebtHeader(List<Cabeceraemisiones> refundedList) {
		List<Cabeceraemisiones> lista = null;

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime fEnvio = today.plusDays(4);
		lista = new ArrayList<>(refundedList.size());

		for (Cabeceraemisiones refunded : refundedList) {
			lista.add(generateRefundedDirectDebtHeader(refunded, today, fEnvio));
		}
		return lista;
	}

	/**
	 * Generate Direct Debt Header for refunded debts.
	 * 
	 * @param refunded.
	 *            Cabeceraemisiones with all emisiones
	 * @param today
	 * @param fEnvio
	 * @return
	 */
	private Cabeceraemisiones generateRefundedDirectDebtHeader(Cabeceraemisiones refunded, LocalDateTime today,
			LocalDateTime fEnvio) {
		Cabeceraemisiones ce = new Cabeceraemisiones();
		ce.setCodigoMes(refunded.getCodigoMes());
		ce.setEmisionManual(true);
		ce.setConcepto(ConstantsCNC.CONCEPTO + " - " + LocalDateUtil.obtainTextualMonth(LocalDateUtil.dateToLocalDateTime(
				refunded.getFechaEmision())) + " (Devueltos)");
		ce.setFechaEmision(LocalDateUtil.localDateTimeToDate(today));
		ce.setFechaEnvio(LocalDateUtil.localDateTimeToDate(fEnvio));
		ce.setParroquiaHasParroco(phpRepository.findActiveParroquiaHasParroco());
		ce.setPeriodo(refunded.getPeriodo());
		cabeceraRepository.save(ce);
		Suscripcion suscripcionActiva = null;
		PSD psdActivo = null;
		for (Emision refundedEmision : refunded.getEmisions()) {
			if (refundedEmision.getDevuelto()) {
				suscripcionActiva = suscripcionRepository.findActiveSuscripcionByPersona(
					refundedEmision.getIdSuscripcion().getIdSuscripcion().getPersona());
				if (suscripcionActiva != null) {
					psdActivo = suscripcionActiva.getActivePSD();
					if (psdActivo != null)
						ce.getEmisions().add(generateDirectDebt(psdActivo, ce));
				}
			}
		}
		return ce;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.cnc.suscripciones.services.emision.EmisionService#deleteCabecera(es.
	 * cnc.suscripciones.domain.Cabeceraemisiones)
	 */
	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public void deleteCabecera(Cabeceraemisiones ce) {
		ce = cabeceraRepository.findCabeceraByIdWithEmisiones(ce);
		for (Emision e : ce.getEmisions()) {
			emisionRepository.delete(e);
		}
		cabeceraRepository.delete(ce);
	}

	/**
		 * Generate a Cabeceraemisiones for the period pattern passed as parameter.
		 * 
		 * @param p
		 *            -> Period pattern.
		 * @param today
		 *            -> Issue date.
		 * @param fEnvio
		 *            -> Payment date.
		 * @return
		 */
		private Cabeceraemisiones generateWSDirectDebtHeader(Periodo p, LocalDateTime today, LocalDateTime fEnvio, Integer year, Integer month) {
			Cabeceraemisiones ce = new Cabeceraemisiones();
			ce.setCodigoMes(month);
			ce.setAnyo(year);
			ce.setEmisionManual(false);
			ce.setConcepto(ConstantsCNC.CONCEPTO + "-" + LocalDateUtil.obtainTextualMonth(today) + "-" + p.getNombre());
	//		ce.setConcepto(ConstantsCNC.CONCEPTO);
			ce.setFechaEmision(LocalDateUtil.localDateTimeToDate(today));
			ce.setFechaEnvio(LocalDateUtil.localDateTimeToDate(fEnvio));
			ce.setParroquiaHasParroco(phpRepository.findActiveParroquiaHasParroco());
			ce.setPeriodo(p.getId());
			return ce;
		}

	/**
	 * Generate a list of Cabeceraemisiones for the period pattern list passed
	 * as parameter and for the current date like issue date and payment date
	 * (issue date + 4)
	 * 
	 * @param periodList
	 * @return
	 */
	private List<Cabeceraemisiones> generateWSDirectDebtHeaders(List<Periodo> periodList, Integer year, Integer month) {
		List<Cabeceraemisiones> lista = null;
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime fEnvio = today.plusDays(4);
		lista = new ArrayList<>(periodList.size());
		for (Periodo p : periodList) {
			lista.add(generateWSDirectDebtHeader(p, today, fEnvio, year, month));
		}
		return lista;
	}

	/**
	 * Regular or normal group of generating methods.
	 */
	
	/**
	 * Generate a list of Cabeceraemisiones for the month passed as parameter.
	 * Search de period pattern for the month passed as parameter.
	 * 
	 * @param month
	 * @return
	 */
	private List<Cabeceraemisiones> generateWSDirectDebtHeaders(int year, int month) {
		List<Periodo> periodList = findMascara(month);
		return generateWSDirectDebtHeaders(periodList, year, month); // mal
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.cnc.suscripciones.services.emision.EmisionService#generate()
	 */
	@Override
	@Transactional
	public List<Cabeceraemisiones> preGenerate(int year, int month) {
		List<Cabeceraemisiones> lista;
		lista = generateWSDirectDebtHeaders(year, month);
		for (Cabeceraemisiones ce : lista) {
			ce.setEmisions(generateWSDirectDebts(ce));
		}
		return lista;
	}

	/**
	 * Generate a set of Emision (issue)
	 * 
	 * @param ce
	 * @return
	 */
	private List<Emision> generateWSDirectDebts(Cabeceraemisiones ce) {
		List<PSD> psdList = psdRepository.findByPeriod(ce.getPeriodo());
		Emision emision;
		List<Emision> emisiones = new ArrayList<>(psdList.size());
		for (PSD psd : psdList) {
			emision = generateWSDirectDebt(psd, ce);
			emisiones.add(emision);
		}
		return emisiones;
	}

	/**
	 * Generate and save a Emision (direct debt issue).
	 * 
	 * @param psd
	 * @param ce
	 * @return Generated Emision.
	 */
	private Emision generateWSDirectDebt(PSD psd, Cabeceraemisiones ce) {
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
		return em;
	}

	public static void main(String[] args) {
		LocalDateTime fecha = LocalDateTime.now();
		fecha = fecha.minusMonths(1l);
		System.out.println("Mes:" + LocalDateUtil.obtainTextualMonth(fecha));

		int mesint = 02;
		
		fecha = LocalDateTime.now();
		fecha = fecha.withDayOfMonth(31);
		System.out.println(fecha);
		
		fecha = fecha.withMonth(mesint);
		System.out.println(fecha);
		System.out.println("Mes:" + LocalDateUtil.obtainTextualMonth(fecha));
		
	}

	@Override
	public List<Cabeceraemisiones> findCabecerasByAnyoMes(Integer year, Integer codigoMes) {
		return this.cabeceraRepository.findCabeceraByYearMonth(year, codigoMes);
	}

}
