package es.cnc.suscripciones.services.resumen;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.cnc.suscripciones.domain.Cabeceraemisiones;
import es.cnc.suscripciones.domain.Meses;
import es.cnc.suscripciones.domain.Periodo;
import es.cnc.suscripciones.domain.dao.spring.MesesRepository;
import es.cnc.suscripciones.front.dto.ResumenDTO;
import es.cnc.suscripciones.services.cabecera.CabeceraService;
import es.cnc.util.app.ConstantsData;

@Component("resumenService")
public class ResumenServiceImpl implements ResumenService {

	@Autowired
	MesesRepository mesesRepository;

	@Autowired
	CabeceraService cabeceraService;

	@Override
	public List<ResumenDTO> getResumen(Integer anyo) {
		List<ResumenDTO> retorno = new ArrayList<ResumenDTO>();
		List<Meses> meses = mesesRepository.findMesesWithPeriodos();
		List<Cabeceraemisiones> cabeceras = cabeceraService.findCabecerasByYear(anyo);
		ResumenDTO aux = null;
		Cabeceraemisiones ce = null;
		for (Meses mes : meses) {
			aux = new ResumenDTO();
			retorno.add(aux);
			aux.setCodigoMes(mes.getId());
			aux.setMes(mes.getMes());

			for (Periodo p : mes.getPeriodoes()) {
				ce = searchByAnyoMes(anyo,mes.getId(), p.getCodigo(), cabeceras);
				if (ce != null) {
					if (p.getCodigo().equals(ConstantsData.MENSUAL)) {
						aux.setImporteMes(ce.getImporte());
						aux.setDevueltoMes(ce.getImporteDevuelto());
						aux.setFechaMes(ce.getFechaEmision());
					} else if (p.getCodigo().equals(ConstantsData.TRIMESTRAL)) {
						aux.setImporteTrimestre(ce.getImporte());
						aux.setDevueltoTrimestre(ce.getImporteDevuelto());
						aux.setFechaTrimestre(ce.getFechaEmision());					
					} else if (p.getCodigo().equals(ConstantsData.SEMESTRAL)) {
						aux.setImporteSemestre(ce.getImporte());
						aux.setDevueltoSemestre(ce.getImporteDevuelto());
						aux.setFechaSemestre(ce.getFechaEmision());					
					} else if (p.getCodigo().equals(ConstantsData.ANUAL)) {
						aux.setImporteAnyo(ce.getImporte());
						aux.setDevueltoAnyo(ce.getImporteDevuelto());
						aux.setFechaAnyo(ce.getFechaEmision());					
					}
				}
			}
		}
		return retorno;

	}

	private Cabeceraemisiones searchByAnyoMes(Integer anyo, Integer mes, String tipo, List<Cabeceraemisiones> lista) {
		Cabeceraemisiones retorno = null;
		for (Cabeceraemisiones ce : lista) {
			if (ce.getAnyo().equals(anyo) &&
				ce.getCodigoMes().equals(mes) &&
				ce.getPeriodo().equals(tipo) &&
				!ce.getEmisionManual()) {
				retorno = ce;
				break;
			}
		}
		return retorno;
	}
}
