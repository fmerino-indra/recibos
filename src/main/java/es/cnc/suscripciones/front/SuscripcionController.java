package es.cnc.suscripciones.front;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
@RequestMapping("**/suscripciones")
public class SuscripcionController extends AbstractController<Suscripcion> {
	@Autowired
	SuscripcionService suscripcionService;
	
	@Autowired
	CertificadoService certificadoService;
	
    @RequestMapping(path={"/{id}"}, method = RequestMethod.GET)
    public ResponseAbstract<SuscripcionDTO> detail(@PathVariable Integer id) {
        SuscripcionDTO dto = null;
//        SuscripcionDTO auxDto = null;
        ResponseDTO<SuscripcionDTO> response = null;
        
        Suscripcion s = suscripcionService.findSuscripcionById(id);
//        List<Suscripcion> suscripciones = suscripcionService.findAllByIdPersona(s.getPersona().getId());
        dto = buildDTO(s);

//        if (suscripciones != null && !suscripciones.isEmpty()) {
//        	for (Suscripcion ss:suscripciones) {
//        		if (!ss.getId().equals(s.getId())) {
//        			auxDto = buildDTO(ss);
//        			dto.addSuscripcionDtoHistory(auxDto);
//        		}
//        	}
//        }
        
        response = new ResponseDTO<>();
        response.setData(dto);
        response.setSuccess(true);
        response.setTotalCount(1l);
        return response;
    }
    
    private SuscripcionDTO buildDTO(Suscripcion s) {
        SuscripcionDTO dto = null;
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
    	return dto;
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
			pagina = suscripcionService.findAllSuscripciones(page-1, start, limit, fh);
		} else if (noActivo != null) {
			pagina = suscripcionService.findInactiveSuscripciones(page-1, start, limit,fh);
			
		} else if (activo != null) {
			pagina = suscripcionService.findActiveSuscripciones(page-1, start, limit, fh);
		}
		returnContainer.setData(toList(pagina));
		returnContainer.setTotalCount(pagina.getTotalElements());
		returnContainer.setSuccess(true);
		return returnContainer;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json" , path={"others"})
    public ResponseList<List<SuscripcionDTO>> listOhters(@RequestParam("filter") String filter) throws JsonParseException, JsonMappingException, IOException {
		ResponseList<List<SuscripcionDTO>> returnContainer = new ResponseList<>();
		List<SuscripcionDTO> others = null;
		List<Suscripcion> suscripciones = null;		
        SuscripcionDTO auxDto = null;
        Suscripcion s = null;
		FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh = null;
		Integer idSuscripcion = null;
		
		fh = buildFilters(filter);
		FilterBaseDTO<Integer> dto = null;
		if (fh.isActive() ) {
			dto =  (FilterBaseDTO<Integer>)fh.get("idSuscription");
			if (dto != null) {
				idSuscripcion = dto.getValue();
				s = suscripcionService.findSuscripcionById(idSuscripcion);
				suscripciones = suscripcionService.findAllByIdPersona(s.getPersona().getId());

		        if (suscripciones != null && !suscripciones.isEmpty()) {
		        	others = new ArrayList<>();
		        	for (Suscripcion ss:suscripciones) {
	        			auxDto = buildDTO(ss);
	        			others.add(auxDto);
		        	}
		        }
		        returnContainer.setData(others);
		        if(others != null)
		        	returnContainer.setTotalCount(others.size());
		        else
		        	returnContainer.setTotalCount(0);
			}
		}
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

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json" , path={"persona"})
	public ResponseList<List<Suscripcion>> listSuscriptionsByPerson(@RequestParam("filter") String filter) throws JsonParseException, JsonMappingException, IOException {
		ResponseList<List<Suscripcion>> returnContainer = new ResponseList<>();
		List<Suscripcion> suscripciones = null;		
		FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh = null;
		Integer idPersona = null;
		
		fh = buildFilters(filter);
		FilterBaseDTO<Integer> dto = null;
		if (fh.isActive() ) {
			dto =  (FilterBaseDTO<Integer>)fh.get("idPersona");
			if (dto != null) {
				idPersona = dto.getValue();
				suscripciones = suscripcionService.findAllByIdPersona(idPersona);
	
		        returnContainer.setData(suscripciones);
		        if(suscripciones != null)
		        	returnContainer.setTotalCount(suscripciones.size());
		        else
		        	returnContainer.setTotalCount(0);
			}
		}
		returnContainer.setSuccess(true);
		return returnContainer;
	}
	
	@RequestMapping("*")    
    public void defaultMethod(@RequestHeader org.springframework.http.HttpHeaders HttpHeaders, @RequestBody HttpMessageConverter body) {
    	System.out.println("aqui");
    }
}