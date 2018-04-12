package es.cnc.suscripciones.front.export.pdf.itext.util;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class ITextPdfViewResolver implements ViewResolver {

	public final static String ITEXT_PDF_CERTIFICATE_VIEW = "ITEXT_PDF_CERTIFICATE_VIEW"; 
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if (ITEXT_PDF_CERTIFICATE_VIEW.equals(viewName)) {
			return new CertificadoiTextPDFView();
		} else {
			return null;
//			throw new RuntimeException("Error en configuración de Resolver for view:" + viewName);
		}
	}

}
