package es.cnc.suscripciones.front;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import es.cnc.suscripciones.domain.PSD;
import es.cnc.suscripciones.domain.Suscripcion;
import es.cnc.suscripciones.dto.FilterBaseDTO;
import es.cnc.suscripciones.dto.FilterHolder;
import es.cnc.suscripciones.front.dto.CertificadoDTO;
import es.cnc.suscripciones.front.dto.SuscripcionDTO;
import es.cnc.suscripciones.front.response.ResponseAbstract;
import es.cnc.suscripciones.front.response.ResponseDTO;
import es.cnc.suscripciones.front.response.ResponseList;
import es.cnc.suscripciones.services.certificado.CertificadoService;
import es.cnc.suscripciones.services.suscripcion.SuscripcionService;

@RestController()
@RequestMapping("{contextPath}/suscripciones")
public class SuscripcionController extends AbstractController<Suscripcion> {
	@Autowired
	SuscripcionService suscripcionService;
	
	@Autowired
	CertificadoService certificadoService;
	
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
    @SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseList<List<Suscripcion>> list(@RequestParam("filter") String filter, 
    		@RequestParam("limit") Integer limit,
			@RequestParam("page") Integer page, 
			@RequestParam("start") Integer start) throws JsonParseException, JsonMappingException, IOException {
		ResponseList<List<Suscripcion>> returnContainer = new ResponseList<>();
		Page<Suscripcion> pagina = null;

		FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh = buildFilters(filter);
		Boolean activo = null;
		Boolean noActivo = null;
		FilterBaseDTO<Boolean> dto = null;
		if (fh.isActive() ) {
			dto =  (FilterBaseDTO<Boolean>)fh.get("activo");
			if (dto != null) {
				activo = dto.getValue();
			}
			dto =  (FilterBaseDTO<Boolean>)fh.get("noActivo");
			if (dto != null) {
				noActivo = dto.getValue();
			}
		}
		
		if (activo != null && noActivo != null || activo == null && noActivo == null) {
			pagina = suscripcionService.findAllSuscripciones(page-1, start, limit);
		} else if (noActivo != null) {
			pagina = suscripcionService.findInactiveSuscripciones(page-1, start, limit);
			
		} else if (activo != null) {
			pagina = suscripcionService.findActiveSuscripciones(page-1, start, limit, fh);
		}
		returnContainer.setData(toList(pagina));
		returnContainer.setTotalCount(pagina.getTotalElements());
		returnContainer.setSuccess(true);
		return returnContainer;
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "application/json", path={"/{id}"})
    public void cambiarSuscripcion(@RequestBody String body, @PathVariable("id") Integer id) {
    	System.out.println(body);
    }
    
    /**
     * TODO Si se pone RequestBody peta
     * @param id
     */
    @RequestMapping(method = RequestMethod.POST, path={"/desactivar"})
    public void cancelSuscripcion(@RequestParam("id") ArrayList<Integer> ids) {
    	for (Integer id : ids)
    		suscripcionService.cancelSuscripcion(id);
    }
    
    /**
     * Copy the Suscripcions with id in id list. Set the Activo column to 1. 
     * Takes the old Domiciliacion and creates a new P_S_D record.
     * @param id
     */
    @RequestMapping(method = RequestMethod.POST, path={"/activar"})
    public void activateSuscripcion(@RequestParam("id") ArrayList<Integer> ids) {
    	for (Integer id : ids)
    		suscripcionService.reactivateSuscripcion(id, null, null);
    }
    
    // TODO In process
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", path={"/certificado/{id}"})
	public ResponseList<List<CertificadoDTO>> listCertificado(@PathVariable("id") Integer id
			) throws JsonParseException, JsonMappingException, IOException {
		ResponseList<List<CertificadoDTO>> returnContainer = new ResponseList<>();
	
		List<CertificadoDTO> list = certificadoService.findListForCetificado(id);
		
		returnContainer.setData(list);
		returnContainer.setTotalCount(list.size());
		returnContainer.setSuccess(true);
		return returnContainer;
	}
    
    
}