package com.ispan.dogland.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dog_birth_date")
    private Date dogBirthDate;

    private Integer dogSize;

    private String dogBreed;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    public Dog() {
    }

    public Dog(String dogName, String dogImgPathLocal, String dogImgPathCloud, String dogImgPublicId, String dogGender, String dogIntroduce, Date dogBirthDate, int dogSize, String dogBreed, Users user) {
        this.dogName = dogName;
        this.dogImgPathLocal = dogImgPathLocal;
        this.dogImgPathCloud = dogImgPathCloud;
        this.dogImgPublicId = dogImgPublicId;
        this.dogGender = dogGender;
        this.dogIntroduce = dogIntroduce;
        this.dogBirthDate = dogBirthDate;
        this.dogSize = dogSize;
        this.dogBreed = dogBreed;
        this.user = user;
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

    public Integer getDogSize() {
        return dogSize;
    }

    public void setDogSize(Integer dogSize) {
        this.dogSize = dogSize;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
        sb.append(", dogSize=").append(dogSize);
        sb.append(", dogBreed='").append(dogBreed).append('\'');
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
