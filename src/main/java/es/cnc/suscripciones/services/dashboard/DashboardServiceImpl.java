package es.cnc.suscripciones.services.dashboard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.dao.spring.OtrosRepository;
import es.cnc.suscripciones.front.dto.DashboardDTO;
import es.cnc.suscripciones.front.dto.reports.DashboardSummaryDTO;

@Component("dashboardService")
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private OtrosRepository otrosRepository;

	Logger logger;

	public DashboardServiceImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public List<DashboardSummaryDTO> generateEmissionStatistics(LocalDate from, LocalDate to) {
		return generateEmissionStatistics(from, to, true, true, true, true);
	}
	
	/**
	 * @param listDto
	 * @return the list DTO
	 */
	private List<DashboardSummaryDTO> toDashboardSummaryDTO(List<DashboardDTO> listDto) {
		List<DashboardSummaryDTO> laLista=new ArrayList<>();
		Map<String, List<DashboardDTO>> mapa = new LinkedHashMap<>();
		String auxKey;
		DashboardSummaryDTO mesDto;
		List<DashboardDTO> aux;
		for (DashboardDTO dto:listDto) {
			auxKey = DashboardSummaryDTO.calcKey(dto.getAnyo(),dto.getMes());
			if (mapa.containsKey(auxKey)) {
				aux = mapa.get(auxKey);
			} else {
				aux = new ArrayList<>();
				mapa.put(auxKey, aux);
			}				
			aux.add(dto);
		}
		
		for (String key : mapa.keySet()) {
			aux = mapa.get(key);
			mesDto = new DashboardSummaryDTO(DashboardSummaryDTO.parseAnyo(key), DashboardSummaryDTO.parseMes(key));
			for (DashboardDTO dto : aux) {
				mesDto.addDto(dto);
			}
			laLista.add(mesDto);
		}
		return laLista;
	}

	@Override
	public List<DashboardSummaryDTO> generateEmissionStatistics(LocalDate from, LocalDate to, Boolean mensuales,
			Boolean trimestrales, Boolean semestrales, Boolean anuales) {
		List<DashboardDTO> lista = null;
		List<DashboardSummaryDTO> retorno = null;
		Integer anyoFrom = null;
		Integer anyoTo = null;
		Integer mesFrom = null;
		Integer mesTo = null;
		
		anyoFrom = from.getYear();
		anyoTo = to.getYear();
		mesFrom = from.getMonthValue();
		mesTo = to.getMonthValue();
//		if (agrupados)
//			lista = otrosRepository.findEmissionStatisticsSummary(anyoFrom, anyoTo, mesFrom, mesTo);
//		else {
//			Sort sort = new Sort(Sort.Direction.ASC, "idCabecera.anyo", "idCabecera.codigoMes", "idCabecera.periodo");
//			lista = otrosRepository.findEmissionStatisticsSummaryNoGroupSort(anyoFrom, anyoTo, mesFrom, mesTo,sort);
//		}
		Sort sort = new Sort(Sort.Direction.ASC, "idCabecera.anyo", "idCabecera.codigoMes", "idCabecera.periodo");
		
		List<String> periodos = new ArrayList<>();
		if (mensuales)
			periodos.add("M");
		if (trimestrales)
			periodos.add("T");
		if (semestrales)
			periodos.add("S");
		if (anuales)
			periodos.add("A");
		
		lista = otrosRepository.findEmissionStatisticsSummaryNoGroupSortPeriods(anyoFrom, anyoTo, mesFrom, mesTo,sort,periodos);
		retorno = toDashboardSummaryDTO(lista);
		return retorno;
	}
	

}
