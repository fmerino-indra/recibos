package es.cnc.suscripciones.front.export.excel.util;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import es.cnc.suscripciones.front.dto.reports.DevolucionesReportDTO;

public class PoiExcelView extends AbstractXlsView {
	 
    public PoiExcelView() {
    	super();
//        setContentType("application/");
    }
 
    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }
 
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
    	String fileName;
    	fileName = (String)model.get("fileName");
    	 // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
//        response.setHeader("Content-Disposition", "inline");

    	
    	@SuppressWarnings("unchecked")
		List<DevolucionesReportDTO> laLista = (List<DevolucionesReportDTO>) model.get("exportDevolucionesExcel");
		//create a wordsheet
		Sheet sheet = workbook.createSheet("Informe detallado de devoluciones");
		Row row;
		int cell=0;
		Row header = sheet.createRow(0);
		header.createCell(cell++).setCellValue("Id");
		header.createCell(cell++).setCellValue("Año");
		header.createCell(cell++).setCellValue("Mes");
		header.createCell(cell++).setCellValue("Mes");
		header.createCell(cell++).setCellValue("Periodo");
		header.createCell(cell++).setCellValue("Importe");
		header.createCell(cell++).setCellValue("Nombre");
		header.createCell(cell++).setCellValue("Teléfono");
		header.createCell(cell++).setCellValue("Móvil");
		header.createCell(cell++).setCellValue("Motivo");
		header.createCell(cell++).setCellValue("Motivo");
		

		int rowNum = 1;
		for (DevolucionesReportDTO dto : laLista) {
			cell=0;
			//create the row data
			row = sheet.createRow(rowNum++);
			row.createCell(cell++).setCellValue(dto.getId());
			row.createCell(cell++).setCellValue(dto.getAnyo());
			row.createCell(cell++).setCellValue(dto.getCodigoMes());
			row.createCell(cell++).setCellValue(dto.getDescMes());
			row.createCell(cell++).setCellValue(dto.getPeriodo());
			row.createCell(cell++).setCellValue(dto.getImporte());
			row.createCell(cell++).setCellValue(dto.getNombrePersona());
			row.createCell(cell++).setCellValue(dto.getTelefono());
			row.createCell(cell++).setCellValue(dto.getMovil());
			row.createCell(cell++).setCellValue(dto.getReasonCode());
			row.createCell(cell++).setCellValue(dto.getReasonDescription());
        }
//    	saveFile(workbook, fileName);
    }
    
    private static void saveFile(Workbook workbook, String fileName) throws IOException {
    	FileOutputStream fos;
    	
    	fos = new FileOutputStream(fileName+System.currentTimeMillis()+".xls");
    	workbook.write(fos);
    	fos.close();
    	
    }
}
