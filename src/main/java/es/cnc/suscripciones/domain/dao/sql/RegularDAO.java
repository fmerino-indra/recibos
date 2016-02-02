package es.cnc.suscripciones.domain.dao.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.cnc.suscripciones.domain.AbstractEntity;

public abstract class RegularDAO<T extends AbstractEntity<I>, I extends Serializable> extends AbstractDAO<T,I> {
    private List<String> fieldNames4OrderClauseFilter;

    public RegularDAO() {
    	this(null);
    }
	public RegularDAO(List<String> fields) {
		super();
		this.fieldNames4OrderClauseFilter = fields;
	}

	@Override
	protected final List<String> getFieldNames4OrderClauseFilter() {
		if (fieldNames4OrderClauseFilter == null)
			fieldNames4OrderClauseFilter = new ArrayList<>();
		return fieldNames4OrderClauseFilter;
	}

}
