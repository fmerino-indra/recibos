package es.cnc.suscripciones.front.response;

public abstract class ResponseAbstract<T> {

	private T data;
	private Response info;
	private boolean success = false;
	private long totalCount = 0;

	public ResponseAbstract() {
		super();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public Response getInfo() {
		return info;
	}

	public void setInfo(Response info) {
		this.info = info;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}