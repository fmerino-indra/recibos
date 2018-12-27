package es.cnc.suscripciones.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("**/test")
public class TestController {
    // Body siempre a nulo
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/test1" }, params={"anyo", "codigoMes"})
    public void test1(HttpEntity<?> body) {
    	System.out.println(body);
    }

    // Body siempre a nulo
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/test2" }, params={"anyo", "codigoMes"})
    public void test2(@RequestParam Integer anyo, @RequestParam Integer codigoMes, HttpEntity<?> httpEntity) {
    	System.out.println(anyo);
    	System.out.println(codigoMes);
    	System.out.println(httpEntity);
    }

    // Falla, no consigo que construya un body
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = { "/test3" }, params={"codigoMes"})
    public void test3(@RequestBody Integer codigoMes, HttpEntity<?> httpEntity) {
    	System.out.println(codigoMes);
    	System.out.println(httpEntity);
    }

    @RequestMapping(path={"/**"})
    public void defaultMethod( HttpServletRequest request) {
    	System.out.println(request);
    }
}