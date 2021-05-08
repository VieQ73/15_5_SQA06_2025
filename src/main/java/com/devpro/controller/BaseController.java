package com.devpro.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.devpro.entities.Category;
import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
import com.devpro.model.ThongKe;
import com.devpro.repositories.CategoryRepo;
import com.devpro.services.ProductSaleService;
import com.devpro.services.ProductService;
import com.devpro.services.ThongKeService;
@Controller
public abstract class BaseController {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductSaleService productSaleService;
	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return categoryRepo.findAll();
	}
	
	@ModelAttribute("spbc")
	public List<ProductCustom> getSpbc() throws ParseException{
		List<ProductCustom> pc = new ArrayList<>();
		List<Product> productSelling = productService.searchPrSelling4(null);
		
		for (Product item : productSelling) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			pc.add(p);
		}
		return pc;
	}
	
}
