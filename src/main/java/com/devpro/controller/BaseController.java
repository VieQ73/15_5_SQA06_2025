package com.devpro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.devpro.entities.Category;
import com.devpro.repositories.CategoryRepo;
@Controller
public abstract class BaseController {
	@Autowired
	private CategoryRepo categoryRepo;
	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return categoryRepo.findAll();
	}
}
