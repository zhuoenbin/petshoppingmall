package com.ispan.dogland.dao;

import com.ispan.dogland.model.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCategoryDao extends JpaRepository<ArticleCategory,Integer> {
}
