package com.ispan.dogland.model;

import jakarta.persistence.*;

@Entity
@Table(name="article_category_match")
public class ArticleCategoryMatch {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private Articles article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private  ArticleCategory category;

    public ArticleCategoryMatch(){}

    public ArticleCategoryMatch(Articles article, ArticleCategory category) {
        this.article = article;
        this.category = category;
    }

    public Articles getArticle() {
        return article;
    }

    public void setArticle(Articles article) {
        this.article = article;
    }

    public ArticleCategory getCategory() {
        return category;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
    }
}
