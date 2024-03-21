package com.ispan.dogland.model.dao.forum;

import com.ispan.dogland.model.entity.forum.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCategoryDao extends JpaRepository<ArticleCategory,Integer> {
}
