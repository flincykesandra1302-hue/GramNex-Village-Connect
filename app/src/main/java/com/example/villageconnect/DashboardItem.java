package com.example.villageconnect;

public class DashboardItem {

    private final String title;
    private final String subtitle;
    private final int image;

    public DashboardItem(String title, String subtitle, int image){
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
    }

    public String getTitle(){
        return title;
    }

    public String getSubtitle(){
        return subtitle;
    }

    public int getImage(){
        return image;
    }
}