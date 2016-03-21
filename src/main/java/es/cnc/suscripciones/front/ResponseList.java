package es.cnc.suscripciones.front;

import java.util.Collection;

public class ResponseList<T extends Collection<?>> extends ResponseAbstract<T> {

	private int count = 0;
//	private boolean more = false;
	
	public ResponseList() {
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
