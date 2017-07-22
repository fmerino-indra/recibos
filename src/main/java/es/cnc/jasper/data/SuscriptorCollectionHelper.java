package es.cnc.jasper.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import es.cnc.suscripciones.domain.PSD;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.suscripciones.front.dto.SuscripcionDTO;
import es.cnc.util.LocalDateUtil;

public class SuscriptorCollectionHelper {
	public static List<SuscripcionDTO> obtainDTO() {
		List<SuscripcionDTO> lista = null;
		lista = new ArrayList<>();
		for (int i=0; i<175;i++) {
			lista.add(SuscriptorCollectionHelper.detailDTO());
		}
		return lista;
	}
	
	private static SuscripcionDTO detailDTO() {
		SuscripcionDTO dto = new SuscripcionDTO();
		dto.setActivo(true);
		dto.setConcepto("Financiación Iglesia Católica");
		dto.setDivisa("euro");
		dto.setEuros(30.05d);
		dto.setFechaInicio(LocalDateUtil.localDateToDate(LocalDate.now()));
		dto.setIban("ES9400301001320890294271");
		dto.setId(1);
		dto.setNombre("Félix Merino Martínez de Pinillos");
		dto.setPeriodo("A");
		dto.setTelefono("660.95.93.25");
		return dto;
	}
	
	public static List<Suscripcion> obtain() {
		List<Suscripcion> lista = null;
		lista = new ArrayList<>();
		for (int i=0; i<75;i++) {
			lista.add(SuscriptorCollectionHelper.detail());
		}
		
		return lista;
	}
	
	private static Suscripcion detail() {
		Persona p = new Persona();
		Suscripcion s = new Suscripcion();
		PSD psd = new PSD();
		
		
		p.setNif("1A");
		p.setNombre("Félix Merino Martínez de Pinillos");
		p.setCp("28017");
		p.setDomicilio("Av. Pablo Neruda, 49 - 2ºB");
		p.setId(1);
		p.setPoblacion("Madrid");
		
		s.setId(1);
		s.setActivo(true);
		s.setConcepto("Financiación Iglesia Católica");
		s.setDivisa("Euro");
		s.setEuros(30.05d);
		s.setPeriodo("A");
		s.setFechaInicio(LocalDateUtil.localDateToDate(LocalDate.now()));
		s.setPersona(p);

		psd.setId(1);
		psd.setIdSuscripcion(s);
		
		
		return s;
	}
}
