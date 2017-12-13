package es.cnc.suscripciones.front;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.front.response.ResponseList;
import es.cnc.suscripciones.front.response.ResponseMap;
import es.cnc.suscripciones.front.response.ResponseVoid;
import es.cnc.suscripciones.services.devolucion.DevolucionService;
import es.cnc.suscripciones.services.emision.EmisionService;
import es.cnc.suscripciones.services.generacion.GeneracionService;

@RestController()
@RequestMapping("{contextPath}/emisiones")
public class EmisionController {
	@Autowired
	EmisionService emisionService;
	
	@Autowired
	DevolucionService devolucionService;
	
	@Autowired
	GeneracionService generacionService;
	/**
	 * Anota una devolución de recibo.
	 * @param body
	 * @param ids
	 */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/devolver" })
    public void devolver(@RequestBody String body, @RequestParam("id") ArrayList<Integer> ids) {
    	System.out.println(ids);
    	devolucionService.devolver(ids);
    }
    /**
     * Anula una devolución.
     * @param body
     * @param ids
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/anular" })
    public void anular(@RequestBody String body, @RequestParam("id") ArrayList<Integer> ids) {
    	System.out.println(ids);
    	devolucionService.anular(ids);
    }

    
//    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/emitir" }, params={"anyo", "codigoMes"})
    public void porDefecto(@RequestBody String body,@RequestHeader HttpHeaders headers,@RequestParam("anyo") Integer year, @RequestParam("codigoMes") Integer codigoMes) {
    	System.out.println(body);
    }
    /**
     * Emite los recibos de un período.
     * @param body
     * @param ids
     * @throws DatatypeConfigurationException 
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/emitir" }, params={"anyo", "codigoMes"})
    public ResponseMap<Map<String, Cabeceraemisiones>> emitir(@RequestBody String body, @RequestParam("anyo") Integer year, @RequestParam("codigoMes") Integer codigoMes) {
		List<Cabeceraemisiones> cabeceras = null;
    	ResponseMap<Map<String, Cabeceraemisiones>> response = null;
    	Map<String, Cabeceraemisiones> fileNamesCabeceras = null;
    	String fileName = null;
    	response = new ResponseMap<>();
    	 
    	cabeceras = emisionService.generate(year, codigoMes);
    	response.setCount(cabeceras.size());

		for (Cabeceraemisiones cabecera: cabeceras) {
			System.out.println(cabecera.getId() + "-" + cabecera.getPeriodo() +  "-" + cabecera.getCodigoMes() + "-" + cabecera.getConcepto()+"-"+cabecera.getFechaEmision());
		}
		fileNamesCabeceras = new HashMap<>();
		try {
			for (Cabeceraemisiones cabecera: cabeceras) {
				fileName = generacionService.generateISO20022(cabecera);
				fileNamesCabeceras.put(fileName, cabecera);
			}
	    	response.setSuccess(true);

		} catch (DatatypeConfigurationException dce) {
			response.setSuccess(false);
		}
    	response.setData(fileNamesCabeceras);

    	return response;
    }
    
    /**
     * Genera los XML ya emitidos de un período.
     * @param body
     * @param ids
     * @throws DatatypeConfigurationException 
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/generar" }, params={"anyo", "codigoMes"})
    public ResponseVoid generar(@RequestBody String body, @RequestParam("anyo") Integer year, @RequestParam("codigoMes") Integer codigoMes) {
		List<Cabeceraemisiones> cabeceras = null;
    	ResponseVoid response = null;
    	Map<String, Cabeceraemisiones> fileNamesCabeceras = null;
    	String fileName = null;
    	response = new ResponseVoid();
    	 
    	cabeceras = emisionService.findCabecerasByAnyoMes(year, codigoMes);
    	response.setTotalCount(cabeceras.size());

		for (Cabeceraemisiones cabecera: cabeceras) {
			System.out.println(cabecera.getId() + "-" + cabecera.getPeriodo() +  "-" + cabecera.getCodigoMes() + "-" + cabecera.getConcepto()+"-"+cabecera.getFechaEmision());
		}
		fileNamesCabeceras = new HashMap<>();
		try {
			for (Cabeceraemisiones cabecera: cabeceras) {
				fileName = generacionService.generateISO20022(cabecera);
				fileNamesCabeceras.put(fileName, cabecera);
			}
	    	response.setSuccess(true);

		} catch (DatatypeConfigurationException dce) {
			response.setSuccess(false);
		}
    	response.setData(fileNamesCabeceras);

    	return response;
    }
    
    /**
     * Anula una devolución.
     * @param body
     * @param ids
     */
    @Transactional
    @Deprecated
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/preemision" })
    public ResponseList<List<Cabeceraemisiones>> preemision(@RequestBody String body) {
    	List<Cabeceraemisiones> lista = null;
    	ResponseList<List<Cabeceraemisiones>> response = null;
		LocalDate today = LocalDate.now();
    	lista = emisionService.preGenerate(today.getYear(), today.getMonthValue());
    	response = new ResponseList<>();
    	response.setData(lista);
    	response.setCount(lista.size());
    	response.setSuccess(true);
    	return response;
    }
}