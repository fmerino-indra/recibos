package es.cnc.suscripciones.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.cnc.suscripciones.domain.Periodo;
import es.cnc.suscripciones.domain.dao.spring.PeriodoRepository;
import es.cnc.suscripciones.services.emision.EmisionService;

@RestController()
@RequestMapping("{contextPath}/periodos")
public class PeriodoController {
	@Autowired
	EmisionService emisionService;
	
	@Autowired
	PeriodoRepository periodoRepository;
	
    @RequestMapping(path={"/{id}"}, method = RequestMethod.GET)
    public Periodo detail(@PathVariable String id) {
        return periodoRepository.findOne(id);
    }
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Periodo> list() {
    	return periodoRepository.findAll();
//    	return emisionService.findMascara();
    }
}