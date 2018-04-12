package es.cnc.suscripciones.front.export.pdf.jasper.util;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class JasperPdfViewResolver implements ViewResolver {

	public final static String JASPER_PDF_CERTIFICATE_VIEW = "JASPER_PDF_CERTIFICATE_VIEW"; 
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if (JASPER_PDF_CERTIFICATE_VIEW.equals(viewName)) {
			return new CertificadoJasperPDFView();
		} else {
			return null;
//			throw new RuntimeException("Error en configuración de Resolver for view:" + viewName);
		}
	}

}
