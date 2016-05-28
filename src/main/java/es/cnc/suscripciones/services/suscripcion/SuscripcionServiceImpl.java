package es.cnc.suscripciones.services.suscripcion;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.stasiena.sepa.util.IBANUtil;

import es.cnc.suscripciones.domain.Domiciliacion;
import es.cnc.suscripciones.domain.PSD;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.Sucursal;
import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.suscripciones.domain.dao.spring.DomiciliacionRepository;
import es.cnc.suscripciones.domain.dao.spring.PSDRepository;
import es.cnc.suscripciones.domain.dao.spring.PersonaRepository;
import es.cnc.suscripciones.domain.dao.spring.SucursalRepository;
import es.cnc.suscripciones.domain.dao.spring.SuscripcionRepository;
import es.cnc.util.ConstantsCNC;
import es.cnc.util.LocalDateUtil;

@Component("suscripcionService")
public class SuscripcionServiceImpl implements SuscripcionService {
	Logger logger;

	@Autowired
	private SuscripcionRepository suscripcionRepository;

	@Autowired
	private PSDRepository pSDRepository;
	
	@Autowired
	private SucursalRepository sucursalRepository;

	@Autowired
	private DomiciliacionRepository domiciliacionRepository;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	public SuscripcionServiceImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public Page<Suscripcion> findActiveSuscripciones(Integer page, Integer start, Integer limit) {
		PageRequest pr = new PageRequest(page, limit);
		Page<Suscripcion> response = suscripcionRepository.findActiveSuscripciones(pr);
		return response;
	}

	@Override
	public Page<Suscripcion> findInactiveSuscripciones(Integer page, Integer start, Integer limit) {
		PageRequest pr = new PageRequest(page, limit);
		Page<Suscripcion> response = suscripcionRepository.findInactiveSuscripciones(pr);
		return response;
	}

	@Override
	public Suscripcion findSuscripcionById(Integer id) {
		return suscripcionRepository.findSuscripcionById(id);
	}

	@Override
	@Transactional
	public Suscripcion updateSuscripcion(Integer id, String iban, Double euros) {
		Suscripcion s = null;
		Domiciliacion d = null;
		Persona titular = null;
		LocalDateTime today = null;
		today = LocalDateTime.now();
		s = suscripcionRepository.findSuscripcionById(id);
		titular = s.getPersona();
		Double importe = null;
		String ibanValue = null;
		if (euros == null && iban == null) {
			logger.info("updateSuscripcion: Nada que hacer");
			return s;
		}
		
		if (euros != null && !s.getEuros().equals(euros)) {
			importe = euros;
		} else {
			importe = s.getEuros();
		}
		
		if (null != iban) {
			if (!IBANUtil.validarIBAN(iban)) {
				String message = "IBAN no válido:" + iban;
				logger.error(message);
				throw new RuntimeException(message);
			}

			d = s.getActivePSD().getIdDomiciliacion();
			if (!iban.equals(d.getIban())) {
				ibanValue = iban;
			} else {
				ibanValue = s.getActivePSD().getIdDomiciliacion().getIban();
			}
		}		
		desactivar(s, today);
		d = createDomiciliacion(ibanValue, titular);
		s = createSuscripcion(ibanValue, importe, titular,today,s.getPeriodo());
		createPSD(s, d, today);
		
//		if (euros != null && !s.getEuros().equals(euros)) {
//			desactivar(s, today);
//			d = createDomiciliacion(iban, titular);
//			s = createSuscripcion(iban, euros, titular, today, s.getPeriodo());
//			createPSD(s, d, today);
//		}
//		
//		if (null != iban) {
//			d = s.getActivePSD().getIdDomiciliacion();
//			if (!iban.equals(d.getIban()) && IBANUtil.validarIBAN(iban)) {
//				desactivar(s, today);
//				d = createDomiciliacion(iban, titular);
//				s = createSuscripcion(iban, euros != null?euros:s.getEuros(), titular,today,s.getPeriodo());
//				createPSD(s, d, today);
//			}
//		}
		return s;
	}
	
