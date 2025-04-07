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
import com.devpro.entities.News;
import com.devpro.model.AjaxResponse;
import com.devpro.repositories.NewsRepo;
import com.devpro.services.NewsService;
import com.github.slugify.Slugify;

@Controller
public class AdminNewsController {
	@Autowired
	private NewsRepo newsRepo;
	
	@Autowired
	private NewsService newsService;
	
	@RequestMapping(value = { "/admin/addNews" }, method = RequestMethod.GET)
	public String saveNews(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		model.addAttribute("news", new News());
		return "back-end/addNews";
	}
	
	@RequestMapping(value = { "/admin/addNews" }, method = RequestMethod.POST)
	public String saveNews(@RequestParam("news_image") MultipartFile[] newsImages,
			@ModelAttribute("news") News news, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		Date d = Calendar.getInstance().getTime();
		news.setCreatedDate(d);
		Slugify slg = new Slugify();
		String result = slg.slugify(news.getTitle() + "-" + System.currentTimeMillis());
		news.setSeo(result);
		newsService.save(newsImages, news);
		return "redirect:/admin/listNews/?add=success";
	}
	
	@RequestMapping(value = { "/admin/listNews" }, method = RequestMethod.GET)
	public String list(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		model.addAttribute("messsage", "");
		String messsage = request.getParameter("add");
		if (messsage != null && messsage.equalsIgnoreCase("success")) {
			model.addAttribute("messsage", "<div class=\"alert alert-success\">"
					+ "  <strong>Success!</strong> Cập nhật thành công." + "</div>");
		}
		model.addAttribute("news", newsRepo.findAll());
		return "back-end/listNews";
	}
	@RequestMapping(value = { "/admin/news/{id}" }, method = RequestMethod.GET)
	public String saveNews(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		News news = newsRepo.getOne(id);
		model.addAttribute("news", news);
		return "back-end/addNews";
	}

	@RequestMapping(value = { "/delete-news-with-ajax" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> deleteProduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response, @RequestBody News news) {
		News newsInDB = newsRepo.getOne(news.getId());
		if(newsInDB.getStatus())
			newsInDB.setStatus(false);
		else
			newsInDB.setStatus(true);
		newsRepo.save(newsInDB);
		return ResponseEntity.ok(new AjaxResponse(200, newsInDB.getId()));
	}
}
