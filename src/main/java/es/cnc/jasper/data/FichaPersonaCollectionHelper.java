package es.cnc.jasper.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import es.cnc.suscripciones.domain.Bancos;
import es.cnc.suscripciones.domain.Domiciliacion;
import es.cnc.suscripciones.domain.PSD;
import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.Sucursal;
import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.util.LocalDateUtil;

public class FichaPersonaCollectionHelper {
	
	static List<Persona> lista = null;
	
	public static List<Persona> obtain() {
		if (lista == null)
			lista = new ArrayList<>();
		for (int i=0; i<5;i++) {
			if ((i & 1) == 0)
				lista.add(FichaPersonaCollectionHelper.detail1());
			else
				lista.add(FichaPersonaCollectionHelper.detail0());
		}
		return lista;
	}
	
	public static Collection<Suscripcion> obtainSuscriptions() {
		if (lista == null)
			obtain();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0).getSuscripcions();
		}
		throw new RuntimeException("FMM");
	}
	
	private static Persona detail0() {
		Persona p = new Persona();
		Suscripcion s = new Suscripcion();
		PSD psd = new PSD();
		Domiciliacion domiciliacion = new Domiciliacion();
		Bancos banco = new Bancos();
		Sucursal suc = new Sucursal();
		Persona titular = new Persona();
		
		p.setNif("10X");
		p.setNombre("Félix Merino Martínez de Pinillos");
		p.setCp("28017");
		p.setDomicilio("Av. Pablo Neruda, 49 - 2ºB");
		p.setId(1);
		p.setPoblacion("Población con nombre muy largo para probar la multilínea - Madrid");
		
		s.setId(1);
		s.setActivo(true);
		s.setConcepto("Financiación Iglesia Católica");
		s.setDivisa("Euro");
		s.setEuros(30.05d);
		s.setPeriodo("M");
		s.setFechaInicio(LocalDateUtil.localDateToDate(LocalDate.now()));
		s.setPersona(p);

		p.setSuscripcions(new HashSet<>());
		p.getSuscripcions().add(s);
		
		banco.setId(1);
		banco.setCodBco("0095");
		banco.setDenBco("Banco de Vasconia");
		
		suc.setIdBanco(banco);
		suc.setCodSuc("4713");
		suc.setDenSuc("Luchana 21 - Madrid");
		
		titular.setNif("40V");
		titular.setNombre("Titular cuenta de Félix");
		
		domiciliacion.setId(1);
		domiciliacion.setIban("ES2200954713810000183535");
		domiciliacion.setSucursalId(suc);
		domiciliacion.setIdPersona(titular);
		
		psd.setId(1);
		psd.setIdSuscripcion(s);
		psd.setIdDomiciliacion(domiciliacion);
		
		s.setPSDs(new HashSet<>());
		s.getPSDs().add(psd);
		
		return p;
	}

	private static Persona detail1() {
		Persona p = new Persona();
		Suscripcion s = new Suscripcion();
		PSD psd = new PSD();
		Domiciliacion domiciliacion = new Domiciliacion();
		Bancos banco = new Bancos();
		Sucursal suc = new Sucursal();
		Persona titular = new Persona();
		
		p.setNif("21C");
		p.setNombre("Mayte Cabanes Miró");
		p.setCp("28017");
		p.setDomicilio("Av. Pablo Neruda, 49 - 2ºB");
		p.setId(1);
		p.setPoblacion("Población con nombre muy largo para probar la multilínea - Madrid");
		
		s.setId(1);
		s.setActivo(true);
		s.setConcepto("Financiación Iglesia Católica");
		s.setDivisa("Euro");
		s.setEuros(123.15d);
		s.setPeriodo("A");
		s.setFechaInicio(LocalDateUtil.localDateToDate(LocalDate.now()));
		s.setPersona(p);
	
		p.setSuscripcions(new HashSet<>());
		p.getSuscripcions().add(s);
		
		banco.setId(1);
		banco.setCodBco("0182");
		banco.setDenBco("BANCO BILBAO-VIZCAYA");
		
		suc.setIdBanco(banco);
		suc.setCodSuc("4027");
		suc.setDenSuc("AVD. DEL MEDITERRANEO, 24");
		
		titular.setNif("30F");
		titular.setNombre("Titular cuenta de Mayte");
		
		domiciliacion.setId(1);
		domiciliacion.setIban("ES5801824027210000006959");
		domiciliacion.setSucursalId(suc);
		domiciliacion.setIdPersona(titular);
		
		psd.setId(1);
		psd.setIdSuscripcion(s);
		psd.setIdDomiciliacion(domiciliacion);
		
		s.setPSDs(new HashSet<>());
		s.getPSDs().add(psd);
		
		return p;
	}
}
