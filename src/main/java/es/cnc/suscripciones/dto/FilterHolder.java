package es.cnc.suscripciones.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FilterHolder<T extends Collection<FilterBaseDTO<?>>> {
	public static String FILTER = "filter";
	
	T filters;
	public FilterHolder(T filters) {
		this.filters = filters;
		buildMap();
	}
	private boolean active = true;
	private void buildMap() {
		if (filters != null) {
			mapa = new HashMap<>();
			for (FilterBaseDTO<?> filter: filters) {
				if (filter.getProperty().equals(FILTER)) {
					setActive((Boolean)filter.getValue()); 
				} else {
					mapa.put(filter.getProperty(), filter);
				}
			}
		}
	}
	public Map<String, FilterBaseDTO<?>> mapa;
	
	public FilterBaseDTO<?> get(String property) {
		FilterBaseDTO<?> retorno = null;
		if (mapa != null) {
			retorno = mapa.get(property);
		}
		return retorno; 
	}
	
	public boolean isEmpty() {
		return mapa == null || mapa.isEmpty();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
