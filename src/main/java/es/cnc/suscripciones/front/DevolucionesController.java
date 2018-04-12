package es.cnc.suscripciones.front;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import es.cnc.suscripciones.front.dto.DashboardDTO;
import es.cnc.suscripciones.front.dto.UploadForm;
import es.cnc.suscripciones.services.devolucion.DevolucionService;

@RestController()
@RequestMapping("**/devoluciones")
public class DevolucionesController extends AbstractController<DashboardDTO> {
	
	@Autowired
	DevolucionService devolucionService;
	
	@RequestMapping(value="/other", method = RequestMethod.POST)
//    public void list(@RequestBody Object obj, @RequestParam("xml-path") MultipartFile file,
    public void list(@ModelAttribute UploadForm form, @RequestParam("name") String file) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Hola");
		form.getFiles();
    }

//	@RequestMapping(value="/upload", method = RequestMethod.POST)
//  public void upload(@RequestParam("xml-path")MultipartFile file, @RequestParam("name") String fileName) throws JsonParseException, JsonMappingException, IOException {
//		System.out.println("Hola");
//		byte[] bytes = null;
//		if (!file.isEmpty()) {
//			bytes=file.getBytes();
//			devolucionService.readRefundXMLJAXB(new ByteArrayInputStream(bytes));
//		}
//  }
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	  public void uploadSinNombre(@RequestParam("xml-path")MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
			System.out.println("Hola");
			byte[] bytes = null;
			if (!file.isEmpty()) {
				bytes=file.getBytes();
				devolucionService.readRefundXMLJAXB(new ByteArrayInputStream(bytes));
			}
	  }
}