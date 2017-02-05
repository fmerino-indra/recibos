package es.cnc.suscripciones.front.export.pdf.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CertificadoPDFView extends AbstractITextPdfView {
	 
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
//        ResponseList<List<CertificadoDTO>> certificateList = null;
        
        doc.add(new Paragraph("Recommended books for Spring framework"));
         
        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.WHITE);
         
//
//        for (CertificadoDTO dto : certificateList.getData()) {
//        	//dto
//        }
         
        //doc.add();
    }
}
