package com.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpro.entities.Gift;

public interface GiftRepo extends JpaRepository<Gift, Integer>{

}
