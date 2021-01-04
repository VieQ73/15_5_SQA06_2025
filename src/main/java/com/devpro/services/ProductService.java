package com.devpro.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devpro.entities.Product;
import com.devpro.entities.ProductImages;
import com.devpro.model.ProductSearch;
import com.devpro.repositories.ProductRepo;

@Service // -> Bean
public class ProductService {
	
	@PersistenceContext protected EntityManager entityManager;
	@Autowired
	private ProductRepo productRepo;
	
	private boolean isEmptyUploadFile(MultipartFile[] images) {
		if(images == null || images.length <= 0) return true; 
		if(images.length == 1 && images[0].getOriginalFilename().isEmpty()) return true;
		return false;
	}
	
	/**
	 * Lưu sản phẩm và ảnh sản phẩm.
	 * 
	 * @param productImages
	 * @param product
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */

	
	public void save(MultipartFile[] productImages, Product product) throws IllegalStateException, IOException {
		
		
		if(product.getId() != null) { // chỉnh sửa
			// lấy dữ liệu cũ của sản phẩm
			Product productInDb = productRepo.findById(product.getId()).get();
			
			if(!isEmptyUploadFile(productImages)) { // nếu admin sửa ảnh sản phẩm
				// lấy danh sách ảnh cũ của sản phẩm
				List<ProductImages> oldProductImages = productInDb.getProductImages();
				
				// xoá ảnh cũ trên vật lí(host)
				for(ProductImages _images : oldProductImages) {
					new File("E:\\java\\template\\imua.com\\upload\\" + _images.getPath()).delete();
				}
				
				// xoá ảnh trên database
				product.removeProductImages();
				
			} else { // ảnh phải giữ nguyên
				product.setProductImages(productInDb.getProductImages());
			}
			
		}
		
		if(!isEmptyUploadFile(productImages)) { // có upload ảnh lên.
			for(MultipartFile productImage : productImages) {
				
				// lưu vật lí
				productImage.transferTo(new File("E:\\java\\template\\imua.com\\upload\\" + productImage.getOriginalFilename()));
				
				ProductImages _productImages = new ProductImages();
				_productImages.setPath(productImage.getOriginalFilename());
				_productImages.setTitle(productImage.getOriginalFilename());
				product.addProductImages(_productImages);
			}
		}
		
		productRepo.save(product);
	}
	
	/**
	 * Tìm kiếm sản phẩm.
	 * 
	 * @param productSearch
	 * @return
	 */
	
	public List<Product> searchProductWithCate8(int idCate) {
//		String jpql = "Select caijcungduoc from Product caijcungduoc";
//		Query query = entityManager.createQuery(jpql, Product.class);

		String sql = "select * from tbl_products where status = 1 and category_id="+idCate+" order by rand() limit 0,8;";
		
		
		
		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}public List<Product> searchProductWithCate(String seo) {
//		String jpql = "Select caijcungduoc from Product caijcungduoc";
//		Query query = entityManager.createQuery(jpql, Product.class);

		String sql = "select * from tbl_products where category_id in (select id from tbl_category where seo='"+seo+"')";
		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}
	
	
	
	public List<Product> search(final ProductSearch productSearch) {
//		String jpql = "Select caijcungduoc from Product caijcungduoc";
//		Query query = entityManager.createQuery(jpql, Product.class);

		String sql = "select * from tbl_products where status=true";

		if(productSearch != null && productSearch.getCategoryId() != null) {
			sql = sql + " and category_id=" + productSearch.getCategoryId();
		} 
		if(productSearch != null && productSearch.getId() != null) {
			sql = sql + " and id=" + productSearch.getId();
		}
		if(productSearch != null && productSearch.getSeoProduct() != null) {
			sql = sql + " and seo='"+productSearch.getSeoProduct()+"';";
		}
		if(productSearch != null && productSearch.getSeoCategoty() != null) {
			sql = sql + " and category_id in (select id from tbl_category where seo='"+productSearch.getSeoCategoty()+"')";
		}
		Query query = entityManager.createNativeQuery(sql, Product.class);
		
		if(productSearch.getCurrentPage() != null && productSearch.getCurrentPage() > 0){
			query.setFirstResult((productSearch.getCurrentPage()-1) * ProductSearch.SIZE_ITEMS_ON_PAGE);
			query.setMaxResults(ProductSearch.SIZE_ITEMS_ON_PAGE); 
		}
		return query.getResultList();
	}
	
	
	public List<Product> searchPrSelling(final ProductSearch productSearch) {

		String sql = "select * from tbl_products where status=true and selling = true";

		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}
	public List<Product> searchPrSelling2(final ProductSearch productSearch) {

		String sql = "select * from tbl_products where status=true and selling = true order by rand() limit 0,6;";

		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}
	
	public List<Product> searchAdmin(final ProductSearch productSearch) {
//		String jpql = "Select caijcungduoc from Product caijcungduoc";
//		Query query = entityManager.createQuery(jpql, Product.class);

		String sql = "select * from tbl_products where 1=1";

		if(productSearch != null && productSearch.getCategoryId() != null) {
			sql = sql + " and category_id=" + productSearch.getCategoryId();
		} 
		if(productSearch != null && productSearch.getId() != null) {
			sql = sql + " and id=" + productSearch.getId();
		}
		if(productSearch != null && productSearch.getSeoProduct() != null) {
			sql = sql + " and seo='"+productSearch.getSeoProduct()+"';";
		}
		if(productSearch != null && productSearch.getSeoCategoty() != null) {
			sql = sql + " and category_id in (select id from tbl_category where seo='"+productSearch.getSeoCategoty()+"')";
		}
		Query query = entityManager.createNativeQuery(sql, Product.class);
		
		return query.getResultList();
	}
}
