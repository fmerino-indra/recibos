package es.cnc.suscripciones.front;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import es.cnc.suscripciones.dto.FilterBaseDTO;
import es.cnc.suscripciones.dto.FilterHolder;
import es.cnc.suscripciones.front.dto.ResumenDTO;
import es.cnc.suscripciones.services.resumen.ResumenService;
import es.cnc.util.LocalDateUtil;

@RestController()
@RequestMapping("{contextPath}/resumen")
public class ResumenController extends AbstractController<ResumenDTO> {
	@Autowired
	ResumenService resumenService;
	
//    @RequestMapping(path={"/{id}"}, method = RequestMethod.GET)
//    public Meses detail(@PathVariable Integer id) {
//        return mesesRepository.findOne(id);
//    }
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<ResumenDTO> list() {
    	return resumenService.getResumen(LocalDateUtil.currentYear());
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", params = { "page", "start", "limit", "filter"})
    public List<ResumenDTO> list(@RequestParam("filter") String filter, 
    		@RequestParam("limit") Integer limit,
			@RequestParam("page") Integer page, 
			@RequestParam("start") Integer start) throws JsonParseException, JsonMappingException, IOException {
    	
		FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh = buildFilters(filter);
//		Boolean activo = null;
//		Boolean noActivo = null;
//		FilterBaseDTO<Boolean> dto = null;
		FilterBaseDTO<Integer> anyoDto = null;
    	Integer anyo = null;
		if (fh.get("anyo")!= null) {
			anyoDto = (FilterBaseDTO<Integer>)fh.get("anyo");
			anyo = anyoDto.getValue();
		}
    	return resumenService.getResumen(anyo);
    }
    
}