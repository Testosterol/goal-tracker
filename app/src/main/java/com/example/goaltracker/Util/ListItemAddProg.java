package com.example.goaltracker.Util;

public class ListItemAddProg {
    String name;
    int logo;

    public ListItemAddProg(String name, int logo) {
        this.name = name;
        this.logo = logo;
    }

    public void setData(String name, int logo) {
        this.name = name;
        this.logo = logo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLogo() {
        return logo;
    }
    public void setLogo(int logo) {
        this.logo = logo;
    }
}