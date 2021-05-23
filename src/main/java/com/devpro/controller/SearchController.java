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

import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
import com.devpro.services.ProductSaleService;
import com.devpro.services.ProductService;

@Controller
public class SearchController extends BaseController{
	@Autowired 
	ProductService productService;
	@Autowired
	private ProductSaleService productSaleService;
	@RequestMapping(value = { "/search" })
	public String viewHomePage(ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
        String keyword = request.getParameter("keyword");
		List<Product> products = productService.listAll(keyword);
        List<ProductCustom> productCustom = new ArrayList<ProductCustom>();
		for (Product item : products) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			productCustom.add(p);
		}
		model.addAttribute("productCustom", productCustom);
		model.addAttribute("key", keyword);
		return "front-end/search";
	}
	@RequestMapping(value = { "/search/desc/{keyword}" })
	public String searchT(ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response, @PathVariable("keyword") String keyword) throws Exception {
		List<Product> products = new ArrayList<>();
		if(keyword != "") {
        	products = productService.listAllDesc(keyword);
        }
        else {
        	products = productService.search(null);
        }
        List<ProductCustom> productCustom = new ArrayList<ProductCustom>();
		for (Product item : products) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			productCustom.add(p);
		}
		
		model.addAttribute("productCustom", productCustom);
		model.addAttribute("key", keyword);
		model.addAttribute("selectS", "1");
		return "front-end/search";
	}
	@RequestMapping(value = { "/search/asc/{keyword}" })
	public String searchG(ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response, @PathVariable("keyword") String keyword) throws Exception {
        
		List<Product> products = new ArrayList<>();
		if(keyword != "") {
        	products = productService.listAllDesc(keyword);
        }
        else {
        	products = productService.search(null);
        }
        List<ProductCustom> productCustom = new ArrayList<ProductCustom>();
		for (Product item : products) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			productCustom.add(p);
		}
		
		model.addAttribute("productCustom", productCustom);
		model.addAttribute("key", keyword);
		model.addAttribute("selectS", "2");
		return "front-end/search";
	}
}
