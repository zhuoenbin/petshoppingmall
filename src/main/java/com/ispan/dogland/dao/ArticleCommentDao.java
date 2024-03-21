package com.ispan.dogland.dao;

import com.ispan.dogland.model.ArticleComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentDao extends JpaRepository<ArticleComments,Integer> {
}
