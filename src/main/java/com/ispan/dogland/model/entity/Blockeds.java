package com.ispan.dogland.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="blockeds")
public class Blockeds {

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private com.ispan.dogland.model.Users user;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id", referencedColumnName = "user_id")
    private com.ispan.dogland.model.Users blocked;

    public Blockeds(){}

    public Blockeds(com.ispan.dogland.model.Users user, com.ispan.dogland.model.Users blocked) {
        this.user = user;
        this.blocked = blocked;
    }

    public com.ispan.dogland.model.Users getUser() {
        return user;
    }

    public void setUser(com.ispan.dogland.model.Users user) {
        this.user = user;
    }

    public com.ispan.dogland.model.Users getBlocked() {
        return blocked;
    }

    public void setBlocked(Users blocked) {
        this.blocked = blocked;
    }
}
