package com.ispan.dogland.model.entity.mongodb;


import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "tweet_data")
public class TweetData {

    @Id
    private String id;

    private Map<String, Integer> words;

    private String timestamp;

    public TweetData() {
    }

    public TweetData(Map<String, Integer> words, String timestamp) {
        this.words = words;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Integer> getWords() {
        return words;
    }

    public void setWords(Map<String, Integer> words) {
        this.words = words;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TweetData{");
        sb.append("id='").append(id).append('\'');
        sb.append(", words=").append(words);
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
