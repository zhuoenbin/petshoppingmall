package com.ispan.dogland.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispan.dogland.model.entity.tweet.Tweet;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String lastName;

    private String firstName;

    private String userEmail;

    private String userPassword;

    private String userGender;

    private Date birthDate;

    private Integer userViolationCount;

    private Date lastLoginTime;

    private String userImgPath;

    private String imgPublicId;

    private String userStatus;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dog> dogs;


    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tweet> tweets;


    @PrePersist //在物件轉換到persistent狀態以前，做這個function
    public void onCreate() {
        if(lastLoginTime==null ) {
            lastLoginTime=new Date();
        }
    }

    public Users() {
    }

    public Users(String lastName, String firstName, String userEmail, String userPassword, String userGender, Date birthDate, Integer userViolationCount, Date lastLoginTime, String userStatus) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userGender = userGender;
        this.birthDate = birthDate;
        this.userViolationCount = userViolationCount;
        this.lastLoginTime = lastLoginTime;
        this.userStatus = userStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getUserViolationCount() {
        return userViolationCount;
    }

    public void setUserViolationCount(Integer userViolationCount) {
        this.userViolationCount = userViolationCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }



    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public String getUserImgPath() {
        return userImgPath;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }

    public String getImgPublicId() {
        return imgPublicId;
    }

    public void setImgPublicId(String imgPublicId) {
        this.imgPublicId = imgPublicId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Users{");
        sb.append("userId=").append(userId);
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", userEmail='").append(userEmail).append('\'');
        sb.append(", userPassword='").append(userPassword).append('\'');
        sb.append(", userGender='").append(userGender).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", userViolationCount=").append(userViolationCount);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", userImgPath='").append(userImgPath).append('\'');
        sb.append(", imgPublicId='").append(imgPublicId).append('\'');
        sb.append(", userStatus='").append(userStatus).append('\'');
//        sb.append(", dogs=").append(dogs);
        sb.append('}');
        return sb.toString();
    }
}
