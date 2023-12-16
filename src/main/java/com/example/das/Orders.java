package com.example.das;

public class Orders {
    String id, client, emp, date, shop, address;

    public Orders(String id, String client, String emp, String date,
                  String shop, String address) {
        this.id = id;
        this.client = client;
        this.emp = emp;
        this.date = date;
        this.shop = shop;
        this.address = address;

    }

    public Orders(){

    }

    public String getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public String getEmp() {
        return emp;
    }

    public String getDate() {
        return date;
    }

    public String getShop() {
        return shop;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
