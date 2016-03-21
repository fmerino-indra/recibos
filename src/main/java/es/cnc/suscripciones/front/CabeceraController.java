package es.cnc.suscripciones.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Emision;
import es.cnc.suscripciones.front.dto.CabeceraDTO;
import es.cnc.suscripciones.front.dto.EmisionDTO;
import es.cnc.suscripciones.services.cabecera.CabeceraService;

@RestController()
@RequestMapping("{contextPath}/cabeceras")
public class CabeceraController {
	@Autowired
	CabeceraService cabeceraService;

	@RequestMapping(path = { "/{id}" }, method = RequestMethod.GET)
	public ResponseAbstract<CabeceraDTO> detail(@PathVariable Integer id) {
		Cabeceraemisiones param = new Cabeceraemisiones();
		param.setId(id);
//		Cabeceraemisiones ce = cabeceraService.findCabeceraDetail(param);
		Cabeceraemisiones ce = cabeceraService.findCabeceraDetailSimple(param);
		List<Emision> emissionList = cabeceraService.findEmissionList(ce);
		
		CabeceraDTO cab = buildCabeceraDTO(ce, emissionList);
		
//		ResponseDetail<Cabeceraemisiones> returnValue = new ResponseDetail<>();
		ResponseAbstract<CabeceraDTO> returnValue = new ResponseAbstract<>();
		returnValue.setData(cab);
		returnValue.setSuccess(true);
		returnValue.setTotalCount(1l);
		return returnValue;
	}

	private CabeceraDTO buildCabeceraDTO(Cabeceraemisiones ce, List<Emision> emissionList) {
		CabeceraDTO cab =new CabeceraDTO();
		cab.setId(ce.getId());
		cab.setCodigoMes(ce.getCodigoMes());
		cab.setConcepto(ce.getConcepto());
		cab.setEmisionManual(ce.getEmisionManual());
		cab.setFechaEmision(ce.getFechaEmision());
		cab.setFechaEnvio(ce.getFechaEnvio());
		cab.setImporte(ce.getImporte());
		cab.setImporteDevuelto(ce.getImporteDevuelto());
		cab.setPeriodo(ce.getPeriodo());
		List<EmisionDTO> dtoList = new ArrayList<>(emissionList.size());
		cab.setImporte(0d);
		cab.setImporteDevuelto(0d);
		for (Emision e : emissionList) {
			dtoList.add(buildEmisionDTO(e, cab));
			if (e.getDevuelto()) {
				cab.setImporteDevuelto(cab.getImporteDevuelto()+e.getImporte());
			} else
				cab.setImporte(cab.getImporte()+e.getImporte());
		}
		cab.setEmisiones(dtoList);
		
		return cab;
	}
	
	private EmisionDTO buildEmisionDTO(Emision e, CabeceraDTO cab) {
		EmisionDTO dto = new EmisionDTO();
		dto.setId(e.getId());
		dto.setDevuelto(e.getDevuelto());
		dto.setDivisa(e.getDivisa());
		dto.setIdTitular(e.getIdSuscripcion().getIdSuscripcion().getPersona().getId());
		dto.setTitular(e.getIdSuscripcion().getIdSuscripcion().getPersona().getNombre());
		dto.setImporte(e.getImporte());
		dto.setPrimero(e.getPrimero());
		dto.setUltimo(e.getUltimo());
		dto.setReenviado(e.getReenviado());
		dto.setCabeceraDTO(cab);
		return dto;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", params = { "page", "start", "limit", "filter"})
	public ResponseList<List<Cabeceraemisiones>> listPag(@RequestParam("filter") String filter, @RequestParam("limit") String limit,
			@RequestParam("page") String page, @RequestParam("start") String start) {
		return listPag(filter, limit, page, start, null);
	}

	// TODO Apply filters!!!
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", params = { "page", "start", "limit", "filter", "sort" })
	public ResponseList<List<Cabeceraemisiones>> listPag(@RequestParam("filter") String filter, @RequestParam("limit") String limit,
			@RequestParam("page") String page, @RequestParam("start") String start, @RequestParam("sort") String sort) {
		ResponseList<List<Cabeceraemisiones>> returnContainer = new ResponseList<>();
		Integer iPage = parseToInteger(page);
		Integer iStart = parseToInteger(start);
		Integer iLimit = parseToInteger(limit);
		Page<Cabeceraemisiones> pagina = cabeceraService.findCabecerasDesc(iPage-1, iStart, iLimit);
		returnContainer.setData(toList(pagina));
		returnContainer.setTotalCount(pagina.getTotalElements());
		returnContainer.setSuccess(true);
		return returnContainer;
	}

	private List<Cabeceraemisiones> toList(Page<Cabeceraemisiones> page) {
		List<Cabeceraemisiones> returnValue = new ArrayList<Cabeceraemisiones>(page.getSize());
		for (Cabeceraemisiones ce:page) {
			returnValue.add(ce);
		}
		return returnValue;
	}
	
	private Integer parseToInteger(String cadena) {
		Integer value = 0;
		if (cadena != null) {
			try {
				value =Integer.valueOf(cadena);
			} catch (NumberFormatException nfe) {
				value = 0;
			}
		}
		return value;
	}
	/*
	 * @RequestParam("filter") String filter,
	 * 
	 * @RequestParam("limit") String limit,
	 * 
	 * @RequestParam("page") String page,
	 * 
	 * @RequestParam("start") String start,
	 * 
	 * @RequestParam("sort") String sort) {
	 * 
	 */

	@RequestMapping(path = { "/{id}/refunded" }, method = RequestMethod.GET)
	public ResponseAbstract<CabeceraDTO> detailRefunded(@PathVariable Integer id) {
		Cabeceraemisiones param = new Cabeceraemisiones();
		param.setId(id);
//		Cabeceraemisiones ce = cabeceraService.findCabeceraDetail(param);
		Cabeceraemisiones ce = cabeceraService.findCabeceraDetailSimple(param);
		List<Emision> emissionList = cabeceraService.findRefundedEmissionList(ce);
		
		CabeceraDTO cab = buildCabeceraDTO(ce, emissionList);
		
//		ResponseDetail<Cabeceraemisiones> returnValue = new ResponseDetail<>();
		ResponseAbstract<CabeceraDTO> returnValue = new ResponseAbstract<>();
		returnValue.setData(cab);
		returnValue.setSuccess(true);
		returnValue.setTotalCount(1l);
		return returnValue;
	}

}

