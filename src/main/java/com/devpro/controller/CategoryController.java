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
import com.devpro.repositories.CategoryRepo;
import com.devpro.services.ProductService;

@Controller
public class CategoryController extends BaseController {
	@Autowired
	ProductService productService;
	@Autowired
	private CategoryRepo categoryRepo;
	 
	@RequestMapping(value = { "/category/{seo}" }, method = RequestMethod.GET)
	public String index(@PathVariable("seo") String seo, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		ProductSearch productSearch = new ProductSearch();
		productSearch.setSeoCategoty(seo);
		
		productSearch.buildPaging(request); 
		model.addAttribute("page", productSearch);

		List<Product> products = productService.search(productSearch);
		for (Product item : products) {
			BigDecimal gia = item.getPrice();
			if(item.getSaleoff()!=0)
			{
				gia = gia.subtract(gia.multiply(new BigDecimal(item.getSaleoff()).divide(new BigDecimal(100))));
			}
			item.setPrice(gia);
		}
		Integer id = products.get(0).getCategory().getId();
		model.addAttribute("category", categoryRepo.getOne(id));
		model.addAttribute("products", products);
		
		return "front-end/category";
	}

}
