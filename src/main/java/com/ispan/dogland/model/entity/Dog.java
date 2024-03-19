package com.ispan.dogland.model.entity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "dog")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dogId;
    private String dogName;
    private String dogImgPathLocal;
    private String dogImgPathCloud;
    private String dogImgPublicId;
    private String dogGender;
    private String dogIntroduce;
    private Date dogBirthDate;
    private Double dogWeight;
    private String dogBreed;
    private Integer userId;

    public Dog() {
    }

    public Dog(Integer dogId, String dogName, String dogImgPathLocal, String dogImgPathCloud, String dogImgPublicId, String dogGender, String dogIntroduce, Date dogBirthDate, Double dogWeight, String dogBreed, Integer userId) {
        this.dogId = dogId;
        this.dogName = dogName;
        this.dogImgPathLocal = dogImgPathLocal;
        this.dogImgPathCloud = dogImgPathCloud;
        this.dogImgPublicId = dogImgPublicId;
        this.dogGender = dogGender;
        this.dogIntroduce = dogIntroduce;
        this.dogBirthDate = dogBirthDate;
        this.dogWeight = dogWeight;
        this.dogBreed = dogBreed;
        this.userId = userId;
    }

    public Integer getDogId() {
        return dogId;
    }

    public void setDogId(Integer dogId) {
        this.dogId = dogId;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDogImgPathLocal() {
        return dogImgPathLocal;
    }

    public void setDogImgPathLocal(String dogImgPathLocal) {
        this.dogImgPathLocal = dogImgPathLocal;
    }

    public String getDogImgPathCloud() {
        return dogImgPathCloud;
    }

    public void setDogImgPathCloud(String dogImgPathCloud) {
        this.dogImgPathCloud = dogImgPathCloud;
    }

    public String getDogImgPublicId() {
        return dogImgPublicId;
    }

    public void setDogImgPublicId(String dogImgPublicId) {
        this.dogImgPublicId = dogImgPublicId;
    }

    public String getDogGender() {
        return dogGender;
    }

    public void setDogGender(String dogGender) {
        this.dogGender = dogGender;
    }

    public String getDogIntroduce() {
        return dogIntroduce;
    }

    public void setDogIntroduce(String dogIntroduce) {
        this.dogIntroduce = dogIntroduce;
    }

    public Date getDogBirthDate() {
        return dogBirthDate;
    }

    public void setDogBirthDate(Date dogBirthDate) {
        this.dogBirthDate = dogBirthDate;
    }

    public Double getDogWeight() {
        return dogWeight;
    }

    public void setDogWeight(Double dogWeight) {
        this.dogWeight = dogWeight;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Dog{");
        sb.append("dogId=").append(dogId);
        sb.append(", dogName='").append(dogName).append('\'');
        sb.append(", dogImgPathLocal='").append(dogImgPathLocal).append('\'');
        sb.append(", dogImgPathCloud='").append(dogImgPathCloud).append('\'');
        sb.append(", dogImgPublicId='").append(dogImgPublicId).append('\'');
        sb.append(", dogGender='").append(dogGender).append('\'');
        sb.append(", dogIntroduce='").append(dogIntroduce).append('\'');
        sb.append(", dogBirthDate=").append(dogBirthDate);
        sb.append(", dogWeight=").append(dogWeight);
        sb.append(", dogBreed='").append(dogBreed).append('\'');
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
