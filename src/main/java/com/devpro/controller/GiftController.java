package com.devpro.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.Category;
import com.devpro.entities.Gift;
import com.devpro.services.CategoryService;
import com.devpro.services.GiftService;

@Controller
public class GiftController {
	@Autowired
	private GiftService giftService;
	@Autowired
	private CategoryService categoryService;
	@RequestMapping(value = { "/gift" }, method = RequestMethod.GET)
	public String hdmh(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		List<Category> categories = categoryService.search(null);
		model.addAttribute("categories", categories);
		List<Gift> gifts = giftService.searchGift(null);
		model.addAttribute("gifts", gifts);
		return "front-end/gift";
	}
}
