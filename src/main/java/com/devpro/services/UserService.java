package com.devpro.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;


import com.devpro.entities.Customer;

@Service
public class UserService {
	@PersistenceContext protected EntityManager entityManager;
	public List<Customer> searchUser(final Customer user) {
		String sql = "select * from tbl_customer where 1=1";
		Query query = entityManager.createNativeQuery(sql, Customer.class);
		return query.getResultList();
	}
	public List<Customer> searCustomerByPhone(String phone){
		String sql = "select * from tbl_customer where phone='"+phone+"' limit 1;";
		System.out.println(sql);
		Query query = entityManager.createNativeQuery(sql, Customer.class);
		return query.getResultList();
	}
}
