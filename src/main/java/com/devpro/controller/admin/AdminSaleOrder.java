package com.devpro.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.MyConstants;

import com.devpro.entities.SaleOrder;


import com.devpro.repositories.SaleOrderRepo;
import com.devpro.services.SaleOrderService;
@Controller
public class AdminSaleOrder {
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private SaleOrderRepo saleOrderRepo;
	@Autowired
	public JavaMailSender javaMailSender;
	
	
	@RequestMapping(value = { "/admin/saleorder" }, method = RequestMethod.GET)
	public String saveProduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		model.addAttribute("saleorders", saleOrderService.searchAdmin(null));
		return "back-end/saleorder";
	}
	@RequestMapping(value = { "/admin/saleorder/{id}" }, method = RequestMethod.GET)
	public String saveProduct(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		SaleOrder saleOrder = saleOrderRepo.getOne(id);
		model.addAttribute("saleorder", saleOrder);
		model.addAttribute("saleorderproducts", saleOrderService.searchProduct(id));
		return "back-end/saleorderProduct";
	}
	@RequestMapping(value = { "/confirm_saleProduct/{id}" }, method = RequestMethod.GET)
	public String confirm_sale(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		SaleOrder saleOrderInDP = saleOrderRepo.getOne(id);
		saleOrderInDP.setStatus_ok(1);
		saleOrderRepo.save(saleOrderInDP);
		model.addAttribute("saleorders", saleOrderService.searchAdmin(null));
		
//		String emaill = request.getParameter("FRIEND_EMAIL");
//		MyConstants myConstants = new MyConstants();
//		myConstants.setFRIEND_EMAIL(emaill);
//		SimpleMailMessage mess = new SimpleMailMessage();
//		mess.setTo(myConstants.getFRIEND_EMAIL());
//		mess.setSubject("Imua.com");
//		mess.setText("Xin chào! Đây là email từ imua.com Đơn hàng bạn đặt đã được chuyển đi. Vui lòng giữ liên lạc để nhận hàng");
// 
//        // Send Message!
//        this.javaMailSender.send(mess);
		
        
        
        

        
        
        
		return "back-end/saleorder";
	}
}
