package es.cnc.suscripciones.front;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import es.cnc.suscripciones.front.dto.DashboardDTO;
import es.cnc.suscripciones.services.dashboard.DashboardService;

@RestController()
@RequestMapping("**/dashboards")
public class DashboardController extends AbstractController<DashboardDTO> {
	@Autowired
	DashboardService dashboardService;
	
    @SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<DashboardDTO> list(@RequestParam("filter") String filter, 
    		@RequestParam("limit") Integer limit,
			@RequestParam("page") Integer page, 
			@RequestParam("start") Integer start) throws JsonParseException, JsonMappingException, IOException {
		FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh = buildFilters(filter);
		FilterBaseDTO<String> fromStr = null;
		FilterBaseDTO<String> toStr = null;
		FilterBaseDTO<LocalDate> from = null;
		FilterBaseDTO<LocalDate> to = null;
		LocalDate aux = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		if (fh.get("fechaDesde")!= null) {
			fromStr = (FilterBaseDTO<String>)fh.get("fechaDesde");
			aux = LocalDate.parse(fromStr.getValue(), formatter);
			from = new FilterBaseDTO<>();
			from.setProperty(fromStr.getProperty());
			from.setValue(aux);
		}
		if (fh.get("fechaHasta")!= null) {
			toStr = (FilterBaseDTO<String>)fh.get("fechaHasta");
			aux = LocalDate.parse(toStr.getValue(), formatter);
			to = new FilterBaseDTO<>();
			to.setProperty(toStr.getProperty());
			to.setValue(aux);
		}
    	return dashboardService.generateEmissionStatistics(from.getValue(), to.getValue());
    }
}