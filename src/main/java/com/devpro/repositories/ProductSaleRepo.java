package com.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpro.entities.ProductSale;

public interface ProductSaleRepo extends JpaRepository<ProductSale, Integer>{

}
