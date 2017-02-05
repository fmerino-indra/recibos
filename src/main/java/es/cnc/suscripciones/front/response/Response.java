package es.cnc.suscripciones.front.response;

import java.util.List;

public class Response {
	private List<String> messages;
	private String code;
	
	
	
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
}
