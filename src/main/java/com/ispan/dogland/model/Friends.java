package com.ispan.dogland.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="friends")
public class Friends {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", referencedColumnName = "user_id")
    private Users friend;

    @Column(name = "become_friend_time")
    private Date becomeFriendTime;

    public Friends(Users user, Users friend, Date becomeFriendTime) {
        this.user = user;
        this.friend = friend;
        this.becomeFriendTime = becomeFriendTime;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getFriend() {
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
