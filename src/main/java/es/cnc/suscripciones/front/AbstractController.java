package es.cnc.suscripciones.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

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

}