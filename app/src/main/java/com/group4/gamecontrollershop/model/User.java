package com.group4.gamecontrollershop.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String avatarUrl;
    private String address;
    private String phone;

    public User() {
    }

    public User(int id, String username, String password, String avatarUrl, String address, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
}
