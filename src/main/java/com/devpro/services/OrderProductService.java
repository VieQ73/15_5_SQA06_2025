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
public class OrderProductService {
	@PersistenceContext protected EntityManager entityManager;
	
	@Autowired
	private SaleRepo saleRepo;
	
	public List<Order> searchAdmin(final Order saleOrder) {
		String sql = "select * from tbl_order where 1=1 and status <> 3 order by updated_date desc";
		Query query = entityManager.createNativeQuery(sql, Order.class);
		return query.getResultList();
	}
	
	public List<Order> searchCustomer(Integer id) {
		String sql = "select * from tbl_order where created_by="+id;
		Query query = entityManager.createNativeQuery(sql, Order.class);
		return query.getResultList();
	}
	
	public List<Order> searchCustomerPhone(String phone, int status) {
		String sql = "select * from tbl_order where status = "+status+" and created_by=(select id from tbl_customer where phone = '"+phone+"') order by updated_date desc";
		Query query = entityManager.createNativeQuery(sql, Order.class);
		return query.getResultList();
	}
	
	public List<OrderProducts> searchProduct(int id) {
		String sql = "select * from tbl_order_product where order_id='"+id+"'";
		Query query = entityManager.createNativeQuery(sql, OrderProducts.class);
		return query.getResultList();
	}
	
	public List<Order> findOrderByStatus(Integer status){
		String sql = "select * from tbl_order where status='"+status+"'";
		Query query = entityManager.createNativeQuery(sql, Order.class);
		return query.getResultList();
	}
	
}
