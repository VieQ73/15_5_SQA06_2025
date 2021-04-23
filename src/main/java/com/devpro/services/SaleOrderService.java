package com.devpro.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.devpro.entities.Order;
import com.devpro.entities.ProductSale;
import com.devpro.entities.Sale;
import com.devpro.entities.OrderProducts;
import com.devpro.repositories.ProductSaleRepo;
import com.devpro.repositories.SaleRepo;


@Service
public class SaleOrderService {
	@PersistenceContext protected EntityManager entityManager;
	
	@Autowired
	private SaleRepo saleRepo;
	
	@Autowired
	private ProductSaleRepo productSaleRepo;
	public List<Order> searchAdmin(final Order saleOrder) {


		String sql = "select * from tbl_order where 1=1 and status <> 3 order by created_date desc";
		Query query = entityManager.createNativeQuery(sql, Order.class);
		
		return query.getResultList();
	}
	public List<Order> searchCustomer(Integer id) {


		String sql = "select * from tbl_order where created_by="+id;
		Query query = entityManager.createNativeQuery(sql, Order.class);
		
		return query.getResultList();
	}
	
	public List<Order> searchCustomerPhone(String phone, int status) {
		String sql = "select * from tbl_order where status = "+status+" and created_by=(select id from tbl_customer where phone = '"+phone+"') order by created_date desc";
		
		Query query = entityManager.createNativeQuery(sql, Order.class);
		
		return query.getResultList();
	}
	public List<OrderProducts> searchProduct(int id) {


		String sql = "select * from tbl_order_product where order_id='"+id+"'";

		
		Query query = entityManager.createNativeQuery(sql, OrderProducts.class);
		
		return query.getResultList();
	}

	public List<Sale> getSale(){
		String sql ="select * from tbl_sale where status = true";
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
			if(n >= 0 && m >= 0 && n < min)
			{
				min = n;
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
			if(item.getProduct().getId() == id) {
				discount = item.getDiscount();
			}
		}
		return discount;
	}
	
	public Sale getSaleByID(Integer id) {
		return saleRepo.getOne(id);
	}
}
