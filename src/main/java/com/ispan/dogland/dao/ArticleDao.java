package com.ispan.dogland.dao;

import com.ispan.dogland.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDao extends JpaRepository<Articles, Integer>{
}
