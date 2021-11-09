package com.example.cobalagi.model;

public class UserInformation {

    public String name;
    public String lat;
    public String lng;

    public UserInformation() {               //default constructor which invokes on object creation of respective class in MainActivity.java

    }

    public UserInformation(String name, String lat, String lng) {    //parameterized constructor which will store the retrieved data from firebase
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
}