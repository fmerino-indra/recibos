package es.cnc.suscripciones.front.export.excel.util;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class ExcelViewResolver implements ViewResolver {

	public final static String EXCEL_VIEW = "EXCEL_VIEW"; 
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if (EXCEL_VIEW.equals(viewName)) {
			return new PoiExcelView();
		} else {
			return null;
//			throw new RuntimeException("Error en configuración de Resolver for view:" + viewName);
		}
	}

}
