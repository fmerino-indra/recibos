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

import es.cnc.suscripciones.domain.Persona;
import es.cnc.suscripciones.services.persona.PersonaService;

@RestController()
@RequestMapping("{contextPath}/personas")
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
			@RequestParam("start") Integer start) {
		ResponseList<List<Persona>> returnContainer = new ResponseList<>();

		Page<Persona> pagina = personaService.findPersonas(page-1, start, limit);
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
    @RequestMapping(method = RequestMethod.POST, produces = "application/json") // , path = { "/anular" }
    public ResponseDetail<Persona> crear(@RequestBody String body, @RequestParam("id") ArrayList<Integer> ids) {
    	System.out.println(body);
    	
    	ResponseDetail<Persona> response = new ResponseDetail<>();
    	response.setData(new Persona());
    	return response;
    }
/*    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> UsrUsuarioController.createFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        UsrUsuario usrUsuario = UsrUsuario.fromJsonToUsrUsuario(json);
        usrUsuarioService.saveUsrUsuario(usrUsuario);
        RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        headers.add("Location",uriBuilder.path(a.value()[0]+"/"+usrUsuario.getId()).build().toUriString());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
*/
}