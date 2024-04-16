package com.ispan.dogland.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="orders_cancel")
public class OrderCancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="case_id")
    private Integer caseId;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="order_id")
    private Orders orders;

    @Column(name="is_read")
    private Integer isRead;

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
