package com.devpro.controller;



import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.Category;
import com.devpro.entities.Product;
import com.devpro.entities.Sale;
import com.devpro.repositories.ProductRepo;
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
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductRepo productRepo;
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
		
		List<Product> p = productService.getProductSale(null);
		for (Product item : p) {
			Sale k =productService.getDiscount(item.getId());
			item.setDiscount(k.getDiscount());
			item.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(item.getDiscount()))));
			productRepo.save(item);
		}
		List<Product> prod = productService.getAllProduct();
		for (Product item : prod) {
			if(item.getDiscount() != 0)
				item.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(item.getDiscount())).divide(new BigDecimal(100))));
			else
				item.setPrice_sale(item.getPrice());
			productRepo.save(item);
		}
		
		List<Category> categories = categoryService.search(null);
		model.addAttribute("categories", categories);
		
		List<Product> products = productService.searchProductWithCate8(16);
		
		model.addAttribute("products", products);
		List<Product> products1 = productService.searchProductWithCate8(17);
		
		model.addAttribute("products2", products1);
		List<Product> products2 = productService.searchProductWithCate8(18);
		
		model.addAttribute("products3", products2);
		List<Product> products3 = productService.searchProductWithCate8(19);
		
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
