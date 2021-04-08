package com.devpro.controller;

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
import com.devpro.services.CategoryService;
import com.devpro.services.ProductService;


@Controller
public class SaleOffContrller {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;

	@RequestMapping(value = { "/saleoff" }, method = RequestMethod.GET)
	public String hdmh(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		List<Category> categories = categoryService.search(null);
		model.addAttribute("categories", categories);
		List<Product> products = productService.getProductSale(null);
		model.addAttribute("products", products);

		return "front-end/saleoff";
	}
	
}
