package com.ispan.dogland.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="friends")
public class Friends {

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private com.ispan.dogland.model.Users user;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friend_id", referencedColumnName = "user_id")
    private com.ispan.dogland.model.Users friend;

    @Column(name = "become_friend_time")
    private Date becomeFriendTime;

    public Friends(com.ispan.dogland.model.Users user, com.ispan.dogland.model.Users friend, Date becomeFriendTime) {
        this.user = user;
        this.friend = friend;
        this.becomeFriendTime = becomeFriendTime;
    }

    public com.ispan.dogland.model.Users getUser() {
        return user;
    }

    public void setUser(com.ispan.dogland.model.Users user) {
        this.user = user;
    }

    public com.ispan.dogland.model.Users getFriend() {
        return friend;
    }

    public void setFriend(Users friend) {
        this.friend = friend;
    }

    public Date getBecomeFriendTime() {
        return becomeFriendTime;
    }

    public void setBecomeFriendTime(Date becomeFriendTime) {
        this.becomeFriendTime = becomeFriendTime;
    }
}
