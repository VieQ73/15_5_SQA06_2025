package com.devpro.controller.admin;


import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.devpro.entities.Gift;

import com.devpro.services.GiftService;

@Controller
public class AdminGiftController {
	@Autowired
	private GiftService giftService;
	@RequestMapping(value = { "/admin/addGift" }, method = RequestMethod.GET)
	public String saveProduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		model.addAttribute("gift", new Gift());
		return "back-end/addGift";
	}

	@RequestMapping(value = { "/admin/addGift" }, method = RequestMethod.POST)
	public String saveProduct(@RequestParam("gift_image") MultipartFile[] giftImages,
			@ModelAttribute("gift") Gift gift, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		Date d = Calendar.getInstance().getTime();
		gift.setCreatedDate(d);
		
		giftService.save(giftImages, gift);
		return "back-end/addGift";
	}
}
