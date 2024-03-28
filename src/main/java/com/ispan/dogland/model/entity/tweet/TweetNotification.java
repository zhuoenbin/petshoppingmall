package com.ispan.dogland.model.entity.tweet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tweet_notification")
public class TweetNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tweetNotiId;
    private Date postTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                                                  CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;
    private String content;
    private Integer tweetId;
    private Integer isRead;


    public TweetNotification() {
    }

    public TweetNotification(Date postTime, Users user, String content, Integer tweetId, Integer isRead) {
        this.postTime = postTime;
        this.user = user;
        this.content = content;
        this.tweetId = tweetId;
        this.isRead = isRead;
    }

    public Integer getTweetNotiId() {
        return tweetNotiId;
    }

    public void setTweetNotiId(Integer tweetNotiId) {
        this.tweetNotiId = tweetNotiId;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTweetId() {
        return tweetId;
    }

    public void setTweetId(Integer tweetId) {
        this.tweetId = tweetId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TweetNotification{");
        sb.append("tweetNotiId=").append(tweetNotiId);
        sb.append(", postTime=").append(postTime);
        sb.append(", user=").append(user);
        sb.append(", content='").append(content).append('\'');
        sb.append(", tweetId=").append(tweetId);
        sb.append(", isRead=").append(isRead);
        sb.append('}');
        return sb.toString();
    }
}