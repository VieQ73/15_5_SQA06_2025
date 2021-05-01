package com.devpro.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devpro.entities.Images;
import com.devpro.entities.News;
import com.devpro.entities.Product;
import com.devpro.repositories.NewsRepo;

@Service
public class NewsService {
	@Autowired 
	private NewsRepo newsRepo;
	@PersistenceContext protected EntityManager entityManager;
	private boolean isEmptyUploadFile(MultipartFile[] images) {
		if(images == null || images.length <= 0) return true; 
		if(images.length == 1 && images[0].getOriginalFilename().isEmpty()) return true;
		return false;
	}
	public void save(MultipartFile[] newsImages, News news) throws IOException {
		if(news.getId() != null) {
			News newsInDb = newsRepo.findById(news.getId()).get();
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date d = cal.getTime();
			news.setUpdatedDate(d);
			if(!isEmptyUploadFile(newsImages)) { 
				List<Images> oldNewsImages = newsInDb.getNewsImages();
				for(Images _images : oldNewsImages) {
					new File("C:\\Users\\PV\\OneDrive\\Máy tính\\DoAnTotNghiepHaUI\\upload\\" + _images.getPath()).delete();
				}
				news.removeNewsImages();
				
			} else { // ảnh phải giữ nguyên
				news.setNewsImages(newsInDb.getNewsImages());
				news.setCreatedDate(newsInDb.getCreatedDate());
			}
		}
		
		if(!isEmptyUploadFile(newsImages)) { // có upload ảnh lên.
			for(MultipartFile newsImage : newsImages) {
				
				// lưu vật lí
				newsImage.transferTo(new File("C:\\Users\\PV\\OneDrive\\Máy tính\\DoAnTotNghiepHaUI\\upload\\" + newsImage.getOriginalFilename()));
				
				Images _newsImages = new Images();
				_newsImages.setPath(newsImage.getOriginalFilename());
				_newsImages.setTitle(newsImage.getOriginalFilename());
				news.addNewsImages(_newsImages);
			}
		}
		
		newsRepo.save(news);
	}
	
	public List<News> getNewsActive(){
		String sql ="select * from tbl_news where status = true";
		Query query = entityManager.createNativeQuery(sql, News.class);
		return query.getResultList();
	}
	
	public List<News> getNewsIndex(){
		String sql ="select * from tbl_news where status = true order by rand() limit 0,4;";
		Query query = entityManager.createNativeQuery(sql, News.class);
		return query.getResultList();
	}
}
