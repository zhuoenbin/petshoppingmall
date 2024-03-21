package com.ispan.dogland.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name="articles")
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @Column(name="article_title")
    private String articleTitle;

    @Column(name="article_content")
    private String articleContent;

    @Column(name="article_create_time")
    private Date articleCreateTime;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "user")
    private List<ArticleComments> comments;

    @OneToOne(fetch = FetchType.EAGER,
             cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private ArticleCategory category;

    public Articles(){}

    public Articles(Integer articleId, Users user, String articleTitle, String articleContent, Date articleCreateTime, List<ArticleComments> comments) {
        this.articleId = articleId;
        this.user = user;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.articleCreateTime = articleCreateTime;
        this.comments = comments;
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

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Date getArticleCreateTime() {
        return articleCreateTime;
    }

    public void setArticleCreateTime(Date articleCreateTime) {
        this.articleCreateTime = articleCreateTime;
    }

    public List<ArticleComments> getComments() {
        return comments;
    }

    public void setComments(List<ArticleComments> comments) {
        this.comments = comments;
    }
}
