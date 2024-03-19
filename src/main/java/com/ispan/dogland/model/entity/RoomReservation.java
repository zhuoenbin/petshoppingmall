package com.ispan.dogland.model.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "room_reservation")
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="room_id")
    private Room room;

    private Integer dogId;

    private Date startTime;

    private Date endTime;

    private Integer totalPrice;

    private Date reservationTime;

    private Date cancelTime;

    private String cancelDirection;

    private String paymentMethod;

    private String paymentStatus;

    public RoomReservation() {
    }

    public RoomReservation(Long reservationId, Room room, Integer dogId, Date startTime, Date endTime, Integer totalPrice, Date reservationTime, Date cancelTime, String cancelDirection, String paymentMethod, String paymentStatus) {
        this.reservationId = reservationId;
        this.room = room;
        this.dogId = dogId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.reservationTime = reservationTime;
        this.cancelTime = cancelTime;
        this.cancelDirection = cancelDirection;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getDogId() {
        return dogId;
    }

    public void setDogId(Integer dogId) {
        this.dogId = dogId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelDirection() {
        return cancelDirection;
    }

    public void setCancelDirection(String cancelDirection) {
        this.cancelDirection = cancelDirection;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoomReservation{");
        sb.append("reservationId=").append(reservationId);
        sb.append(", room=").append(room);
        sb.append(", dogId=").append(dogId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", reservationTime=").append(reservationTime);
        sb.append(", cancelTime=").append(cancelTime);
        sb.append(", cancelDirection='").append(cancelDirection).append('\'');
        sb.append(", paymentMethod='").append(paymentMethod).append('\'');
        sb.append(", paymentStatus='").append(paymentStatus).append('\'');
        sb.append('}');
        return sb.toString();
    }
}