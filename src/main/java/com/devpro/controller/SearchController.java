package com.devpro.controller;

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
import com.devpro.services.ProductService;

@Controller
public class SearchController extends BaseController{
	@Autowired 
	ProductService productService;
	@RequestMapping(value = { "/productsSerch" }, method = RequestMethod.GET)
	public String index(@PathVariable("find") String seo,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		List<Product> products = productService.searchProductWithCate(seo);
		model.addAttribute("product", products);
		return "front-end/search";
	}
}
