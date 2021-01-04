package com.devpro.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devpro.entities.Category;
import com.devpro.model.AjaxResponse;
import com.devpro.model.Contact;
import com.devpro.repositories.CategoryRepo;
import com.devpro.services.CategoryService;
import com.devpro.services.ProductService;

/**
 * 
 * @author admin
 *
 */
@Controller // -> tạo ra 1 bean tên webConf và được spring-container quản lí.
			// -> báo module web mapping request cho controller này.
public class IndexController {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	/**
	 * 
	 * @param model: trung gian trao đổi thông tin giữa Controller và View(Jsp)
	 * @param request: Chứa các thông tin Trình Duyệt truy vấn thông qua mẫu: ?abc=xyz
	 * @param response: Dữ liệu trả về Trình Duyệt.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String index(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		
		List<Category> categories = categoryService.search(null);
		model.addAttribute("categories", categories);
		model.addAttribute("products", productService.searchProductWithCate8(16));
		model.addAttribute("products2", productService.searchProductWithCate8(17));
		model.addAttribute("products3", productService.searchProductWithCate8(18));
		model.addAttribute("products4", productService.searchProductWithCate8(19));
		model.addAttribute("productSelling", productService.searchPrSelling2(null));
		return "front-end/home";
	}
	
	@RequestMapping(value = { "/save-contact-with-ajax" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> saveWithAjax(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response,
													@RequestBody Contact data) {
		
		return ResponseEntity.ok(new AjaxResponse(200, "Thành công"));
	}
}
