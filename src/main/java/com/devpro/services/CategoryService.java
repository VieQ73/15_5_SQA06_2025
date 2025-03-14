package com.devpro.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpro.entities.Category;
import com.devpro.model.CategorySearch;
import com.devpro.repositories.CategoryRepo;

@Service
public class CategoryService {
	@PersistenceContext protected EntityManager entityManager;
	@Autowired
	private CategoryRepo categoryRepo;
	public void save(Category category) {
		if(category.getId() != null)
		{
			Category categoryInDB = categoryRepo.findById(category.getId()).get();
		}
		categoryRepo.save(category);
	}
	
	public List<Category> searchAdmin(final CategorySearch categorySearch) {


		String sql = "select * from tbl_category where 1=1";

		Query query = entityManager.createNativeQuery(sql, Category.class);
		
		return query.getResultList();
	}
	public List<Category> search(final CategorySearch categorySearch) {


		String sql = "select * from tbl_category where status=1";

		Query query = entityManager.createNativeQuery(sql, Category.class);
		
		return query.getResultList();
	}
}
