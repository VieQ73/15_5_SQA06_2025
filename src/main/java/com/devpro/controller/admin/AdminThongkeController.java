package com.devpro.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.Order;
import com.devpro.repositories.OrderRepo;
import com.devpro.services.OrderProductService;

@Controller
public class AdminThongkeController {

	@Autowired
	private OrderProductService orderProductService;
	@RequestMapping(value = { "/admin/thongke" }, method = RequestMethod.GET)
	public String saveProduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		List<Order> listOrder2 = orderProductService.findOrderByStatus(2);
		
		
		return "back-end/thongke";
	}
}
