package es.cnc.suscripciones.front;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import es.cnc.suscripciones.front.dto.reports.DashboardSummaryDTO;
import es.cnc.suscripciones.services.dashboard.DashboardService;

@RestController()
@RequestMapping("**/dashboards")
public class DashboardController extends AbstractController<DashboardDTO> {
	@Autowired
	DashboardService dashboardService;
	
    @SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<DashboardSummaryDTO> list(@RequestParam("filter") String filter, 
    		@RequestParam("limit") Integer limit,
			@RequestParam("page") Integer page, 
			@RequestParam("start") Integer start) throws JsonParseException, JsonMappingException, IOException {
		FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh = buildFilters(filter);
		FilterBaseDTO<String> fromStr = null;
		FilterBaseDTO<String> toStr = null;
		FilterBaseDTO<LocalDate> from = null;
		FilterBaseDTO<LocalDate> to = null;
		LocalDate aux = null;

		Boolean mensuales = null;
		FilterBaseDTO<String> mensualesStr = null;
		
		Boolean trimestrales = null;
		FilterBaseDTO<String> trimestralesStr = null;
		
		Boolean semestrales = null;
		FilterBaseDTO<String> semestralesStr = null;
		
		Boolean anuales = null;
		FilterBaseDTO<String> anualesStr = null;
		
//		FilterBaseDTO<Boolean> agrupadosBool = null;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (fh.get("anyoDesde")!= null) {
			fromStr = (FilterBaseDTO<String>)fh.get("anyoDesde");
			aux = LocalDate.parse("01/01/"+ fromStr.getValue(), formatter);
			from = new FilterBaseDTO<>();
			from.setProperty(fromStr.getProperty());
			from.setValue(aux);
		} else if (fh.get("fechaDesde")!= null) {
			fromStr = (FilterBaseDTO<String>)fh.get("fechaDesde");
			aux = LocalDate.parse(fromStr.getValue(), formatter);
			from = new FilterBaseDTO<>();
			from.setProperty(fromStr.getProperty());
			from.setValue(aux);
		}
		
		if (fh.get("anyoHasta")!= null) {
			toStr = (FilterBaseDTO<String>)fh.get("anyoHasta");
			aux = LocalDate.parse("31/12/"+ toStr.getValue(), formatter);
			to = new FilterBaseDTO<>();
			to.setProperty(toStr.getProperty());
			to.setValue(aux);
		} else if (fh.get("fechaHasta")!= null) {
			toStr = (FilterBaseDTO<String>)fh.get("fechaHasta");
			aux = LocalDate.parse(toStr.getValue(), formatter);
			to = new FilterBaseDTO<>();
			to.setProperty(toStr.getProperty());
			to.setValue(aux);
		}
		if (fh.get("mensuales")!= null) {
			mensualesStr = (FilterBaseDTO<String>)fh.get("mensuales");
			mensuales = new Boolean(mensualesStr.getValue().equals("on"));
		} else {
			mensuales = new Boolean(false);
		}
		if (fh.get("trimestrales")!= null) {
			trimestralesStr = (FilterBaseDTO<String>)fh.get("trimestrales");
			trimestrales = new Boolean(trimestralesStr.getValue().equals("on"));
		} else {
			trimestrales = new Boolean(false);
		}
		if (fh.get("semestrales")!= null) {
			semestralesStr = (FilterBaseDTO<String>)fh.get("semestrales");
			semestrales = new Boolean(semestralesStr.getValue().equals("on"));
		} else {
			semestrales = new Boolean(false);
		}
		if (fh.get("anuales")!= null) {
			anualesStr = (FilterBaseDTO<String>)fh.get("anuales");
			anuales = new Boolean(anualesStr.getValue().equals("on"));
		} else {
			anuales = new Boolean(false);
		}
		
		
		
		
    	return dashboardService.generateEmissionStatistics(from.getValue(), to.getValue(),mensuales,trimestrales, semestrales, anuales);
    }
}