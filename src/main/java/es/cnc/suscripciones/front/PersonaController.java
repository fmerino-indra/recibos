package es.cnc.suscripciones.front;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.front.response.ResponseAbstract;
import es.cnc.suscripciones.front.response.ResponseDetail;
import es.cnc.suscripciones.front.response.ResponseList;
import es.cnc.suscripciones.services.persona.PersonaService;

@RestController()
//@RequestMapping("{contextPath}/personas")
@RequestMapping("**/personas")
public class PersonaController extends AbstractController<Persona> {
	@Autowired
	PersonaService personaService;
	
    @RequestMapping(path={"/{id}"}, method = RequestMethod.GET)
    public ResponseAbstract<Persona> detail(@PathVariable Integer id) {
        Persona p = personaService.findPersonaById(id);
        ResponseDetail<Persona> response = null;
        
        response = new ResponseDetail<>();
        response.setData(p);
        response.setSuccess(true);
        response.setTotalCount(1l);
        return response;
        
    }
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseList<List<Persona>> list(@RequestParam("filter") String filter, 
    		@RequestParam("limit") Integer limit,
			@RequestParam("page") Integer page, 
			@RequestParam("start") Integer start, HttpServletRequest request ) {
		ResponseList<List<Persona>> returnContainer = new ResponseList<>();
		Page<Persona> pagina = null;
		if (filter == null || filter.isEmpty()) {
			pagina = personaService.findPersonas(page-1, start, limit);
		} else {
			try {
				pagina = personaService.findPersonasWithCriteria(buildFilters(filter), page-1, start, limit);
			} catch (JsonParseException e) {
				// TODO [FMM] Producir errores
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		returnContainer.setData(toList(pagina));
		returnContainer.setTotalCount(pagina.getTotalElements());
		returnContainer.setSuccess(true);
		return returnContainer;
    }
    
    /**
     * Anula una devolución.
     * @param body
     * @param ids
     */
//    @RequestMapping(method = RequestMethod.POST, produces = "application/json") // , path = { "/anular" }
//  public ResponseDetail<Persona> crear(HttpServletRequest request, @RequestBody Map<String, Object> body) {
//    public ResponseDetail<Persona> crear(HttpServletRequest request, @RequestBody Persona p, UriComponentsBuilder uriBuilder) {

/* Version para ResponseEntity
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> crear(HttpServletRequest servletRequest, HttpRequest request, @RequestBody Persona p, UriComponentsBuilder uriBuilder) {
    	p = personaService.createPersona(p);
    	ResponseDetail<Persona> response = new ResponseDetail<>();
    	response.setData(p);
    	response.setSuccess(true);
    	response.setTotalCount(1l);
    	// location header
    	uriBuilder.pathSegment(servletRequest.getContextPath(), servletRequest.getRequestURI().startsWith("/")?servletRequest.getRequestURI().substring(1):servletRequest.getRequestURI(), String.valueOf(p.getId()));
    	
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Content-Type", "application/json");
        //RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        //request.getURI();
        //headers.add("Location",uriBuilder.path(a.value()[0]+"/"+p.getId()).build().toUriString());
        //return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        
        //UriComponentsBuilder uriCB = UriComponentsBuilder.fromHttpRequest(request).pathSegment(String.valueOf(p.getId()));
        //uriCB.build().toUriString();
        
        return ResponseEntity.created(uriBuilder.build().toUri()).header("MyResponseHeader", "MyValue").body(p);
    }
*/
    
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDetail<Persona> crear(HttpServletRequest servletRequest, HttpRequest request, @RequestBody Persona p, UriComponentsBuilder uriBuilder) {
    	p = personaService.createPersona(p);
    	ResponseDetail<Persona> response = new ResponseDetail<>();
    	response.setData(p);
    	response.setSuccess(true);
    	response.setTotalCount(1l);
    	// location header
    	uriBuilder.pathSegment(servletRequest.getContextPath(), servletRequest.getRequestURI().startsWith("/")?servletRequest.getRequestURI().substring(1):servletRequest.getRequestURI(), String.valueOf(p.getId()));
    	
        HttpHeaders headers = new HttpHeaders();
        //RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        //request.getURI();
        headers.add(HttpHeaders.LOCATION,uriBuilder.build().toUriString());
        
        return response;
    }
    
    @RequestMapping(path={"/{id}"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseDetail<Persona> actualizar(HttpServletRequest servletRequest, HttpRequest request, @RequestBody Persona p, UriComponentsBuilder uriBuilder) {
    	p = personaService.updatePersona(p);
    	ResponseDetail<Persona> response = new ResponseDetail<>();
    	response.setData(p);
    	response.setSuccess(true);
    	response.setTotalCount(1l);
    	// location header
    	uriBuilder.pathSegment(servletRequest.getContextPath(), servletRequest.getRequestURI().startsWith("/")?servletRequest.getRequestURI().substring(1):servletRequest.getRequestURI(), String.valueOf(p.getId()));
    	
        HttpHeaders headers = new HttpHeaders();
        //RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        //request.getURI();
        headers.add(HttpHeaders.LOCATION,uriBuilder.build().toUriString());
        
        return response;
    }
    
    @RequestMapping(path={"/{id}"}, method = RequestMethod.DELETE)
    public @ResponseBody ResponseDetail<Persona> deletePersona(@PathVariable("id") Integer id) {
    	ResponseDetail<Persona> rD = new ResponseDetail<>();

    	personaService.deletePersona(id);
    	
    	rD.setTotalCount(0);
    	rD.setSuccess(true);
    	return rD;
    }
/*    
    public @ResponseBody ResponseDetail<Persona> deletePersona(HttpServletRequest request, @RequestBody Map<String, Object> body, @PathVariable("id") String idS) {
*/
}