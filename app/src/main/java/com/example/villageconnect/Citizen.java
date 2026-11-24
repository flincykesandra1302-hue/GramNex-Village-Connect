package com.example.villageconnect;

public class Citizen {

    String name, phone, occupation, district;

    public Citizen(String name, String phone,
                   String occupation, String district){
        this.name = name;
        this.phone = phone;
        this.occupation = occupation;
        this.district = district;
    }

    public String getName(){ return name; }
    public String getPhone(){ return phone; }
    public String getOccupation(){ return occupation; }
    public String getDistrict(){ return district; }
}