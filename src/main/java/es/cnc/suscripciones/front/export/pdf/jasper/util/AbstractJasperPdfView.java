package es.cnc.suscripciones.front.export.pdf.jasper.util;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public abstract class AbstractJasperPdfView extends AbstractView {
	 

	private final static String s = "%s; filename=\"%s\"";

    public AbstractJasperPdfView() {
        setContentType("application/pdf");
    }
 
    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }
         
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String fileName=null;
        // IE workaround: write into byte array first.
        ByteArrayOutputStream baos = null;//createTemporaryOutputStream();
        baos = buildPdfDocument(model, request, response);
        
        fileName=(String)model.get("fileName");
        if (fileName == null)
        	fileName = "Certificado.pdf";
//        FileOutputStream fos = new FileOutputStream("prueba.pdf");
//        fos.write(baos.toByteArray());
//        fos.close();
        
        // Headers
        setHeaders(response, fileName, true);
        // Flush to HTTP response.
        writeToResponse(response, baos);
    }
 
    protected void setHeaders(HttpServletResponse response, String fileName, boolean inline) {
    	String disposition;
    	if (inline)
    		disposition = setInlineDisposition(fileName);
    	else
    		disposition = setAttachmentDisposition(fileName);
        response.setHeader("Content-Disposition", disposition);

	}
    
    private String setInlineDisposition(String fileName) {
    	return String.format(s, "inline", fileName);
    }

    private String setAttachmentDisposition(String fileName) {
    	return String.format(s, "attachment", fileName);
    }

    protected abstract ByteArrayOutputStream buildPdfDocument(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception;

}
