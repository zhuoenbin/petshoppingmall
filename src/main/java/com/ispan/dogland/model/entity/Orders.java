package com.ispan.dogland.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    // 對應 User
//    @ManyToOne(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "user_id")
    private Integer userId;

    // 對應 Employee
//    @ManyToOne(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "employee_id")
    private Integer employeeId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date orderDate;

    private Integer totalPrice;

    private Integer discountPrice;

    private Integer couponPrice;

    private String discountDescription;

    private String couponDescription;

    private Integer paymentMethod;

    private Integer paymentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date confirmDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date shipDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date logisticsShipDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date logisticsArrivalDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date userReceiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date userConfirmDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date orderCancelDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="shipping_company_id")
    private ShippingCompany shippingCompany;

    private String city;

    private String district;

    private String address;

    private Integer freight;

    @OneToMany(mappedBy = "order",
            fetch = FetchType.LAZY ,
            cascade = {CascadeType.ALL})
    // 序列化時省略掉這個物件
    @JsonIgnore
    private List<OrderDetail> orderDetail;

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public void add(OrderDetail tmpOrderDetail){
        if(orderDetail==null){
            orderDetail = new ArrayList<>();
        }
        orderDetail.add(tmpOrderDetail);

        tmpOrderDetail.setOrder(this);
    }

    public Orders() {
    }

    public Orders(Integer orderId, Integer userId, Integer employeeId, Date orderDate, Integer totalPrice, Integer discountPrice, Integer couponPrice, String discountDescription, String couponDescription, Integer paymentMethod, Integer paymentStatus, Date confirmDate, Date shipDate, Date logisticsShipDate, Date logisticsArrivalDate, Date userReceiveDate, Date userConfirmDate, Date orderCancelDate, ShippingCompany shippingCompany, String city, String district, String address, Integer freight) {
        this.orderId = orderId;
        this.userId = userId;
        this.employeeId = employeeId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.couponPrice = couponPrice;
        this.discountDescription = discountDescription;
        this.couponDescription = couponDescription;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.confirmDate = confirmDate;
        this.shipDate = shipDate;
        this.logisticsShipDate = logisticsShipDate;
        this.logisticsArrivalDate = logisticsArrivalDate;
        this.userReceiveDate = userReceiveDate;
        this.userConfirmDate = userConfirmDate;
        this.orderCancelDate = orderCancelDate;
        this.shippingCompany = shippingCompany;
        this.city = city;
        this.district = district;
        this.address = address;
        this.freight = freight;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Integer couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Date getLogisticsShipDate() {
        return logisticsShipDate;
    }

    public void setLogisticsShipDate(Date logisticsShipDate) {
        this.logisticsShipDate = logisticsShipDate;
    }

    public Date getLogisticsArrivalDate() {
        return logisticsArrivalDate;
    }

    public void setLogisticsArrivalDate(Date logisticsArrivalDate) {
        this.logisticsArrivalDate = logisticsArrivalDate;
    }

    public Date getUserReceiveDate() {
        return userReceiveDate;
    }

    public void setUserReceiveDate(Date userReceiveDate) {
        this.userReceiveDate = userReceiveDate;
    }

    public Date getUserConfirmDate() {
        return userConfirmDate;
    }

    public void setUserConfirmDate(Date userConfirmDate) {
        this.userConfirmDate = userConfirmDate;
    }

    public Date getOrderCancelDate() {
        return orderCancelDate;
    }

    public void setOrderCancelDate(Date orderCancelDate) {
        this.orderCancelDate = orderCancelDate;
    }

    public ShippingCompany getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(ShippingCompany shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Orders{");
        sb.append("orderId=").append(orderId);
        sb.append(", userId=").append(userId);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", discountPrice=").append(discountPrice);
        sb.append(", couponPrice=").append(couponPrice);
        sb.append(", discountDescription='").append(discountDescription).append('\'');
        sb.append(", couponDescription='").append(couponDescription).append('\'');
        sb.append(", paymentMethod=").append(paymentMethod);
        sb.append(", paymentStatus=").append(paymentStatus);
        sb.append(", confirmDate=").append(confirmDate);
        sb.append(", shipDate=").append(shipDate);
        sb.append(", logisticsShipDate=").append(logisticsShipDate);
        sb.append(", logisticsArrivalDate=").append(logisticsArrivalDate);
        sb.append(", userReceiveDate=").append(userReceiveDate);
        sb.append(", userConfirmDate=").append(userConfirmDate);
        sb.append(", orderCancelDate=").append(orderCancelDate);
        sb.append(", shippingCompany=").append(shippingCompany);
        sb.append(", city='").append(city).append('\'');
        sb.append(", district='").append(district).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", freight=").append(freight);
        sb.append('}');
        return sb.toString();
    }

}
