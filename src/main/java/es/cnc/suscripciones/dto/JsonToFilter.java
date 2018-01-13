package es.cnc.suscripciones.dto;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToFilter {
	public static <T> T toFilter(String json, TypeReference<T> type) throws JsonParseException, JsonMappingException, IOException {
		T data = null;; 
		ObjectMapper mapper = new ObjectMapper();
		
		if (json != null && !json.isEmpty()) {
			try {
				data = mapper.readValue(json, type);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String json = null;
			json="[{\"property\":\"filter\",\"value\":true},{\"property\":\"nif\",\"value\":2},{\"property\":\"fecha\",\"value\":\"01/01/2018\"}]";
		JsonToFilter.toFilter(json, new TypeReference<List<FilterBaseDTO<?>>>() {});
	}
}
