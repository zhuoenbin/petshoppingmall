package com.ispan.dogland.model.entity.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispan.dogland.model.entity.tweet.Tweet;
import jakarta.persistence.*;

@Entity
@Table(name = "article_gallery")
public class ArticleGallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imgId;


    private String imgPath;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "article_id")
    @JsonIgnore
    private Articles article;

    public ArticleGallery() {
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Articles getArticle() {
        return article;
    }

    public void setArticle(Articles article) {
        this.article = article;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ArticleGallery{");
        sb.append("imgId=").append(imgId);
        sb.append(", imgPath='").append(imgPath).append('\'');
        sb.append(", article=").append(article);
        sb.append('}');
        return sb.toString();
    }
}
