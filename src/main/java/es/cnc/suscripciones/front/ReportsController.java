package es.cnc.suscripciones.front;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import es.cnc.suscripciones.dto.FilterBaseDTO;
import es.cnc.suscripciones.dto.FilterHolder;
import es.cnc.suscripciones.front.dto.DashboardDTO;
import es.cnc.suscripciones.front.dto.reports.DevolucionesReportDTO;
import es.cnc.suscripciones.front.export.excel.util.ExcelViewResolver;
import es.cnc.suscripciones.front.response.ResponseList;
import es.cnc.suscripciones.services.devolucion.DevolucionService;

@RestController()
@RequestMapping("**/reports")
public class ReportsController extends AbstractController<DashboardDTO> {
	
	@Autowired
	DevolucionService devolucionService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/devoluciones", method = RequestMethod.GET, produces={"application/json"})
    public ResponseList<List<DevolucionesReportDTO>> list(@RequestParam("filter") String filter, 
    		@RequestParam("limit") Integer limit,
			@RequestParam("page") Integer page, 
			@RequestParam("start") Integer start,
			HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		ResponseList<List<DevolucionesReportDTO>> returnContainer = new ResponseList<>();
		FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh = buildFilters(filter);
		FilterBaseDTO<String> fromStr = null;
		FilterBaseDTO<String> toStr = null;
		FilterBaseDTO<LocalDate> from = null;
		FilterBaseDTO<LocalDate> to = null;
		LocalDate aux = null;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (fh.get("anyoDesde")!= null) {
			fromStr = (FilterBaseDTO<String>)fh.get("anyoDesde");
			aux = LocalDate.parse("01/01/"+ fromStr.getValue(), formatter);
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
		}
		List<DevolucionesReportDTO> laLista;
		laLista = devolucionService.generateRefundReport(from.getValue().getYear(), to.getValue().getYear());
		returnContainer.setData(laLista);
		returnContainer.setSuccess(true);
		returnContainer.setTotalCount(returnContainer.getCount());
		return returnContainer;
    }

	/**
	 * 
	 * application/vnd.ms-excel
	 * application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
	 * @param filter
	 * @param limit
	 * @param page
	 * @param start
	 * @param response
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/devoluciones", method = RequestMethod.GET, produces={"application/vnd.ms-excel"})
	public ModelAndView exportExcel(@RequestParam(required=false, name="filter") String filter, 
			@RequestParam(required=false, name="limit") Integer limit,
			@RequestParam(required=false, name="page") Integer page, 
			@RequestParam(required=false, name="start") Integer start,
			HttpServletResponse response, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		
		ModelAndView mav = null;
		
		FilterHolder<? extends Collection<FilterBaseDTO<?>>> fh = buildFilters(filter);
		FilterBaseDTO<String> fromStr = null;
		FilterBaseDTO<String> toStr = null;
		FilterBaseDTO<LocalDate> from = null;
		FilterBaseDTO<LocalDate> to = null;
		LocalDate aux = null;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (fh.get("anyoDesde")!= null) {
			fromStr = (FilterBaseDTO<String>)fh.get("anyoDesde");
			aux = LocalDate.parse("01/01/"+ fromStr.getValue(), formatter);
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
		}
		List<DevolucionesReportDTO> laLista;
		laLista = devolucionService.generateRefundReport(from.getValue().getYear(), to.getValue().getYear());
		
		StringBuffer auxBuffer = new StringBuffer(); 
		if (fromStr != null)
			auxBuffer.append(fromStr.getValue());
		
		if (fromStr != null && toStr != null) {
			auxBuffer.append("-");
		}
		
		if (toStr != null) {
			auxBuffer.append(toStr.getValue());
			auxBuffer.append("-");
		}
		
		auxBuffer.append("devoluciones.xls");
		
		mav = new ModelAndView(ExcelViewResolver.EXCEL_VIEW, "exportDevolucionesExcel", laLista);
		mav.addObject("fileName", auxBuffer.toString());
		return mav;
	}

}