package com.devpro.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.Gift;
import com.devpro.entities.Product;
import com.devpro.model.GiftCustom;
import com.devpro.services.GiftService;

@Controller
public class GiftController extends BaseController{
	@Autowired
	private GiftService giftService;
	@RequestMapping(value = { "/gift" }, method = RequestMethod.GET)
	public String hdmh(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		List<Gift> gift = giftService.searchGift(null);
		List<GiftCustom> gifts = new ArrayList<>();
		for (Gift item : gift) {
			List<Product> p = giftService.searchProductGift(item.getId());
			GiftCustom g = new GiftCustom(item, p);
			gifts.add(g);
		}
		model.addAttribute("gifts", gifts);
		
		return "front-end/gift";
	}
}
