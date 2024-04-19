package com.example.mayank.Domain;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String Firstname;
    private String email;


    public UserModel(){

    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
