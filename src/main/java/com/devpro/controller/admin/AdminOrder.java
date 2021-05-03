package com.devpro.controller.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.devpro.entities.Order;
import com.devpro.entities.OrderProducts;


import com.devpro.repositories.OrderRepo;
import com.devpro.services.OrderProductService;
import com.ibm.icu.util.Calendar;
@Controller
public class AdminOrder {
	@Autowired
	private OrderProductService orderService;
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	public JavaMailSender javaMailSender;
	
	
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
		model.addAttribute("orderproducts", orderService.searchProduct(id));
		return "back-end/orderProduct";
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
		model.addAttribute("saleorders", orderService.searchAdmin(null));
		
		
//		String email = saleOrderInDP.getEmail();
//		MyConstants myConstants = new MyConstants();
//		myConstants.setFRIEND_EMAIL(email);
//		SimpleMailMessage mess = new SimpleMailMessage();
//		mess.setTo(myConstants.getFRIEND_EMAIL());
//		mess.setSubject("Xác nhận đơn hàng");
//		mess.setText("Xin chào! Đơn hàng của bạn đã được gửi. Vui lòng giữ liên lạc để nhận hàng. Cảm ơn bạn đã mua"
//				+ "hàng của chúng tôi");
// 
//        // Send Message!
//        this.javaMailSender.send(mess);

		return "back-end/order";
	}
}
