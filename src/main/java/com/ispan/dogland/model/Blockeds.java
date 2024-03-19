package com.ispan.dogland.model;

import jakarta.persistence.*;

@Entity
@Table(name="blockeds")
public class Blockeds {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id", referencedColumnName = "user_id")
    private Users blocked;

    public Blockeds(){}

    public Blockeds(Users user, Users blocked) {
        this.user = user;
        this.blocked = blocked;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getBlocked() {
        return blocked;
    }

    public void setBlocked(Users blocked) {
        this.blocked = blocked;
    }
}
