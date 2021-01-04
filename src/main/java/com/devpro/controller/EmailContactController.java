package com.devpro.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devpro.entities.MyConstants;
import com.devpro.model.AjaxResponse;

@Controller
public class EmailContactController {
	@Autowired
	public JavaMailSender javaMailSender;
	
	@RequestMapping(value = { "/send-email" }, method = RequestMethod.POST)
	public String senEmail(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		String email = request.getParameter("FRIEND_EMAIL");
		MyConstants myConstants = new MyConstants();
		myConstants.setFRIEND_EMAIL(email);
		SimpleMailMessage mess = new SimpleMailMessage();
		mess.setTo(myConstants.getFRIEND_EMAIL());
		mess.setSubject("Imua.com");
		mess.setText("Xin chào! Đây là email từ imua.com");
 
        // Send Message!
        this.javaMailSender.send(mess);
        return "thành công";
	}
	
	
}
