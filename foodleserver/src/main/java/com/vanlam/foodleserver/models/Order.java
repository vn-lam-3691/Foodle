package com.vanlam.foodleserver.models;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String receiverName;
    private String receiverPhoneNumber;
    private String receiverAddress;
    private List<Cart> orderList;
    private String orderNote;
    private double totalPayment;
    private String orderTime;
    private String userId;
    private String orderStatus;

    public Order() {
    }

    public Order(String receiverName, String receiverPhoneNumber, String receiverAddress, List<Cart> orderList, String orderNote, double totalPayment, String orderTime, String userId, String orderStatus) {
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.receiverAddress = receiverAddress;
        this.orderList = orderList;
        this.orderNote = orderNote;
        this.totalPayment = totalPayment;
        this.orderTime = orderTime;
        this.userId = userId;
        this.orderStatus = orderStatus;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public List<Cart> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Cart> orderList) {
        this.orderList = orderList;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
