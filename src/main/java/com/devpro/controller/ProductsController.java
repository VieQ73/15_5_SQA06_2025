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
import com.devpro.model.ProductSearch;
import com.devpro.repositories.ProductRepo;
import com.devpro.services.ProductService;


@Controller
public class ProductsController extends BaseController{
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	ProductService productService;
	/*
	 * @RequestMapping(value = { "/products" }, method = RequestMethod.GET) public
	 * String getProductFromCategoryId(final ModelMap model, final
	 * HttpServletRequest request, final HttpServletResponse response) throws
	 * Exception {
	 * 
	 * return "front-end/products"; }
	 */
	
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
		model.addAttribute("product", productRepo.getOne(id));
		BigDecimal total = productRepo.getOne(id).getPrice()
				.multiply(new BigDecimal(productRepo.getOne(id).getSaleoff()).divide(new BigDecimal(100)));
		model.addAttribute("totalSale", productRepo.getOne(id).getPrice().subtract(total));
		return "front-end/products";
	}
	
	
}
