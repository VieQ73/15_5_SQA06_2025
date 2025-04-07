package com.devpro.controller;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.Category;
import com.devpro.entities.News;
import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
import com.devpro.services.CategoryService;
import com.devpro.services.NewsService;
import com.devpro.services.ProductService;
import com.devpro.services.OrderProductService;
import com.devpro.services.ProductSaleService;

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
	private ProductSaleService productSaleService;
	@Autowired
	private NewsService newsService;
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
		
		/*
		 * List<Product> p = productService.getProductSale(null); for (Product item : p)
		 * { Sale k =productService.getDiscount(item.getId());
		 * item.setDiscount(k.getDiscount());
		 * item.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new
		 * BigDecimal(item.getDiscount())))); productRepo.save(item); } List<Product>
		 * prod = productService.getAllProduct(); for (Product item : prod) {
		 * if(item.getDiscount() != 0)
		 * item.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new
		 * BigDecimal(item.getDiscount())).divide(new BigDecimal(100)))); else
		 * item.setPrice_sale(item.getPrice()); productRepo.save(item); }
		 */
		productSaleService.setDiscountActive();
		
		List<Category> categories = categoryService.search(null);
		model.addAttribute("categories", categories);
		
		List<Product> products = productService.searchProductWithCate8(16);
		List<ProductCustom> productCustom1 = new ArrayList<ProductCustom>();
		for (Product item : products) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			productCustom1.add(p);
		}
		model.addAttribute("productCustom1", productCustom1);
		
		List<Product> products1 = productService.searchProductWithCate8(17);
		List<ProductCustom> productCustom2 = new ArrayList<ProductCustom>();
		for (Product item : products1) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			productCustom2.add(p);
		}
		model.addAttribute("productCustom2", productCustom2);
		
		List<Product> products2 = productService.searchProductWithCate8(18);
		List<ProductCustom> productCustom3 = new ArrayList<ProductCustom>();
		for (Product item : products2) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			productCustom3.add(p);
		}
		model.addAttribute("productCustom3", productCustom3);
		
		List<Product> products3 = productService.searchProductWithCate8(19);
		List<ProductCustom> productCustom4 = new ArrayList<ProductCustom>();
		for (Product item : products3) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			productCustom4.add(p);
		}
		model.addAttribute("productCustom4", productCustom4);
		
		List<Product> productSelling = productService.searchPrSelling2(null);
		List<ProductCustom> productCustom5 = new ArrayList<ProductCustom>();
		for (Product item : productSelling) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			productCustom5.add(p);
		}
		model.addAttribute("productSelling", productCustom5);
		List<News> news = newsService.getNewsIndex();
		model.addAttribute("news", news);
		return "front-end/home";
	}
	
	
}
