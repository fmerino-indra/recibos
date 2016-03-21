package es.cnc.suscripciones.front;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.cnc.suscripciones.services.emision.EmisionService;

@RestController()
@RequestMapping("{contextPath}/emisiones")
public class EmisionController {
	@Autowired
	EmisionService emisionService;
	
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/devolver" })
    public void devolver(@RequestBody String body, @RequestParam("id") ArrayList<Integer> ids) {
    	System.out.println(ids);
    	emisionService.devolver(ids);
    }
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/anular" })
    public void anular(@RequestBody String body, @RequestParam("id") ArrayList<Integer> ids) {
    	System.out.println(ids);
    	emisionService.anular(ids);
    }
}