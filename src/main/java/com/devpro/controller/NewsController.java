package com.devpro.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.devpro.entities.Category;
import com.devpro.entities.News;
import com.devpro.repositories.NewsRepo;
import com.devpro.services.CategoryService;
import com.devpro.services.NewsService;


@Controller
public class NewsController extends BaseController{

	@Autowired
	private NewsService newsService;
	@Autowired
	private NewsRepo newsRepo;

	@RequestMapping(value = { "/news" }, method = RequestMethod.GET)
	public String hdmh(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		List<News> news = newsService.getNewsActive();
		model.addAttribute("news", news);
		return "front-end/news";
	}
	
	@RequestMapping(value = { "/newsDetail/{id}" }, method = RequestMethod.GET)
	public String newDetail(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		News news = newsRepo.getOne(id);
		model.addAttribute("news", news);
		return "front-end/newsDetail";
	}
	
}
