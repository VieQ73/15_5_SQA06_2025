package com.devpro.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.Category;
import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
import com.devpro.model.ProductSearch;
import com.devpro.repositories.CategoryRepo;
import com.devpro.services.ProductService;
import com.devpro.services.OrderProductService;
import com.devpro.services.ProductSaleService;

@Controller
public class CategoryController extends BaseController {
	@Autowired
	ProductService productService;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ProductSaleService productSaleService;
	 
	@RequestMapping(value = { "/category/{seo}" }, method = RequestMethod.GET)
	public String index(@PathVariable("seo") String seo, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		ProductSearch productSearch = new ProductSearch();
		productSearch.setSeoCategoty(seo);
		
		productSearch.buildPaging(request); 
		model.addAttribute("page", productSearch);

		List<Product> products = productService.search(productSearch);
		
		Integer id = products.get(0).getCategory().getId();
		model.addAttribute("category", categoryRepo.getOne(id));
		
		List<ProductCustom> productCustom = new ArrayList<ProductCustom>();
		for (Product item : products) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			productCustom.add(p);
		}
		model.addAttribute("productCustom", productCustom);
		
		return "front-end/category";
	}
	@RequestMapping(value = { "/huongdanmuahang" }, method = RequestMethod.GET)
	public String hdmh(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		return "front-end/huongdanmuahang";
	}
}
