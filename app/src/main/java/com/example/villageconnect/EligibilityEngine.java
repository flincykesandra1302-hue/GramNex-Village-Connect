package com.example.villageconnect;

import java.util.ArrayList;

public class EligibilityEngine {

    public static ArrayList<String> getEligibleSchemes(
            String occupation,
            int income,
            String gender,
            String state,
            String disability,
            String land,
            int age){

        ArrayList<String> schemes = new ArrayList<>();

        // Farmer
        if(occupation.equalsIgnoreCase("Farmer")
                && income <= 200000){
            schemes.add("PM-Kisan");
        }

        // Housing
        if(income <= 300000){
            schemes.add("PM Awas Yojana");
        }

        // Health
        if(income <= 500000){
            schemes.add("Ayushman Bharat");
        }

        // Women
        if(gender.equalsIgnoreCase("Female")){
            schemes.add("Women Welfare Scheme");
        }

        // Disability
        if(disability.equalsIgnoreCase("Yes")){
            schemes.add("Disabled Welfare Pension");
        }

        // Senior Citizen
        if(age >= 60){
            schemes.add("Senior Citizen Pension");
        }

        // Landless Employment
        if(land.equalsIgnoreCase("No")){
            schemes.add("MGNREGA Employment Scheme");
        }

        // Karnataka Example
        if(state.equalsIgnoreCase("Karnataka")){
            schemes.add("Karnataka Raitha Siri");
        }

        return schemes;
    }
}