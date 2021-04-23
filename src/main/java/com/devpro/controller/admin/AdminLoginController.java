package com.devpro.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.devpro.entities.Customer;
import com.devpro.repositories.UserRepo;

@Controller // -> tạo ra 1 bean tên webConf và được spring-container quản lí.
			// -> báo module web mapping request cho controller này.
public class AdminLoginController {

	@Autowired
	private UserRepo userR;
	
	@RequestMapping(value = { "/cai-nay-la-mapping-trong-adminlogincontroller" }, method = RequestMethod.GET)
	public String index(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		return "back-end/login";
	}
	@RequestMapping(value = { "/hien-thi-loi-neu-khong-co-quen" }, method = RequestMethod.GET)
	public String accessDenied(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		return "back-end/accessDenied";
	}
	@RequestMapping(value = { "/registration_get" }, method = RequestMethod.GET)
	public String registration(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		return "back-end/Registration";
	}
}
