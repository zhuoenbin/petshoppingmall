package com.ispan.dogland.model.dao.forum;

import com.ispan.dogland.model.entity.forum.ArticleComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleCommentDao extends JpaRepository<ArticleComments,Integer> {

    @Query("SELECT c FROM ArticleComments c JOIN c.article a ON a.articleId = :articleId")
    List<ArticleComments> findAllByArticleId(@Param("articleId") Integer articleId);
}
