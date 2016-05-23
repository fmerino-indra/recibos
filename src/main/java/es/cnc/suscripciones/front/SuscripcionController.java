package es.cnc.suscripciones.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.cnc.suscripciones.domain.PSD;
import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.suscripciones.front.dto.SuscripcionDTO;
import es.cnc.suscripciones.services.suscripcion.SuscripcionService;

@RestController()
@RequestMapping("{contextPath}/suscripciones")
public class SuscripcionController {
	@Autowired
	SuscripcionService suscripcionService;
	
    @RequestMapping(path={"/{id}"}, method = RequestMethod.GET)
    public ResponseAbstract<SuscripcionDTO> detail(@PathVariable Integer id) {
        SuscripcionDTO dto = null;
        ResponseDTO<SuscripcionDTO> response = null;
        
        Suscripcion s = suscripcionService.findSuscripcionById(id);
        if (s != null) {
            dto = new SuscripcionDTO();
        	dto.setId(s.getId());
        	dto.setNombre(s.getPersona().getNombre());
        	dto.setEuros(s.getEuros());
        	dto.setDivisa(s.getDivisa());
        	dto.setFechaBaja(s.getFechaBaja());
        	dto.setFechaInicio(s.getFechaInicio());
        	dto.setConcepto(s.getConcepto());
        	dto.setPeriodo(s.getPeriodo());
        	dto.setActivo(s.getActivo());
        	if (s.getPSDs() != null && s.getPSDs().size() > 0 ) {
        		for (PSD psd : s.getPSDs()) {
        			if ((psd.getIdDomiciliacion() != null) && (psd.getFechaBaja() == null)) {
    					dto.setIban(psd.getIdDomiciliacion().getIban());
    					break;
        			}
        		}
        	}
        }
        response = new ResponseDTO<>();
        response.setData(dto);
        response.setSuccess(true);
        response.setTotalCount(1l);
        return response;
    }
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseList<List<Suscripcion>> list(@RequestParam("filter") String filter, 
    		@RequestParam("limit") Integer limit,
			@RequestParam("page") Integer page, 
			@RequestParam("start") Integer start) {
		ResponseList<List<Suscripcion>> returnContainer = new ResponseList<>();

		Page<Suscripcion> pagina = suscripcionService.findActiveSuscripciones(page-1, start, limit);
		returnContainer.setData(toList(pagina));
		returnContainer.setTotalCount(pagina.getTotalElements());
		returnContainer.setSuccess(true);
		return returnContainer;
	}

	private List<Suscripcion> toList(Page<Suscripcion> page) {
		List<Suscripcion> returnValue = new ArrayList<Suscripcion>(page.getSize());
		for (Suscripcion ce:page) {
			returnValue.add(ce);
		}
		return returnValue;
	}
	
    @RequestMapping(method = RequestMethod.PUT, produces = "application/json", path={"/{id}"})
    public void cambiarSuscripcion(@RequestBody String body, @PathVariable("id") Integer id) {
    	System.out.println(body);
    }
    
    /**
     * TODO Si se pone RequestBody peta
     * @param id
     */
    @RequestMapping(method = RequestMethod.DELETE, path={"/{id}"})
    public void cancelSuscripcion(@PathVariable("id") Integer id) {
    	suscripcionService.cancelSuscripcion(id);
    }
    
    
}