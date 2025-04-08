package com.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devpro.entities.News;

public interface NewsRepo extends JpaRepository<News, Integer>{

}
