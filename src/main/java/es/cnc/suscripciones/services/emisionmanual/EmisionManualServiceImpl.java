package es.cnc.suscripciones.services.emisionmanual;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.CabeceraEmisionManual;
import es.cnc.suscripciones.domain.DetalleSolicitudEmision;
import es.cnc.suscripciones.domain.EmisionManual;
import es.cnc.suscripciones.domain.SolicitudEmisionManual;
import es.cnc.suscripciones.domain.dao.spring.CabeceraEmisionManualRepository;
import es.cnc.suscripciones.domain.dao.spring.DetalleSolicitudEmisionRepository;
import es.cnc.suscripciones.domain.dao.spring.EmisionManualRepository;
import es.cnc.suscripciones.domain.dao.spring.ParroquiaHasParrocoRepository;
import es.cnc.suscripciones.domain.dao.spring.ParroquiaIbanRepository;
import es.cnc.suscripciones.domain.dao.spring.SepaCoreXmlManualRepository;
import es.cnc.suscripciones.domain.dao.spring.SolicitudEmisionManualRepository;
import es.cnc.suscripciones.front.dto.SolicitudDTO;
import es.cnc.util.LocalDateUtil;

@Component("emisionManualService")
public class EmisionManualServiceImpl implements EmisionManualService {

	@Autowired
	ParroquiaHasParrocoRepository phpRepository;

	@Autowired
	ParroquiaIbanRepository ibanRepository;

	@Autowired
	SepaCoreXmlManualRepository xmlManualRepository;

	@Autowired
	SolicitudEmisionManualRepository solicitudRepository;
	
	@Autowired
	DetalleSolicitudEmisionRepository detalleRepository;
	
	@Autowired
	CabeceraEmisionManualRepository cabeceraManualRepository;
	
	@Autowired
	EmisionManualRepository emisionManualRepository;
	
	public EmisionManualServiceImpl() {
		super();
	}

	

	@Override
	public SolicitudEmisionManual generateSolicitudManual(List<SolicitudDTO> solicitudes, String concepto, Date fecha) {
		SolicitudEmisionManual solicitud = null;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		
		solicitud = new SolicitudEmisionManual();
		solicitud.setFechaSolicitud(fecha);
		solicitud.setConcepto(concepto);
		solicitud = solicitudRepository.save(solicitud);
		
		
		DetalleSolicitudEmision detalle = null;
		
		for (SolicitudDTO det : solicitudes) {
			detalle = new DetalleSolicitudEmision();
			detalle.setImporte(det.getImporte());
			detalle.setDomiciliacion(det.getDomiciliacion());
			detalle.setSolicitudEmisionManual(solicitud);
			detalle.setDivisa("Eur");
			detalleRepository.save(detalle);
		}
		
		return solicitud;
	}

	@Override
	public List<CabeceraEmisionManual> generateCabeceraEmisionManual(List<SolicitudEmisionManual> solicitudes) {
		CabeceraEmisionManual cabecera = null;
		List<CabeceraEmisionManual> lista = null;
		List<EmisionManual> listaEmisiones = null;
		
		int month;
		int year;

		LocalDateTime fechaLD = null;
		@SuppressWarnings("unused")
		LocalDateTime fEnvio = null;
		lista = new ArrayList<>();
		
		for (SolicitudEmisionManual solicitud : solicitudes) {
			cabecera = new CabeceraEmisionManual();
			fechaLD = LocalDateUtil.dateToLocalDateTime(solicitud.getFechaSolicitud());
			fEnvio = fechaLD.plusDays(4);
			month = fechaLD.getMonthValue();
			year = fechaLD.getYear();
			cabecera.setFechaEmision(LocalDateUtil.localDateTimeToDate(fechaLD));

//			cabecera.setFechaEnvio(LocalDateUtil.localDateTimeToDate(fEnvio));
			
			cabecera.setConcepto(solicitud.getConcepto());
			
			cabecera.setAnyo(year);
			cabecera.setCodigoMes(month);

		
		
			//TODO [FMM] Revisar si la fecha de envío está bien actualizada aquí o mejor en la generación del XML
//			ce.setFechaEnvio(LocalDateUtil.localDateTimeToDate(fEnvio));
			// FMM
			cabecera.setParroquiaHasParroco(phpRepository.findActiveParroquiaHasParroco());
			cabecera.setDomiciliacion(ibanRepository.findActiveParroquiaIban().getDomiciliacion());
			cabecera.setSolicitud(solicitud);
			cabecera = cabeceraManualRepository.save(cabecera);
			lista.add(cabecera);
			
			listaEmisiones = generateDetalleEmisionManual(solicitud, cabecera);
			cabecera.setEmisionManuals(listaEmisiones);
		}
		
		
		
		return lista;
	}

	private List<EmisionManual> generateDetalleEmisionManual (SolicitudEmisionManual solicitud, CabeceraEmisionManual cabecera) {
		List<EmisionManual> response = null;
		EmisionManual emision = null;
		response = new ArrayList<>();
		for (DetalleSolicitudEmision detalle : solicitud.getSolicitudEmisiones()) {
			emision = new EmisionManual();
			emision.setDetalleSolicitudEmision(detalle);
			emision.setDevuelto(false);
			emision.setImporte(detalle.getImporte());
			emision.setDomiciliacion(detalle.getDomiciliacion());
			emision.setIdCabecera(cabecera);
			emision.setId(detalle.getId());
			emision.setDivisa(detalle.getDivisa());
			emision = emisionManualRepository.save(emision);
			response.add(emision);
		}
		return response;
	}
}
