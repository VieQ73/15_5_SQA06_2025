package com.devpro.controller.admin;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.devpro.entities.Gift;
import com.devpro.model.ThongKe;
import com.devpro.model.ThongKeExcelExporter;
import com.devpro.repositories.GiftRepo;
import com.devpro.services.GiftService;
import com.devpro.services.ThongKeService;

@Controller
public class AdminThongkeController {

	@Autowired
	private ThongKeService thongKeService;
	@Autowired
	private GiftRepo giftRepo;
	
	@RequestMapping(value = { "/admin/thongke" }, method = RequestMethod.GET)
	public String saleadd(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
		return "back-end/thongke";
	}


	@RequestMapping(value = { "/admin/thongke/search" }, method = RequestMethod.POST) 
	 public String saveProduct(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String startD = request.getParameter("ngayBD");
		String endD = request.getParameter("ngayKT");
		
		model.addAttribute("bd", startD);
		model.addAttribute("kt", endD);
		
		String nam = startD.substring(0, 4);
		String thang = startD.substring(5, 7);
		String ngay = startD.substring(8, 10);
		startD = ngay + "/" + thang + "/" + nam;
		
		String nam1 = endD.substring(0, 4);
		String thang1 = endD.substring(5, 7);
		String ngay1 = endD.substring(8, 10);
		endD = ngay1 + "/" + thang1 + "/" + nam1;
		
		Date datengaybd = new SimpleDateFormat("dd/MM/yyyy").parse(startD);
		Date datengaykt = new SimpleDateFormat("dd/MM/yyyy").parse(endD);
		Long ngayBD = datengaybd.getTime();
		Long ngayKT = datengaykt.getTime();
		List<ThongKe> listTK = thongKeService.thongKeTheoTime(ngayBD, ngayKT);
		
		Integer sl = 0;
		BigDecimal g = BigDecimal.ZERO;
		for (ThongKe item : listTK) {
			sl+= item.getTongSoLuong();
			g = g.add(item.getTongGia());
		}
		model.addAttribute("tongSL", sl);
		model.addAttribute("tongG", g);
		model.addAttribute("thongke", listTK);
		 return "back-end/thongkeList"; 
	 }
	
	@GetMapping(value = { "/admin/thongke/export/{ngayBD}/{ngayKT}" }) 
    public void exportToExcel(final HttpServletRequest request, HttpServletResponse response, @PathVariable("ngayBD") String ngayBD,
    		@PathVariable("ngayKT") String ngayKT) throws IOException, ParseException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        String startD = ngayBD;
		String endD = ngayKT;
		
		
		String nam = startD.substring(0, 4);
		String thang = startD.substring(5, 7);
		String ngay = startD.substring(8, 10);
		startD = ngay + "/" + thang + "/" + nam;
		
		String nam1 = endD.substring(0, 4);
		String thang1 = endD.substring(5, 7);
		String ngay1 = endD.substring(8, 10);
		endD = ngay1 + "/" + thang1 + "/" + nam1;
		
		Date datengaybd = new SimpleDateFormat("dd/MM/yyyy").parse(startD);
		Date datengaykt = new SimpleDateFormat("dd/MM/yyyy").parse(endD);
		Long bd = datengaybd.getTime();
		Long kt = datengaykt.getTime();
		List<ThongKe> listTK = thongKeService.thongKeTheoTime(bd, kt);
        ThongKeExcelExporter excelExporter = new ThongKeExcelExporter(listTK);
         
        excelExporter.export(response);
    } 
}
