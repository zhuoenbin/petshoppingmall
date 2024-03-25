package com.ispan.dogland.model.dto;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.forum.ArticleCategory;

import java.sql.Date;

public class ForumDto {

    private Integer articleId;
    private Users user;
    private String articleTitle;
    private Date articleCreateTime;
    private ArticleCategory category;
    private String commentContent;
    private Date commentCreateTime;

    public ForumDto(){}

    public ForumDto(Integer articleId, Users user, String articleTitle, Date articleCreateTime, ArticleCategory category) {
        this.articleId = articleId;
        this.user = user;
        this.articleTitle = articleTitle;
        this.articleCreateTime = articleCreateTime;
        this.category = category;
    }

    public ForumDto(Users user, String commentContent, Date commentCreateTime) {
        this.user = user;
        this.commentContent = commentContent;
        this.commentCreateTime = commentCreateTime;
    }

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

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentCreateTime() {
        return commentCreateTime;
    }

    public void setCommentCreateTime(Date commentCreateTime) {
        this.commentCreateTime = commentCreateTime;
    }
}