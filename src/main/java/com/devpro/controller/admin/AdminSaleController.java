package com.devpro.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.Sale;
import com.devpro.model.AjaxResponse;
import com.devpro.entities.Product;
import com.devpro.entities.ProductSale;
import com.devpro.repositories.ProductRepo;
import com.devpro.repositories.ProductSaleRepo;
import com.devpro.repositories.SaleRepo;
import com.devpro.services.ProductSaleService;
import com.devpro.services.ProductService;


@Controller
public class AdminSaleController{
	
	@Autowired
	private SaleRepo saleRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductSaleRepo productSaleRepo;
	
	@Autowired
	private ProductSaleService productSaleService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = { "/admin/addSale" }, method = RequestMethod.GET)
	public String saleadd(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		model.addAttribute("sale", new Sale());
		return "back-end/addSale";
	}
	
	@RequestMapping(value = { "/admin/listSale" }, method = RequestMethod.GET)
	public String saveProduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		model.addAttribute("messsage", "");
		String messsage = request.getParameter("add");
		if (messsage != null && messsage.equalsIgnoreCase("success")) {
			model.addAttribute("messsage", "<div class=\"alert alert-success\">"
					+ "  <strong>Success!</strong> Cập nhật thành công." + "</div>");
		}
		model.addAttribute("sale", productSaleService.getSaleAdmin());
		productSaleService.setDiscountActive();
		return "back-end/listSale";
	}
	@RequestMapping(value = {"/admin/addSale"}, method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("sale") Sale sale,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		
		Date d = Calendar.getInstance().getTime();
		sale.setCreatedDate(d);
		String startD = request.getParameter("start_date");
		String nam = startD.substring(0, 4);
		String thang = startD.substring(5, 7);
		String ngay = startD.substring(8, 10);
		startD = ngay + "/" + thang + "/" + nam;
		
		String endD = request.getParameter("end_date");
		String nam1 = endD.substring(0, 4);
		String thang1 = endD.substring(5, 7);
		String ngay1 = endD.substring(8, 10);
		endD = ngay1 + "/" + thang1 + "/" + nam1;
		
		Date datengaybd = new SimpleDateFormat("dd/MM/yyyy").parse(startD);
		Date datengaykt = new SimpleDateFormat("dd/MM/yyyy").parse(endD);
		sale.setStart_date(datengaybd);
		sale.setEnd_date(datengaykt);
		saleRepo.save(sale);
		productSaleService.setDiscountActive();
		return "redirect:/admin/listSale/?add=success";
	}
	
	@RequestMapping(value = { "/admin/sale/{id}" }, method = RequestMethod.GET) 
	 public String saveProduct(@PathVariable("id") Integer id,
		 final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		 Sale sale = saleRepo.getOne(id);
		 String startD = sale.getStart_date().toString().substring(0, 10);
		 model.addAttribute("start_date", startD);
		 String endD = sale.getEnd_date().toString().substring(0, 10);
		 model.addAttribute("end_date", endD);
		 model.addAttribute("sale", sale);
		 productSaleService.setDiscountActive();
		 return "back-end/addSale"; 
	 }
	
	@RequestMapping(value = { "/admin/listProductSale/{id}" }, method = RequestMethod.GET) 
	 public String ProductSale(@PathVariable("id") Integer id,
		 final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		 model.addAttribute("messsage", "");
		 String messsage = request.getParameter("add");
		 if (messsage != null && messsage.equalsIgnoreCase("success")) {
			model.addAttribute("messsage", "<div class=\"alert alert-success\">"
					+ "  <strong>Success!</strong> Cập nhật thành công." + "</div>");
		 } 
		 List<ProductSale> productSale = productSaleService.getProductSaleByIdSale(id);
		 model.addAttribute("productSale", productSale);
		 model.addAttribute("sale", saleRepo.findAll());
		 model.addAttribute("saleid", id);
		 model.addAttribute("saleI", saleRepo.getOne(id));
		 List<Product> p = productService.getAllProduct();
		 for (ProductSale item : productSale) {
			p.remove(item.getProduct());
		}
		 model.addAttribute("product", p);
		 productSaleService.setDiscountActive();
		 return "back-end/listProductSale"; 
	 }
	
	@RequestMapping(value = { "/admin/addProductSale" }, method = RequestMethod.POST)
	public String addProductSale(
			@ModelAttribute("productSale") ProductSale productSale, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Integer productId = Integer.parseInt(request.getParameter("productId"));
		Integer saleId = Integer.parseInt(request.getParameter("saleId"));
		String id = request.getParameter("productSaleId");
		if(id != null) {
			productSale.setId(Integer.parseInt(id));
		}
		String dis = request.getParameter("discountU");
		
		productSale.setProduct(productRepo.getOne(productId));
		productSale.setSale(saleRepo.getOne(saleId));
		if(dis != null) {
			Integer discountU = Integer.parseInt(dis);
			productSale.setDiscount(discountU);
		}
		productSaleService.saveProductSale(productSale);
		productSaleService.setDiscountActiveByProductId(productId);
		productSaleService.setDiscountActive();
		return "redirect:/admin/listProductSale/"+saleId+"?add=success";
	}
	
	@RequestMapping(value = { "/admin/deleteProductSale" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> deleteProductSlae(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response, @RequestBody ProductSale productSale) {
		ProductSale prSaleInDB = productSaleRepo.getOne(productSale.getId());
		productSaleRepo.delete(prSaleInDB);
		productSaleService.setDiscountActive();
		return ResponseEntity.ok(new AjaxResponse(200, "Xoa thanh cong"));
	}
}
