package com.intelligentz.sahasara.model;

/**
 * Created by Lakshan on 2017-03-24.
 */
public class Bus {
    private String name;
    private String number;
    private String time;
    private double longitude;
    private double latitude;
    private int state;
    private int lastDestination;
    private String busRouteId;
    // 1 - on,  0 - off
    private Schedule schedule;
    

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Bus() {
    }

    public Bus(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Bus(String name, String number, String time, double longitude, double latitude, int state, int lastDestination) {
        this.name = name;
        this.number = number;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.state = state;
        this.lastDestination = lastDestination;
    }

    public Bus(String name, String number, String time, double longitude, double latitude, int state, int lastDestination, String busRouteId) {
        this.name = name;
        this.number = number;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.state = state;
        this.lastDestination = lastDestination;
        this.busRouteId = busRouteId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getBusRouteId() {
        return busRouteId;
    }

    public void setBusRouteId(String busRouteId) {
        this.busRouteId = busRouteId;
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

    public int getLastDestination() {
        return lastDestination;
    }

    public void setLastDestination(int lastDestination) {
        this.lastDestination = lastDestination;
    }
}
