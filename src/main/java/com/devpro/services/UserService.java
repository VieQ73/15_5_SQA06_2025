package com.devpro.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;


import com.devpro.entities.User;

@Service
public class UserService {
	@PersistenceContext protected EntityManager entityManager;
	public List<User> searchUser(final User user) {
		String sql = "select * from tbl_users where 1=1";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return query.getResultList();
	}
	public List<User> searUserByPhone(String phone){
		String sql = "select * from tbl_users where phone='"+phone+"' limit 1;";
		System.out.println(sql);
		Query query = entityManager.createNativeQuery(sql, User.class);
		return query.getResultList();
	}
}
