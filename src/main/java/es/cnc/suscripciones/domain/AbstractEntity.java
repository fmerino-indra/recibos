package es.cnc.suscripciones.domain;

import java.io.Serializable;

public abstract class AbstractEntity<I extends Serializable> {

	protected Class<? extends AbstractEntity<I>> entityClass;
	public abstract I getId();
	public abstract void setId(I id);
	
	protected AbstractEntity(Class<? extends AbstractEntity<I>> clase) {
		this.entityClass = clase;
	}
	
	public static AbstractEntity<?> fromJSONToEntity(String json, Class<? extends AbstractEntity<?>> entityClass) {
		AbstractEntity<?> entity = null;
		try {
			entity = (AbstractEntity<?>)entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			//[FMM] Must throw a prepared exception
			e.printStackTrace();
		}
		return entity;
	}
}
