package com.devpro.controller.admin;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.services.UserService;



@Controller
public class AdminCustomerController {
	@Autowired
	private UserService userService;
	@RequestMapping(value = { "/admin/listUser" }, method = RequestMethod.GET)
	public String user(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		model.addAttribute("users", userService.searchUser(null));

		return "back-end/listUser";
	}
}
