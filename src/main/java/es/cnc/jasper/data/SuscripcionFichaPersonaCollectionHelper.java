package es.cnc.jasper.data;

import java.util.ArrayList;
import java.util.List;

import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.domain.Suscripcion;

public class SuscripcionFichaPersonaCollectionHelper {
	
	static List<Persona> lista = null;
	
	public static List<Suscripcion> obtainSuscriptions() {
		lista = FichaPersonaCollectionHelper.obtain();
		if (lista != null && !lista.isEmpty()) {
			return new ArrayList<Suscripcion>(lista.get(0).getSuscripcions());
		}
		throw new RuntimeException("FMM");
	}
}
