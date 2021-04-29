package com.devpro.controller.admin;

import java.util.Date;
import java.util.Calendar;

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

import com.devpro.controller.BaseController;
import com.devpro.entities.Product;
import com.devpro.model.AjaxResponse;
import com.devpro.repositories.GiftRepo;
import com.devpro.repositories.ProductRepo;
import com.devpro.services.ProductService;
import com.github.slugify.Slugify;

@Controller
public class AdminProductController extends BaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepo productRepo;
	

	@RequestMapping(value = { "/admin/addProduct" }, method = RequestMethod.GET)
	public String saveProduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		model.addAttribute("product", new Product());
		return "back-end/addProduct";
	}

	@RequestMapping(value = { "/admin/addProduct" }, method = RequestMethod.POST)
	public String saveProduct(@RequestParam("product_image") MultipartFile[] productImages,
			@ModelAttribute("product") Product product, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
		Slugify slg = new Slugify();
		String result = slg.slugify(product.getTitle() + "-" + System.currentTimeMillis());
		product.setSeo(result);
		Date d = Calendar.getInstance().getTime();
		product.setCreatedDate(d);
		productService.save(productImages, product);
		return "redirect:/admin/listProducts/?add=success";
	}

	@RequestMapping(value = { "/admin/listProducts" }, method = RequestMethod.GET)
	public String list(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		model.addAttribute("messsage", "");
		String messsage = request.getParameter("add");
		if (messsage != null && messsage.equalsIgnoreCase("success")) {
			model.addAttribute("messsage", "<div class=\"alert alert-success\">"
					+ "  <strong>Success!</strong> Cập nhật thành công." + "</div>");
		}
		model.addAttribute("products", productService.searchAdmin(null));
		return "back-end/listProducts";
	}
	@RequestMapping(value = { "/admin/product/{id}" }, method = RequestMethod.GET)
	public String saveProduct(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Product product = productRepo.getOne(id);
		model.addAttribute("product", product);
		return "back-end/addProduct";
	}

	@RequestMapping(value = { "/delete-product-with-ajax" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> deleteProduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response, @RequestBody Product product) {
		Product productInDB = productRepo.getOne(product.getId());
		if(productInDB.getStatus())
			productInDB.setStatus(false);
		else
			productInDB.setStatus(true);
		productRepo.save(productInDB);
		return ResponseEntity.ok(new AjaxResponse(200, productInDB.getId()));
	}
	
}
