package com.devpro.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ThongKeExcelExporter {
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ThongKe> listThongKe;
     
    public ThongKeExcelExporter(List<ThongKe> listThongKe) {
        this.listThongKe = listThongKe;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("ThongKe");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "STT", style);      
        createCell(row, 1, "Tên sản phẩm", style);       
        createCell(row, 2, "Số lượng", style);    
        createCell(row, 3, "Tổng giá", style);
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }
        else{
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        Integer stt = 0;
        Integer tongSL = 0;
        BigDecimal tongGia = BigDecimal.ZERO;
        for (ThongKe item : listThongKe) {
        	stt++;
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, stt, style);
            createCell(row, columnCount++, item.getProduct().getTitle(), style);
            createCell(row, columnCount++, item.getTongSoLuong(), style);
            createCell(row, columnCount++, item.getTongGia().toString()+" đ", style);
            tongSL += item.getTongSoLuong();
            tongGia = tongGia.add(item.getTongGia());
        }
        createCell(sheet.createRow(listThongKe.size()+1), 0, "", style);
        createCell(sheet.createRow(listThongKe.size()+1), 1, "Tổng:", style);
        createCell(sheet.createRow(listThongKe.size()+1), 2, tongSL.toString(), style);
        createCell(sheet.createRow(listThongKe.size()+1), 3, tongGia.toString()+" đ", style);
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
}
