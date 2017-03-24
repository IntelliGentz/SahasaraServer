package com.intelligentz.sahasara.model;

/**
 * Created by Lakshan on 2017-03-24.
 */
public class Bus {
    private String name;
    private String number;
    private long time;
    private double longitude;
    private double latitude;
    private int state;
    private City lastDestination;
    // 1 - on,  0 - off


    public Bus() {
    }

    public Bus(String name, String number, long time, double longitude, double latitude, int state, City lastDestination) {
        this.name = name;
        this.number = number;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.state = state;
        this.lastDestination = lastDestination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public City getLastDestination() {
        return lastDestination;
    }

    public void setLastDestination(City lastDestination) {
        this.lastDestination = lastDestination;
    }
}
