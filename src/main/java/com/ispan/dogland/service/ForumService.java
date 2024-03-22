package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.forum.ArticleCategoryRepository;
import com.ispan.dogland.model.dao.forum.ArticleCommentRepository;
import com.ispan.dogland.model.dao.forum.ArticleRepository;
import com.ispan.dogland.model.dto.ArticleDto;
import com.ispan.dogland.model.entity.forum.ArticleComments;
import com.ispan.dogland.model.entity.forum.Articles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    public Page<ArticleDto> showArticlesByPages(Integer pageNumber){
        Sort sortByTime = Sort.by(Sort.Direction.ASC,"articleCreateTime");

        Page<Articles> articles = articleRepository.findAll(PageRequest.of(pageNumber, 6,sortByTime));

        Page<ArticleDto> articleDtos = articles.map(a -> {
            ArticleDto aDto = new ArticleDto();
            BeanUtils.copyProperties(a,aDto);
            return aDto;
        });
        return articleDtos;
    }

    public List<Articles> authorOfArticles(Integer userId){
        return articleRepository.findAllByUserId(userId);
    }

    public Articles addNewArticle(Articles a){
        return articleRepository.save(a);
    }

    public Articles editArticle(Articles a){
        return  articleRepository.saveAndFlush(a);
    }

    public List<ArticleComments> showCommentsOfArticle(Integer articleId){
        return articleCommentRepository.findAllByArticleId(articleId);
    }


}
