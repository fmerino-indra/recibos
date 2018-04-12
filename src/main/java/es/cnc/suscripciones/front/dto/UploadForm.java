package es.cnc.suscripciones.front.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {
	private MultipartFile files;

	public MultipartFile getFiles() {
		return files;
	}

	public void setFiles(MultipartFile files) {
		this.files = files;
	}
	
}
