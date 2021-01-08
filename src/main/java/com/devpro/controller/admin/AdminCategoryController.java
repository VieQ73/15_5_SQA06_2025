package com.devpro.controller.admin;

import java.time.LocalDateTime;

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

import com.devpro.entities.Category;
import com.devpro.entities.Product;
import com.devpro.model.AjaxResponse;
import com.devpro.repositories.CategoryRepo;
import com.devpro.services.CategoryService;
import com.github.slugify.Slugify;


@Controller
public class AdminCategoryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired 
	private CategoryRepo categoryRepo;
	
	@RequestMapping(value = { "/admin/addCategory" }, method = RequestMethod.GET)
	public String index(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		model.addAttribute("category", new Category());
		return "back-end/addCategory";
	}
	
	@RequestMapping(value = {"/admin/addCategory"}, method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("category") Category category,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		Slugify slg = new Slugify();
		String result = slg.slugify(category.getName() + "-" + System.currentTimeMillis());
		category.setSeo(result);
		category.setCreatedDate(LocalDateTime.now());
		categoryService.save(category);
		return "redirect:/admin/listCategory/?add=success";
	}
	

	@RequestMapping(value = { "/admin/listCategory" }, method = RequestMethod.GET)
	public String list(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		model.addAttribute("messsage", "");
		String messsage = request.getParameter("add");
		if (messsage != null && messsage.equalsIgnoreCase("success")) {
			model.addAttribute("messsage", "<div class=\"alert alert-success\">"
					+ "  <strong>Success!</strong> Cập nhật thành công." + "</div>");
		}
		model.addAttribute("categorys", categoryService.searchAdmin(null));
		return "back-end/listCategory";
	}
	
	 @RequestMapping(value = { "/admin/category/{id}" }, method = RequestMethod.GET) 
	 public String saveProduct(@PathVariable("id") Integer id,
	 final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	 Category category = categoryRepo.getOne(id);
	 model.addAttribute("category", category); 
	 return "back-end/addCategory"; 
	 }
	 
	 @RequestMapping(value = { "/delete-category-with-ajax" }, method = RequestMethod.POST)
		public ResponseEntity<AjaxResponse> deleteCategory(final ModelMap model, final HttpServletRequest request,
				final HttpServletResponse response, @RequestBody Category category) {
			
			Category categoryInDB = categoryRepo.getOne(category.getId());
			categoryInDB.setStatus(false);
			categoryRepo.save(categoryInDB);
			return ResponseEntity.ok(new AjaxResponse(200, "Xóa thành công"));
		}
}
