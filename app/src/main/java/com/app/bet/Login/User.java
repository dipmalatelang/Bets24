package com.app.bet.Login;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

    private String id;
    private String username;
    private String email;
    private String gmail;
    private String name , phone, mobileCode;


    public User() {
    }
    public User(String id, String username, String email, String gmail, String name, String phone, String mobileCode) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.gmail = gmail;
        this.phone = phone;
        this.mobileCode = mobileCode;
    }

    public String getGmail(){
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
