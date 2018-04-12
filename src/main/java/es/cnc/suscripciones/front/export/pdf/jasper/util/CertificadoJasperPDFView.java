package es.cnc.suscripciones.front.export.pdf.jasper.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.cnc.suscripciones.front.dto.CertificadoDTO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class CertificadoJasperPDFView extends AbstractJasperPdfView {
	 
    @Override
    @SuppressWarnings({ "unused", "unchecked" })
	protected ByteArrayOutputStream buildPdfDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
    {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		JasperReport jr = null;
		JasperPrint jp = null; 
		JRDataSource ds = new JREmptyDataSource();

    	CertificadoDTO dto = null;
		Map<String, Object> parameters = null;

		dto = (CertificadoDTO)model.get("dto");
		parameters = (Map<String, Object>) model.get("parameters");
		
		try {
			is = getClass().getResourceAsStream("/withholding-v3.jasper");
			jr = (JasperReport)JRLoader.loadObject(is);
			jp = JasperFillManager.fillReport(jr, parameters, ds);
			
			JasperExportManager.exportReportToPdfStream(jp, baos);
		} catch (JRException e) {
			e.printStackTrace();
		}

		return baos;
		
//		HttpHeaders headers = new HttpHeaders();
		
//		headers.setContentType(MediaType.parseMediaType("application/pdf"));
//		headers.setContentDispositionFormData("attachment", "CertificadoDonaciones.pdf");
//		headers.setContentLength(pdf.length);
//		
//		String base64 = null;
//		base64 = Base64.getMimeEncoder().encodeToString(pdf);
//		headers.setContentLength(base64.length());
    	
    }
}
