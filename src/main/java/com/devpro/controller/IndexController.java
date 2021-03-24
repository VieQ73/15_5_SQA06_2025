package com.devpro.controller;



import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.Category;
import com.devpro.entities.Product;
import com.devpro.model.AjaxResponse;
import com.devpro.repositories.CategoryRepo;
import com.devpro.services.CategoryService;
import com.devpro.services.ProductService;

/**
 * 
 * @author admin
 *
 */
@Controller // -> tạo ra 1 bean tên webConf và được spring-container quản lí.
			// -> báo module web mapping request cho controller này.
public class IndexController {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	/**
	 * 
	 * @param model: trung gian trao đổi thông tin giữa Controller và View(Jsp)
	 * @param request: Chứa các thông tin Trình Duyệt truy vấn thông qua mẫu: ?abc=xyz
	 * @param response: Dữ liệu trả về Trình Duyệt.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String index(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		
		List<Category> categories = categoryService.search(null);
		model.addAttribute("categories", categories);
		List<Product> products = productService.searchProductWithCate8(16);
		for (Product item : products) {
			BigDecimal gia = item.getPrice();
			if(item.getSaleoff()!=0)
			{
				gia = gia.subtract(gia.multiply(new BigDecimal(item.getSaleoff()).divide(new BigDecimal(100))));
			}
			item.setPrice(gia);
		}
		model.addAttribute("products", products);
		List<Product> products1 = productService.searchProductWithCate8(17);
		for (Product item : products1) {
			BigDecimal gia = item.getPrice();
			if(item.getSaleoff()!=0)
			{
				gia = gia.subtract(gia.multiply(new BigDecimal(item.getSaleoff()).divide(new BigDecimal(100))));
			}
			item.setPrice(gia);
		}
		model.addAttribute("products2", products1);
		List<Product> products2 = productService.searchProductWithCate8(18);
		for (Product item : products2) {
			BigDecimal gia = item.getPrice();
			if(item.getSaleoff()!=0)
			{
				gia = gia.subtract(gia.multiply(new BigDecimal(item.getSaleoff()).divide(new BigDecimal(100))));
			}
			item.setPrice(gia);
		}
		model.addAttribute("products3", products2);
		List<Product> products3 = productService.searchProductWithCate8(19);
		for (Product item : products3) {
			BigDecimal gia = item.getPrice();
			if(item.getSaleoff()!=0)
			{
				gia = gia.subtract(gia.multiply(new BigDecimal(item.getSaleoff()).divide(new BigDecimal(100))));
			}
			item.setPrice(gia);
		}
		model.addAttribute("products4", products3);
		model.addAttribute("productSelling", productService.searchPrSelling2(null));
		return "front-end/home";
	}
	

	
	@RequestMapping(value = { "/huongdanmuahang" }, method = RequestMethod.GET)
	public String hdmh(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		List<Category> categories = categoryService.search(null);
		model.addAttribute("categories", categories);
		return "front-end/huongdanmuahang";
	}
}
