package com.devpro.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devpro.entities.Product;
import com.devpro.entities.ProductSale;
import com.devpro.entities.Sale;
import com.devpro.entities.Images;
import com.devpro.model.ProductCustom;
import com.devpro.model.ProductSearch;
import com.devpro.repositories.ProductRepo;
import com.taglib.PaginationTaglib;



@Service // -> Bean
public class ProductService {
	
	@PersistenceContext protected EntityManager entityManager;
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private ProductSaleService productSaleService;
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
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date d = cal.getTime();
			product.setUpdatedDate(d);
			product.setAmount(product.getAmount() + productInDb.getAmount());
			if(!isEmptyUploadFile(productImages)) { // nếu admin sửa ảnh sản phẩm
				// lấy danh sách ảnh cũ của sản phẩm
				List<Images> oldProductImages = productInDb.getProductImages();
				
				// xoá ảnh cũ trên vật lí(host)
				for(Images _images : oldProductImages) {
					new File("C:\\Users\\PV\\OneDrive\\Máy tính\\DoAnTotNghiepHaUI\\upload\\" + _images.getPath()).delete();
				}
				
				// xoá ảnh trên database
				product.removeProductImages();
				
			} else { // ảnh phải giữ nguyên
				product.setProductImages(productInDb.getProductImages());
				product.setCreatedDate(productInDb.getCreatedDate());
			}
		}
		
		if(!isEmptyUploadFile(productImages)) { // có upload ảnh lên.
			for(MultipartFile productImage : productImages) {
				
				// lưu vật lí
				productImage.transferTo(new File("C:\\Users\\PV\\OneDrive\\Máy tính\\DoAnTotNghiepHaUI\\upload\\" + productImage.getOriginalFilename()));
				
				Images _productImages = new Images();
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
	
	public List<Product> listAll(String keyword) {
        if (keyword != null) {
        	String sql = "SELECT * FROM tbl_product WHERE title LIKE '%"+keyword+"%' order by rand()";
    		Query query = entityManager.createNativeQuery(sql, Product.class);
    		return query.getResultList();
        }
        return productRepo.findAll();
    }
	
	public List<Product> listAllDesc(String keyword) {
        if (keyword != null) {
        	String sql = "SELECT * FROM tbl_product WHERE title LIKE '%"+keyword+"%' order by price desc";
    		Query query = entityManager.createNativeQuery(sql, Product.class);
    		return query.getResultList();
        }
        return productRepo.findAll();
    }
	
	public List<Product> listAllAsc(String keyword) {
        if (keyword != null) {
        	String sql = "SELECT * FROM tbl_product WHERE title LIKE '%"+keyword+"%' order by price asc";
    		Query query = entityManager.createNativeQuery(sql, Product.class);
    		return query.getResultList();
        }
        return productRepo.findAll();
    }
	
	public List<Product> searchProductWithCate8(int idCate) {
		String sql = "select * from tbl_product where status = 1 and category_id="+idCate+" order by rand() limit 0,8;";

		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}
	public List<Product> searchProductWithCate(String seo) {
//		String jpql = "Select caijcungduoc from Product caijcungduoc";
//		Query query = entityManager.createQuery(jpql, Product.class);

		String sql = "select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='"+seo+"')";
		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}
	
	public List<Product> getProductSale(final ProductSearch productSreach) {
		String sql = "SELECT distinct a.* FROM tbl_product a, tbl_product_sale b, tbl_sale c where a.status = 1 and a.id = b.product_id and b.sale_id = c.id and curdate() >= c.start_date and curdate() <= c.end_date and b.active = 1;";
		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}
	
	
//	public Sale getDiscount(int id) {
//		String sql ="SELECT c.* FROM tbl_products a, tbl_product_sale b, tbl_sale c where a.id="+id+" and a.status = 1 and a.id = b.product_id and b.sale_id = c.id and curdate() >= c.start_date and curdate() <= c.end_date order by datediff(curdate(), c.start_date) asc limit 1;";
//		Query query = entityManager.createNativeQuery(sql, Sale.class);
//		return (Sale) query.getSingleResult();
//	}
	
	public List<Product> getAllProduct(){
		String sql = "select * from tbl_product where status=true";
		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}
	
	public List<Product> search(final ProductSearch productSearch) {
//		String jpql = "Select caijcungduoc from Product caijcungduoc";
//		Query query = entityManager.createQuery(jpql, Product.class);

		String sql = "select * from tbl_product where status=true";

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
		//page
		if(productSearch.getOffset() != null) {
			productSearch.setCount(query.getResultList().size());
			query.setFirstResult(productSearch.getOffset());
			query.setMaxResults(PaginationTaglib.MAX);
		}
		
		return query.getResultList();
	}
	
	public List<Product> searchPrSelling2(final ProductSearch productSearch) {

		String sql = "select * from tbl_product where status=true order by selling DESC limit 0,6;";

		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}
	public List<Product> searchPrSelling4(final ProductSearch productSearch) {

		String sql = "select * from tbl_product where status=true order by selling DESC limit 0,4;";

		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}
	
	public List<Product> searchAdmin(final ProductSearch productSearch) {


		String sql = "select * from tbl_product where 1=1";

		if(productSearch != null && productSearch.getCategoryId() != null) {
			sql = sql + " and category_id=" + productSearch.getCategoryId();
		} 
		if(productSearch != null && productSearch.getId() != null) {
			sql = sql + " and id=" + productSearch.getId();
		}
		if(productSearch != null && productSearch.getSeoProduct() != null) {
			sql = sql + " and seo='"+productSearch.getSeoProduct()+"'";
		}
		if(productSearch != null && productSearch.getSeoCategoty() != null) {
			sql = sql + " and category_id in (select id from tbl_category where seo='"+productSearch.getSeoCategoty()+"')";
		}
		sql = sql + " order by updated_date desc;";
		Query query = entityManager.createNativeQuery(sql, Product.class);
		
		return query.getResultList();
	}
	
	
	public List<ProductCustom> getProductCustom(){
		List<ProductCustom> listPc = new ArrayList<>();
		List<Product> listP = searchAdmin(null);
		for (Product item : listP) {
			ProductCustom p = new ProductCustom();
			p.setProduct(item);
			p.setDiscount(productSaleService.getDiscountByIdProduct(item.getId()));
			p.setPrice_sale(item.getPrice().subtract(item.getPrice().multiply(new BigDecimal(p.getDiscount())).divide(new BigDecimal(100))));
			listPc.add(p);
		}
		return listPc;
	}
}
