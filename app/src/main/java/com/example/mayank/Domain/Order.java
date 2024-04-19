package com.example.mayank.Domain;

public class Order {

    private String email;
    private String address;
    private String phone;
    private double amount;

    // Constructor, getters, and setters

    public Order( String email, String address, String phone, double amount) {

        this.email = email;
        this.address = address;
        this.phone = phone;
        this.amount = amount;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
