package com.devpro.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.devpro.entities.Order;
import com.devpro.entities.OrderProducts;
import com.devpro.model.OrderProductCustom;
import com.devpro.repositories.OrderRepo;
import com.devpro.services.OrderProductService;

import com.ibm.icu.util.Calendar;

@Controller
public class AdminOrder {
	@Autowired
	private OrderProductService orderService;
	@Autowired
	private OrderRepo orderRepo;

	
	
	@RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
	public String saveProduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		model.addAttribute("order", orderService.searchAdmin(null));
		return "back-end/order";
	}
	@RequestMapping(value = { "/admin/order/{id}" }, method = RequestMethod.GET)
	public String saveProduct(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Order order = orderRepo.getOne(id);
		model.addAttribute("order", order);
		
		List<OrderProductCustom> listOPC = new ArrayList<>();
		List<OrderProducts> listOP = orderService.searchProduct(id);
		for (OrderProducts item : listOP) {
			OrderProductCustom p = new OrderProductCustom();
			p.setOrderProduct(item);
			p.setThanhTien(item.getPrice().multiply(new BigDecimal(item.getQuality())));
			listOPC.add(p);
		}
		model.addAttribute("orderproducts", listOPC);
		return "back-end/orderProduct";
	}
	@RequestMapping(value = { "/admin/confirm_saleProduct/{id}/{s}" }, method = RequestMethod.GET)
	public String confirm_order(@PathVariable("id") Integer id,@PathVariable("s") String s, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
		Order saleOrderInDP = orderRepo.getOne(id);
		saleOrderInDP.setStatus(3);
		List<OrderProducts> sop = orderService.searchProduct(id);
		for (OrderProducts item : sop) {
			item.getProduct().setAmount(item.getQuality()+item.getProduct().getAmount());
		}
		saleOrderInDP.setNote_by_admin(s);
		Date d = Calendar.getInstance().getTime();
		saleOrderInDP.setUpdated_date(d);
		orderRepo.save(saleOrderInDP);
		model.addAttribute("order", orderService.searchAdmin(null));

		return "back-end/order";
	}
	@RequestMapping(value = { "/admin/confirm_saleProduct/{id}" }, method = RequestMethod.GET)
	public String confirm_sale(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String status = request.getParameter("status");
		Order saleOrderInDP = orderRepo.getOne(id);
		if(status.equals("1")) {
			saleOrderInDP.setStatus(1);
		}
		if(status.equals("2")) {
			saleOrderInDP.setStatus(2);
		}
		if(status.equals("3")) {
			saleOrderInDP.setStatus(3);
			List<OrderProducts> sop = orderService.searchProduct(id);
			for (OrderProducts item : sop) {
				item.getProduct().setAmount(item.getQuality()+item.getProduct().getAmount());
			}
		}
		Date d = Calendar.getInstance().getTime();
		saleOrderInDP.setUpdated_date(d);
		orderRepo.save(saleOrderInDP);
		model.addAttribute("order", orderService.searchAdmin(null));

		return "back-end/order";
	}
	@RequestMapping(value = { "/admin/print/{id}" }, method = RequestMethod.GET)
	public String printf(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Order order = orderRepo.getOne(id);
		model.addAttribute("order", order);
		
		List<OrderProductCustom> listOPC = new ArrayList<>();
		List<OrderProducts> listOP = orderService.searchProduct(id);
		for (OrderProducts item : listOP) {
			OrderProductCustom p = new OrderProductCustom();
			p.setOrderProduct(item);
			p.setThanhTien(item.getPrice().multiply(new BigDecimal(item.getQuality())));
			listOPC.add(p);
		}
		model.addAttribute("orderproducts", listOPC);
		return "back-end/invoice";
	}
}
