package com.ispan.dogland.model.entity.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispan.dogland.model.entity.Users;
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
    @JsonIgnore
    private Users user;

    @JoinColumn(name="author")
    private String author;

    @Column(name="article_title")
    private String articleTitle;

    @Column(name="article_create_time")
    private Date articleCreateTime;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "commentId")
    private List<ArticleComments> comments;

    @OneToOne(fetch = FetchType.EAGER,
             cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private ArticleCategory category;

    public Articles(){}

    public Articles(Integer articleId, Users user, String author, String articleTitle, Date articleCreateTime, List<ArticleComments> comments, ArticleCategory category) {
        this.articleId = articleId;
        this.user = user;
        this.author = author;
        this.articleTitle = articleTitle;
        this.articleCreateTime = articleCreateTime;
        this.comments = comments;
        this.category = category;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public List<ArticleComments> getComments() {
        return comments;
    }

    public void setComments(List<ArticleComments> comments) {
        this.comments = comments;
    }

    public ArticleCategory getCategory() {
        return category;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
    }
}
