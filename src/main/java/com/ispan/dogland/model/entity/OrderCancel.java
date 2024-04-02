package com.ispan.dogland.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="order_cancel")
public class OrderCancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="case_id")
    private Integer caseId;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Column(name="order_id")
    private Orders orders;

    @Column(name="is_read")
    private Integer isRead;
}
