package com.devpro.services;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devpro.entities.Gift;

import com.devpro.entities.Images;
import com.devpro.entities.Product;
import com.devpro.model.GiftSearch;

import com.devpro.repositories.GiftRepo;
import com.ibm.icu.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class GiftService {
	@Autowired 
	private GiftRepo giftRepo;
	@PersistenceContext
	protected EntityManager entityManager;

	boolean isEmptyUploadFile(MultipartFile[] images) {
		if(images == null || images.length <= 0) return true; 
		if(images.length == 1 && images[0].getOriginalFilename().isEmpty()) return true;
		return false;
	}
	/**
	 * Lưu sản phẩm và ảnh sản phẩm.
	 * 
	 * @param giftImages
	 * @param gift
	 * @throws IOException 
	 * @throws  
	 */

	public void save(MultipartFile[] giftImages, Gift gift) throws IOException {


		if(gift.getId() != null) {
			Gift giftInDb = giftRepo.findById(gift.getId()).get();
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date d = cal.getTime();
			gift.setUpdatedDate(d);
			if(!isEmptyUploadFile(giftImages)) {
				List<Images> oldGiftImages = giftInDb.getGiftImages();
				for(Images _images : oldGiftImages) {
					new File("D:\\IntelliJ\\DoAnTotNghiepHaUI\\src\\main\\resources\\META-INF\\images\\upload" + _images.getPath()).delete();
				}
				gift.removeGiftImages();

			} else { // ảnh phải giữ nguyên
				gift.setGiftImages(giftInDb.getGiftImages());
				gift.setCreatedDate(giftInDb.getCreatedDate());
			}
		}

		if(!isEmptyUploadFile(giftImages)) { // có upload ảnh lên.
			for(MultipartFile giftImage : giftImages) {

				// lưu vật lí
				giftImage.transferTo(new File("D:\\IntelliJ\\DoAnTotNghiepHaUI\\src\\main\\resources\\META-INF\\images\\upload" + giftImage.getOriginalFilename()));

				Images _giftImages = new Images();
				_giftImages.setPath(giftImage.getOriginalFilename());
				_giftImages.setTitle(giftImage.getOriginalFilename());
				gift.addGiftImages(_giftImages);
			}
		}

		giftRepo.save(gift);
	}
	public List<Gift> searchGift(final GiftSearch giftSearch) {
		String sql = "select g.* from tbl_gift as g left join tbl_product p on g.id = p.gift_id where 1=1";
		if (giftSearch.getTitle() != null && !giftSearch.getTitle().isEmpty()) {
			sql += " AND g.title LIKE '%" + giftSearch.getTitle() + "%'";
		}
		if (giftSearch.getGiftSeo() != null && !giftSearch.getGiftSeo().isEmpty()) {
			sql += " AND p.seo LIKE '%" + giftSearch.getGiftSeo() + "%'";
		}
		sql = sql + " order by updated_date desc;";
		Query query = entityManager.createNativeQuery(sql, Gift.class);
		
		return query.getResultList();
	}
	
	public List<Product> searchProductGift(Integer id) {
		String sql = "select * from tbl_product where status = true and gift_id = "+id+" limit 0,3;";
		Query query = entityManager.createNativeQuery(sql, Product.class);
		
		return query.getResultList();
	}
	public List<Product> searchProductGiftAdmin(Integer id) {
		String sql = "select * from tbl_product where status = true and gift_id = "+id;
		Query query = entityManager.createNativeQuery(sql, Product.class);
		
		return query.getResultList();
	}
	
	
}
