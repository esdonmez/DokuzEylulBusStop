package com.esdonmez.esd.dokuzeylulbusstop.Models;

import java.io.Serializable;

public class StopModel implements Serializable {
    private Integer Id;
    private String Name;
    private String Latitude;
    private String Longitude;
    private int Like;
    private int Vote;


    public StopModel() {}


    public StopModel(Integer id, String name, String latitude, String longitude, int like, int vote) {
        Id = id;
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
        Like = like;
        Vote = vote;
    }


    public StopModel(String name, String latitude, String longitude, int like, int vote) {
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
        Like = like;
        Vote = vote;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public int getVote() {
        return Vote;
    }

    public void setVote(int vote) {
        Vote = vote;
    }
}
