package es.cnc.suscripciones.front;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import es.cnc.suscripciones.dto.FilterBaseDTO;
import es.cnc.suscripciones.dto.FilterHolder;
import es.cnc.suscripciones.dto.JsonToFilter;

public class AbstractController<T> {

	public AbstractController() {
		super();
	}

	protected List<T> toList(Page<T> page) {
		List<T> returnValue = new ArrayList<T>(page.getSize());
		for (T ce:page) {
			returnValue.add(ce);
		}
		return returnValue;
	}

	public final FilterHolder<? extends Collection<FilterBaseDTO<?>>> buildFilters(String filter) throws JsonParseException, JsonMappingException, IOException {
		List<FilterBaseDTO<?>> params = null;
		FilterHolder<? extends Collection<FilterBaseDTO<?>>> filters = null;
		params = JsonToFilter.toFilter(filter, new TypeReference<List<FilterBaseDTO<?>>>() {});
		filters = new FilterHolder<Collection<FilterBaseDTO<?>>>(params);
		return filters;
	}
}