package com.esdonmez.esd.dokuzeylulbusstop.Models;

import java.io.Serializable;

public class StopModel implements Serializable {
    private String Name;
    private String Latitude;
    private String Longitude;
    private int Like;
    private int Vote;


    public StopModel(String name, String latitude, String longitude, int like, int vote) {
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
        Like = like;
        Vote = vote;
    }


    public String getName() {
        return Name;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public int getLike() {
        return Like;
    }

    public int getVote() {
        return Vote;
    }
}
