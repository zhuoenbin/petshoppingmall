package com.ispan.dogland.entity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id")
    private Integer shoppingCartId;

    // 對應 user
    @Column(name = "user_id")
    private Integer userId;

    // 對應 product
    @Column(name = "product_id")
    private Integer productId;

    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    public ShoppingCart() {
    }

    public ShoppingCart(Integer shoppingCartId, Integer userId, Integer productId, Integer quantity, Date createdTime, Date updatedTime) {
        this.shoppingCartId = shoppingCartId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ShoppingCart{");
        sb.append("shoppingCartId=").append(shoppingCartId);
        sb.append(", userId=").append(userId);
        sb.append(", productId=").append(productId);
        sb.append(", quantity=").append(quantity);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append('}');
        return sb.toString();
    }
}