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

import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
import com.devpro.services.ProductService;
import com.devpro.services.ProductSaleService;


@Controller
public class SaleOffContrller extends BaseController{

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductSaleService productSaleService;

	@RequestMapping(value = { "/saleoff" }, method = RequestMethod.GET)
	public String hdmh(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		List<Product> products = productService.getProductSale(null);
		List<ProductCustom> productCustom = new ArrayList<ProductCustom>();
		for (Product item : products) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount()).divide(new BigDecimal(100)))));
			productCustom.add(p);
		}
		model.addAttribute("productCustom", productCustom);

		return "front-end/saleoff";
	}
	
}
