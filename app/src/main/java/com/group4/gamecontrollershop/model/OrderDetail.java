package com.group4.gamecontrollershop.model;


public class OrderDetail {
    private int id;
    private int orderId;
    private int userId;
    private String address;
    private String phone;
    private String email;

    // Constructor
    public OrderDetail(int id, int orderId, int userId, String address, String phone, String email) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getter and Setter methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

