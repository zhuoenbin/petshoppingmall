package com.ispan.dogland.model.dto;
import org.springframework.web.multipart.MultipartFile;

public class TweetDto {

    private Integer memberId;

    private String tweetContent;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
    }
}
