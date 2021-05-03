package com.devpro.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
import com.devpro.model.ProductSearch;
import com.devpro.repositories.ProductRepo;
import com.devpro.services.ProductService;
import com.devpro.services.OrderProductService;
import com.devpro.services.ProductSaleService;


@Controller
public class ProductsController extends BaseController{
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductSaleService productSaleService;
	
	@RequestMapping(value = { "/products/{seo}" }, method = RequestMethod.GET)
	public String index(@PathVariable("seo") String seo,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		ProductSearch productSearch = new ProductSearch(); ////
		productSearch.setSeoProduct(seo);
		List<Product> products = productService.search(productSearch);
		Integer id = null;
		for (Product product2 : products) {
			id= product2.getId();
		}
		Product p = productRepo.getOne(id);
		ProductCustom pc = new ProductCustom();
		pc.setProduct(p);
		pc.setDiscount(productSaleService.getDiscountByIdProduct(p.getId()));
		pc.setPrice_sale(p.getPrice().subtract(p.getPrice().multiply(new BigDecimal(pc.getDiscount()).divide(new BigDecimal(100)))));
		model.addAttribute("productCustom", pc);
		
		return "front-end/products";
	}
	
	
}
