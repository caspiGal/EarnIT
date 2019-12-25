package com.example.EarnIT;

public class Post
{
    public String description,price,email,phone;

    public Post(){}

    public Post(String description, String price,String email,String phone) {
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }
}