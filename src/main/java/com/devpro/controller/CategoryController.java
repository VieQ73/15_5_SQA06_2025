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
import com.devpro.model.ProductSearch;
import com.devpro.repositories.CategoryRepo;
import com.devpro.repositories.ProductRepo;
import com.devpro.services.CategoryService;
import com.devpro.services.ProductService;

@Controller
public class CategoryController extends BaseController {
	@Autowired
	ProductService productService;
	@Autowired
	private CategoryRepo categoryRepo;

	/*
	 * @RequestMapping(value = { "/category" }, method = RequestMethod.GET) public
	 * String index(final ModelMap model, final HttpServletRequest request, final
	 * HttpServletResponse response) throws Exception {
	 * model.addAttribute("products", productService.search(null)); return
	 * "front-end/category"; }
	 */
	@RequestMapping(value = { "/category/{seo}" }, method = RequestMethod.GET)
	public String index(@PathVariable("seo") String seo, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		ProductSearch productSearch = new ProductSearch();
		productSearch.setSeoCategoty(seo);

		
		Integer currentPage = 0; String strCurrentPage =
		request.getParameter("page");
		if(strCurrentPage != null)
			currentPage = Integer.parseInt(strCurrentPage);
		productSearch.setCurrentPage(currentPage);
		 

		List<Product> products = productService.search(productSearch);
		Integer id = products.get(0).getCategory().getId();
		model.addAttribute("category", categoryRepo.getOne(id));
		model.addAttribute("products", products);
		return "front-end/category";
	}

}
