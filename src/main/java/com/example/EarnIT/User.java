package com.example.EarnIT;

public class User {

    private String key;
    private String name;
    private String email;
    private String password;
    private String phone;
    private boolean poster;

    public User(){

    }

    public User(String key, String name, String email, String password,String phone,boolean permission) {
        this.key = key;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.poster = permission;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getPermission() {
        return poster;
    }

    public void setPermission(boolean permission) {
        this.poster = permission;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
