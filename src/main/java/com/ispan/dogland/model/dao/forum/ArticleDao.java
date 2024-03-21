package com.ispan.dogland.model.dao.forum;

import com.ispan.dogland.model.entity.forum.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDao extends JpaRepository<Articles, Integer>{
}
