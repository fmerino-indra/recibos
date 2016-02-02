package es.cnc.suscripciones.domain;

import java.io.Serializable;

public abstract class AbstractEntity<I extends Serializable> {

	public abstract I getId();
	public abstract void setId(I id);
}
