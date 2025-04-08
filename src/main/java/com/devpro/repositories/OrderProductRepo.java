package com.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpro.entities.OrderProducts;

public interface OrderProductRepo extends JpaRepository<OrderProducts, Integer>{

}
