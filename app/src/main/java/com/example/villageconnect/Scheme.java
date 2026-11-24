package com.example.villageconnect;


public class Scheme {

    String name, category, eligibility, benefits;

    public Scheme(String name, String category,
                  String eligibility, String benefits) {
        this.name = name;
        this.category = category;
        this.eligibility = eligibility;
        this.benefits = benefits;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getEligibility() { return eligibility; }
    public String getBenefits() { return benefits; }
}