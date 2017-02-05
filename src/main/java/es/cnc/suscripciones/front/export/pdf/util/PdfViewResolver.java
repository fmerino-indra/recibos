package es.cnc.suscripciones.front.export.pdf.util;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class PdfViewResolver implements ViewResolver {

	public final static String CERTIFICATE_VIEW = "CERTIFICATE_VIEW"; 
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if (CERTIFICATE_VIEW.equals(viewName)) {
			return new CertificadoPDFView();
		} else {
			return null;
//			throw new RuntimeException("Error en configuración de Resolver for view:" + viewName);
		}
	}

}
