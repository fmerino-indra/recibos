package es.cnc.suscripciones.front.export.pdf.itext.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import es.cnc.suscripciones.front.dto.CertificadoDTO;

public class PDFBuilder {
    public static byte[] renderMergedOutputModel(CertificadoDTO dto) throws Exception {
        // IE workaround: write into byte array first.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
 
        // Apply preferences and build metadata.
        Document document = newDocument();
        PdfWriter writer = newWriter(document, baos);
        prepareWriter(writer);
 
        // Build PDF document.
        document.open();
        buildPdfDocument(document, writer);
        document.close();
 
        FileOutputStream fos = new FileOutputStream("prueba.pdf");
        fos.write(baos.toByteArray());
        fos.close();
        
        // return document.
        return baos.toByteArray();
    }
 
    private static Document newDocument() {
        return new Document(PageSize.A4);
    }
     
    private static PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
        return PdfWriter.getInstance(document, os);
    }
     
    private static void prepareWriter(PdfWriter writer)
            throws DocumentException {
 
        writer.setViewerPreferences(getViewerPreferences());
    }
     
    private static int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }
    private static void buildPdfDocument(Document doc,
            PdfWriter writer)
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
