package es.cnc.suscripciones.dto;

public class FilterBaseDTO<T> {

	private String property;
	private T value;
	
	public FilterBaseDTO() {
		
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
/*
	public static class Builder {
		private String property;

		public Builder(String property) {
			this.property = property;
		}

		private String value;
		public Builder value(String value) {
			this.value = value;
			return this;
		}
		
		public AbstractFilterBaseDTO<String> build() {
			AbstractFilterBaseDTO<String> retorno = new AbstractFilterBaseDTO<>();
			retorno.setProperty(this.property);
			retorno.setValue(this.value);
			return retorno;
		}
	}
*/
}
