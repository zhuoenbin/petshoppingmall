package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.forum.ArticleCategoryDao;
import com.ispan.dogland.model.dao.forum.ArticleCommentDao;
import com.ispan.dogland.model.dao.forum.ArticleDao;
import com.ispan.dogland.model.entity.forum.ArticleComments;
import com.ispan.dogland.model.entity.forum.Articles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    @Autowired
    public ArticleDao articleDao;

    @Autowired
    public ArticleCommentDao articleCommentDao;

    @Autowired
    public ArticleCategoryDao articleCategoryDao;

    public List<Articles> showAllArticles(){
        return articleDao.findAllOrderByTime();
    }

    public List<Articles> autherOfArticles(Integer userId){
        return articleDao.findAllByUserId(userId);
    }

    public List<ArticleComments> showCommentsOfArticle(Integer articleId){
        return articleCommentDao.findAllByArticleId(articleId);
    }
}
