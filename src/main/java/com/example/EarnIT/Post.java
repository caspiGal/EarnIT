package com.example.EarnIT;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Post
{
    public String description,price,email,phone,posterId;
    public ArrayList<String> applyList;

    public Post(){}

    public Post(String posterId,String description, String price,String email,String phone) {
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.email = email;
        this.posterId = posterId;
        applyList = new ArrayList<>();
    }

    public void addApply(String uid){
        if(applyList.size() <= 4) {
            applyList.add(uid);
        }
    }

    public ArrayList<String> getApplyList() {
        return applyList;
    }

    public void setApplyList(ArrayList<String> applyList) {
        this.applyList = applyList;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
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