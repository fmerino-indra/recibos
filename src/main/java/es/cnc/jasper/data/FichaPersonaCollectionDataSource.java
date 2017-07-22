package es.cnc.jasper.data;

import java.util.Collection;
import java.util.List;

import es.cnc.suscripciones.domain.Persona;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FichaPersonaCollectionDataSource extends JRBeanCollectionDataSource {
	public FichaPersonaCollectionDataSource(Collection<?> beanCollection) {
		super(beanCollection);
		// TODO Auto-generated constructor stub
	}

	public static List<Persona> personas;
	
	public static List<Persona> obtainPersonas() {
		return personas;
	}
	
}
