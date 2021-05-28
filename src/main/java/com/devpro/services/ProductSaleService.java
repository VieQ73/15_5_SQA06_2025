package com.devpro.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpro.entities.ProductSale;
import com.devpro.entities.Sale;
import com.devpro.repositories.ProductSaleRepo;
import com.devpro.repositories.SaleRepo;

@Service
public class ProductSaleService {
	@PersistenceContext protected EntityManager entityManager;
	
	@Autowired
	private SaleRepo saleRepo;
	
	@Autowired
	private ProductSaleRepo productSaleRepo;
	
	public void saveProductSale(ProductSale productSale) {
		if(productSale.getId() != null) {
			productSaleRepo.delete(productSale);
		}
		productSaleRepo.save(productSale);
	}
	
	public void saveSale(Sale sale) {
		if(sale.getId() != null) {
			
		}
		saleRepo.save(sale);
	}
	
	public List<Sale> getSale(){
		String sql ="select * from tbl_sale where status = true";
		Query query = entityManager.createNativeQuery(sql, Sale.class);
		return query.getResultList();
	}
	
	public List<Sale> getSaleAdmin(){
		String sql ="select * from tbl_sale";
		Query query = entityManager.createNativeQuery(sql, Sale.class);
		return query.getResultList();
	}
	
	public List<ProductSale> getProductSale(){
		String sql ="select * from tbl_product_sale";
		Query query = entityManager.createNativeQuery(sql, ProductSale.class);
		return query.getResultList();
	}
	
	public List<ProductSale> getProductSaleByIdProduct(Integer id){
		String sql ="select * from tbl_product_sale where product_id="+id;
		Query query = entityManager.createNativeQuery(sql, ProductSale.class);
		return query.getResultList();
	}
	
	public void setDiscountActiveByProductId(Integer id) {
		List<ProductSale> p = getProductSaleByIdProduct(id);
		long min = Long.MAX_VALUE;
		Integer idActive = null;
		for (ProductSale item : p) {
			Date start = item.getSale().getStart_date();
			Date end = item.getSale().getEnd_date();
			Calendar cal = Calendar.getInstance(); 
			
			long n = end.getTime() - cal.getTimeInMillis();
			long m = cal.getTimeInMillis() - start.getTime();
			if(n >= 0 && m >= 0 && m < min)
			{
				min = m;
				idActive = item.getId();
			}
		}
		for (ProductSale productSale : p) {
			if(productSale.getId() == idActive) {
				productSale.setActive(true);
				productSaleRepo.save(productSale);
			}
			else {
				productSale.setActive(false);
				productSaleRepo.save(productSale);
			}
		}
	}
	
	public void setDiscountActive() {
		List<ProductSale> p = getProductSale();
		for (ProductSale productSale : p) {
			setDiscountActiveByProductId(productSale.getProduct().getId());
		}
	}
	public Integer getDiscountByIdProduct(Integer id) {
		Integer discount = 0;
		List<ProductSale> ps = getProductSale();
		for (ProductSale item : ps) {
			if(item.getProduct().getId() == id && item.getActive() == true) {
				discount = item.getDiscount();
				return discount;
			}
		}
		return discount;
	}
	
	public Sale getSaleByID(Integer id) {
		return saleRepo.getOne(id);
	}
	
	public List<ProductSale> getProductSaleByIdSale(Integer id){
		String sql ="select * from tbl_product_sale where sale_id="+id;
		Query query = entityManager.createNativeQuery(sql, ProductSale.class);
		return query.getResultList();
	}
	public boolean checkP(Integer idP, Integer idS) {
		List<ProductSale> ps = productSaleRepo.findAll();
		for (ProductSale item : ps) {
			if(item.getProduct().getId() == idP && item.getSale().getId() == idS)
				return true;
		}
		return false;
	}
}
