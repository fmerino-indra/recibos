package es.cnc.suscripciones.services.suscripcion;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.stasiena.sepa.util.IBANUtil;
import org.stasiena.sepa.util.NIFUtil;

import es.cnc.suscripciones.domain.Domiciliacion;
import es.cnc.suscripciones.domain.PSD;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.Sucursal;
import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.suscripciones.domain.dao.spring.DomiciliacionRepository;
import es.cnc.suscripciones.domain.dao.spring.PSDRepository;
import es.cnc.suscripciones.domain.dao.spring.SucursalRepository;
import es.cnc.suscripciones.domain.dao.spring.SuscripcionRepository;
import es.cnc.suscripciones.dto.FilterBaseDTO;
import es.cnc.suscripciones.dto.FilterHolder;
import es.cnc.suscripciones.services.persona.PersonaService;
import es.cnc.util.LocalDateUtil;
import es.cnc.util.app.ConstantsData;
import es.cnc.util.sepa.ConstantsCNC;

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
	private PersonaService personaService;
	
	public SuscripcionServiceImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public Page<Suscripcion> findActiveSuscripciones(Integer page, Integer start, Integer limit, FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh) {
		PageRequest pr = new PageRequest(page, limit);
		FilterBaseDTO<?> dto = null;
		if (fh != null) 
			dto = fh.get("nombre");

		
		Page<Suscripcion> response = suscripcionRepository.findActiveSuscripciones(pr, dto != null?dto.getValue().toString():"");
		return response;
	}

	@Override
	public Page<Suscripcion> findInactiveSuscripciones(Integer page, Integer start, Integer limit, FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh) {
		PageRequest pr = new PageRequest(page, limit);

		FilterBaseDTO<?> dto = null;
		if (fh != null) 
			dto = fh.get("nombre");

		Page<Suscripcion> response = suscripcionRepository.findInactiveSuscripciones(pr, dto != null?dto.getValue().toString():"");
		return response;
	}

	@Override
	public Page<Suscripcion> findAllSuscripciones(Integer page, Integer start, Integer limit, FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh) {
		PageRequest pr = new PageRequest(page, limit);

		FilterBaseDTO<?> dto = null;
		if (fh != null) 
			dto = fh.get("nombre");

		return suscripcionRepository.findActiveInactiveSuscripciones(pr, dto != null?dto.getValue().toString():"");
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
		PSD psd = null;
		today = LocalDateTime.now();
		s = suscripcionRepository.findSuscripcionById(id);
		
		if (s == null) {
			String message = "Suscripción no encontrada: " + s;
			logger.error(message);
			throw new RuntimeException(message);
		}
			
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

		// Si no se introduce IBAN, se coge el de la domiciliación activa de la suscripción introducida.
		if (iban == null) {
			psd = s.getActivePSD();
			if (psd != null) {
				d = psd.getIdDomiciliacion();
				ibanValue = s.getActivePSD().getIdDomiciliacion().getIban();
			} else {
				String message = "No hay domiciliación activa para esta suscripción ("+s+") y no se ha indicado IBAN";
				logger.error(message);
				throw new RuntimeException(message);
			}
		} else {
			if (!IBANUtil.validarIBAN(iban)) {
				String message = "IBAN no válido:" + iban;
				logger.error(message);
				throw new RuntimeException(message);
			}
			ibanValue = iban;
		}
		if (s.getActivo())
			desactivar(s, today);
		if (d == null)
			d = createDomiciliacion(ibanValue, titular);
		
		s = createSuscripcion(importe, titular,today,s.getPeriodo());
		createPSD(s, d, today);
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
		d.setCuenta(IBANUtil.obtainCCC(iban));
		d.setDc(IBANUtil.obtainDC(iban));
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
	private Suscripcion createSuscripcion(Double euros, Persona titular, LocalDateTime today, String periodo)  {
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
		List<Persona> lista = personaService.findPersonasByNif(nif);
		
		Persona persona = null;
		LocalDateTime today = null;
		today = LocalDateTime.now();
		Suscripcion s = null;
		Domiciliacion d = null;
		String ibanValue = null;
		
		if (lista == null) { // Crear persona
			throw new RuntimeException("Persona:" + nif + " - No existe");
		} else if (lista.size() > 1) {
			throw new RuntimeException("El NIF:" + nif + " - Está repetido");
		} else {
			persona = lista.get(0);
		}
		
		if (!IBANUtil.validarIBAN(iban)) {
			String message = "IBAN no válido:" + iban;
			logger.error(message);
			throw new RuntimeException(message);
		}
		ibanValue = iban;
		if (periodo == null || !(ConstantsData.ANUAL.equals(periodo) || 
				ConstantsData.SEMESTRAL.equals(periodo) ||
				ConstantsData.TRIMESTRAL.equals(periodo) ||
				ConstantsData.MENSUAL.equals(periodo)) ) {
			String message = MessageFormat. format("PERIODO no válido:", iban);
			logger.error(message);
			throw new RuntimeException(message);
		}
		if (persona != null) {
			s = createSuscripcion(euros, persona, today, periodo);
			d = createDomiciliacion(ibanValue, persona);
			createPSD(s, d, today);
			
		}
		return s;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Suscripcion changeAccountHolder(Integer id, String iban, Double euros, String nif, String nombre, String periodo) {
		Suscripcion s = null;
		Domiciliacion d = null;
		Persona accountHolder = null;
		Persona oldAccountHolder = null;
		LocalDateTime today = null;
		PSD psd = null;
		today = LocalDateTime.now();
		Double importe = null;
		String ibanValue = null;

		s = suscripcionRepository.findSuscripcionById(id);
		
		if (s == null) {
			String message = "Suscripción no encontrada: " + s;
			logger.error(message);
			throw new RuntimeException(message);
		}
		if (nif != null) {
			if (!NIFUtil.isNIFValid(nif)) {
				String message = "NIF introducido no es válido: " + nif;
				logger.error(message);
				throw new RuntimeException(message);
			}
		}
		if (nombre == null) {
			String message = "El nombre introducido es null";
			logger.error(message);
			throw new RuntimeException(message);
		}
		oldAccountHolder = s.getPersona();
		
		if (euros == null && iban == null) {
			logger.info("updateSuscripcion: Nada que hacer");
			return s;
		}
		
		if (euros != null && !s.getEuros().equals(euros)) {
			importe = euros;
		} else {
			importe = s.getEuros();
		}

		// Si no se introduce IBAN, se coge el de la domiciliación activa de la suscripción introducida.
		if (iban == null) {
			psd = s.getActivePSD();
			if (psd != null) {
				d = psd.getIdDomiciliacion();
				ibanValue = s.getActivePSD().getIdDomiciliacion().getIban();
			} else {
				String message = "No hay domiciliación activa para esta suscripción ("+s+") y no se ha indicado IBAN";
				logger.error(message);
				throw new RuntimeException(message);
			}
		} else {
			if (!IBANUtil.validarIBAN(iban)) {
				String message = "IBAN no válido:" + iban;
				logger.error(message);
				throw new RuntimeException(message);
			}
			ibanValue = iban;
		}
		if (s.getActivo())
			desactivar(s, today);
		accountHolder = personaService.createPersona(nif, nombre, oldAccountHolder); 
		d = createDomiciliacion(ibanValue, accountHolder);
		s = createSuscripcion(importe, accountHolder, today,s.getPeriodo());
		createPSD(s, d, today);
		return s;
	}

	@Override
	@Transactional
	public Suscripcion reactivateSuscripcion(Integer id, String iban, Double euros) {
		Suscripcion s = null;
		Domiciliacion d = null;
		Persona titular = null;
		LocalDateTime today = null;
		PSD psd = null;
		today = LocalDateTime.now();
		s = suscripcionRepository.findSuscripcionById(id);
		
		if (s == null) {
			String message = "Suscripción no encontrada: " + s;
			logger.error(message);
			throw new RuntimeException(message);
		}
			
		titular = s.getPersona();
		Double importe = null;
		String ibanValue = null;
		if (euros == null)  {
			if  (iban == null) {
				logger.debug("reactivateSuscripcion: Reactivate Only Suscripción: {0}, Persona: {1} ", s.getId(), s.getPersona().getNombre());
	//			return s;
			} else {
				logger.debug("updateSuscripcion: Update Suscripción: Changes IBAN: {0}, Old Suscripcion: {1}, Persona: {2} ", iban,s.getId(), s.getPersona().getNombre());
			}
		} else {
			if (iban == null) {
				logger.debug("updateSuscripcion: Update Suscripción: Changes Importe: {0}, Old Suscripcion: {1}, Persona: {2} ", euros,s.getId(), s.getPersona().getNombre());
			} else {
				logger.debug("updateSuscripcion: Update Suscripción: Changes IBAN & Importe: {0}, {1}, Old Suscripcion: {2}, Persona: {3} ", iban, euros,s.getId(), s.getPersona().getNombre());
				
			}
		}
		
		if (euros != null && !s.getEuros().equals(euros)) {
			importe = euros;
		} else {
			importe = s.getEuros();
		}
	
		// Si no se introduce IBAN, se coge el de la domiciliación activa de la suscripción introducida.
		if (iban == null) {
			psd = s.getActivePSD();
			if (psd != null) {
				d = psd.getIdDomiciliacion();
				ibanValue = s.getActivePSD().getIdDomiciliacion().getIban();
			} else {
				// Buscar última Domiciliación
				psd = s.getLastPSD();
				if (psd != null) {
					d = psd.getIdDomiciliacion();
					ibanValue = psd.getIdDomiciliacion().getIban();					
				} else {
					String message = "No hay domiciliación activa para esta suscripción ("+s+") y no se ha indicado IBAN";
					logger.error(message);
					throw new RuntimeException(message);
				}
			}
		} else {
			if (!IBANUtil.validarIBAN(iban)) {
				String message = "IBAN no válido:" + iban;
				logger.error(message);
				throw new RuntimeException(message);
			}
			ibanValue = iban;
		}
		if (s.getActivo())
			desactivar(s, today);
		if (d == null)
			d = createDomiciliacion(ibanValue, titular);
		
		s = createSuscripcion(importe, titular,today,s.getPeriodo());
		createPSD(s, d, today);
		return s;
	}

	@Override
	public List<Suscripcion> findAllByIdPersona(Integer idPersona) {
		List<Suscripcion> listaSuscripciones = null;
		Set<PSD> psdSet = null;
		List<PSD> psdList = null;
		if (idPersona == null) {
			listaSuscripciones = new ArrayList<>();
		} else {
			listaSuscripciones = suscripcionRepository.findAllById(idPersona);
			for (Suscripcion s : listaSuscripciones) {
				psdList=pSDRepository.findBySuscripcion(s);
				psdSet = new HashSet<>(psdList);
				s.setPSDs(psdSet);
			}
		}
		return listaSuscripciones;
	}
}
