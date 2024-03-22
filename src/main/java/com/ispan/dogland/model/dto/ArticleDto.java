package com.ispan.dogland.model.dto;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.forum.ArticleCategory;

import java.sql.Date;

public class ArticleDto {

    private Integer articleId;
    private Users user;
    private String articleTitle;
    private Date articleCreateTime;
    private ArticleCategory category;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Date getArticleCreateTime() {
        return articleCreateTime;
    }

    public void setArticleCreateTime(Date articleCreateTime) {
        this.articleCreateTime = articleCreateTime;
    }

    public ArticleCategory getCategory() {
        return category;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
    }
}