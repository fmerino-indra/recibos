package es.cnc.suscripciones.front.response;

import java.util.Map;

public class ResponseMap<T extends Map<?, ?>> extends ResponseAbstract<T> {

	private int count = 0;
	
	public ResponseMap() {
		// TODO Auto-generated constructor stub
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setData(T data) {
		super.setData(data);
		if (data != null) {
			setCount(data.size());
		}
	}


//	public boolean hasMore() {
//		return more;
//	}
//
//	public void setMore(boolean more) {
//		this.more = more;
//	}


}
