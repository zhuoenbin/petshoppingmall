package com.ispan.dogland.model.dao.forum;

import com.ispan.dogland.model.entity.forum.ArticleComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentDao extends JpaRepository<ArticleComments,Integer> {
}
