package com.devpro.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.devpro.entities.SaleOrder;
import com.devpro.entities.SaleOrderProducts;


@Service
public class SaleOrderService {
	@PersistenceContext protected EntityManager entityManager;

	
	public List<SaleOrder> searchAdmin(final SaleOrder saleOrder) {


		String sql = "select * from tbl_saleorder where 1=1 order by created_date desc";
		Query query = entityManager.createNativeQuery(sql, SaleOrder.class);
		
		return query.getResultList();
	}
	public List<SaleOrder> searchUser(Integer id) {


		String sql = "select * from tbl_saleorder where user_id="+id;
		Query query = entityManager.createNativeQuery(sql, SaleOrder.class);
		
		return query.getResultList();
	}
	
	public List<SaleOrder> searchUserNamme(String name) {


		String sql = "select * from tbl_saleorder where user_id=(select id from tbl_users where username = '"+name+"') order by created_date desc";
		Query query = entityManager.createNativeQuery(sql, SaleOrder.class);
		
		return query.getResultList();
	}
	public List<SaleOrder> searchProduct(int id) {


		String sql = "select * from tbl_saleorder_products where saleorder_id='"+id+"'";

		
		Query query = entityManager.createNativeQuery(sql, SaleOrderProducts.class);
		
		return query.getResultList();
	}
}