	@Transactional
	private Domiciliacion createDomiciliacion(String iban, Persona titular) {
		Domiciliacion d = null;
		Sucursal suc = null;
		suc = sucursalRepository.findSucursalByCodBanAndCodSuc(IBANUtil.obtainBanco(iban), IBANUtil.obtainSucursal(iban));
		if (suc == null) {
			String message = "Sucursal no encontrada:" + IBANUtil.obtainSucursal(iban);
			logger.error(message);
			throw new RuntimeException(message);
		}
		d = new Domiciliacion();
		d.setIban(iban);
		d.setIdPersona(titular);
		d.setSucursalId(suc);
		d = domiciliacionRepository.save(d);
		return d;
	}
	/*
		String prefijo = null, banco = null, sucursal = null, dc = null, cuenta = null;

		prefijo = IBANUtil.obtainPrefijo(iban);
		banco = IBANUtil.obtainBanco(iban);
		sucursal = IBANUtil.obtainSucursal(iban);
		dc = IBANUtil.obtainDC(iban);
		cuenta = IBANUtil.obtainCCC(iban);
		this.cuenta = cuenta;
		this.dc = dc;
		this.

	 */
	
	@Transactional
	private Suscripcion desactivar(Suscripcion s, LocalDateTime today) {
		PSD active = null;
		active = s.getActivePSD();
		s.setActivo(false);
		s.setFechaBaja(LocalDateUtil.localDateTimeToDate(today));
		suscripcionRepository.save(s);
		if (active != null) {
			active.setFechaBaja(LocalDateUtil.localDateTimeToDate(today));
			pSDRepository.save(active);
		}
		return s;
	}

	@Transactional
	private Suscripcion createSuscripcion(String iban, Double euros, Persona titular, LocalDateTime today, String periodo)  {
		Suscripcion s = null;
		s = new Suscripcion();
		s.setActivo(true);
		s.setConcepto(ConstantsCNC.CONCEPTO);
		s.setDivisa(ConstantsCNC.DIVISA);
		s.setEuros(euros);
		s.setFechaInicio(LocalDateUtil.localDateTimeToDate(today));
		s.setPeriodo(periodo);
		s.setPersona(titular);
		s.setSecuenciaAdeudo(ConstantsCNC.RCUR);
		suscripcionRepository.save(s);
		return s;
	}

	@Transactional
	private PSD createPSD(Suscripcion s, Domiciliacion d, LocalDateTime today) {
		PSD psd = null;
		psd = new PSD();
		psd.setIdSuscripcion(s);
		psd.setIdDomiciliacion(d);
		psd.setFechaFirma(LocalDateUtil.localDateTimeToDate(today));
		psd.setFechaInicio(LocalDateUtil.localDateTimeToDate(today));
		psd = pSDRepository.save(psd);
		if (s.getPSDs() == null) {
			s.setPSDs(new HashSet<>());
		}
		s.getPSDs().add(psd);
		return psd;
	}

	@Override
	@Transactional
	public Suscripcion cancelSuscripcion(Integer id) {
		Suscripcion s = null;
		LocalDateTime today = null;

		s = suscripcionRepository.findSuscripcionById(id);
		today = LocalDateTime.now();
		return this.desactivar(s, today);
	}

	@Override
	public Suscripcion findActiveSuscripcionByPersona(Persona p) {
		return suscripcionRepository.findActiveSuscripcionByPersona(p);
	}

	@Override
	@Transactional
	public Suscripcion createSuscripcion(String iban, Double euros, String nif, String periodo) {
		List<Persona> lista = personaRepository.findPersonaByNif(nif);
		Persona persona = null;
		LocalDateTime today = null;
		today = LocalDateTime.now();
		Suscripcion s = null;
		if (lista == null) { // Crear persona
			throw new RuntimeException("Persona:" + nif + " - No existe");
		} else if (lista.size() > 1) {
			throw new RuntimeException("El NIF:" + nif + " - Está repetido");
		} else {
			persona = lista.get(0);
		}
		if (persona != null) {
			s = createSuscripcion(iban, euros, persona, today, periodo);
		}
		return s;
	}
}
