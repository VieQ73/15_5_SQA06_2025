package com.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devpro.entities.Order;
import com.devpro.entities.Customer;

@Repository
public interface UserRepo extends JpaRepository<Customer, Integer> {

}
