package com.devpro.controller.admin;


import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.devpro.entities.Gift;
import com.devpro.entities.Product;
import com.devpro.model.AjaxResponse;
import com.devpro.repositories.GiftRepo;
import com.devpro.services.GiftService;

@Controller
public class AdminGiftController {
	@Autowired
	private GiftService giftService;
	@Autowired
	private GiftRepo giftRepo;
	
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
		return "redirect:/admin/listGift/?add=success";
	}
	
	@RequestMapping(value = { "/admin/listGift" }, method = RequestMethod.GET)
	public String list(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		model.addAttribute("messsage", "");
		String messsage = request.getParameter("add");
		if (messsage != null && messsage.equalsIgnoreCase("success")) {
			model.addAttribute("messsage", "<div class=\"alert alert-success\">"
					+ "  <strong>Success!</strong> Cập nhật thành công." + "</div>");
		}
		model.addAttribute("gift", giftRepo.findAll());
		return "back-end/listGift";
	}
	@RequestMapping(value = { "/admin/gift/{id}" }, method = RequestMethod.GET)
	public String saveProduct(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Gift gift = giftRepo.getOne(id);
		model.addAttribute("gift", gift);
		return "back-end/addGift";
	}

	@RequestMapping(value = { "/delete-gift-with-ajax" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> deleteProduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response, @RequestBody Gift gift) {
		Gift giftInDB = giftRepo.getOne(gift.getId());
		if(giftInDB.getStatus())
			giftInDB.setStatus(false);
		else
			giftInDB.setStatus(true);
		giftRepo.save(giftInDB);
		return ResponseEntity.ok(new AjaxResponse(200, giftInDB.getId()));
	}
}
