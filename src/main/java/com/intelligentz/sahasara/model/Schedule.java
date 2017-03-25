/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelligentz.sahasara.model;

/**
 *
 * @author Heshan
 */
public class Schedule {
    private int busId;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;

    public Schedule(int busId) {
        this.busId = busId;
        this.mon = false;
        this.tue = false;
        this.wed = false;
        this.thu = false;
        this.fri = false;
        this.sat = false;
        this.sun = false;
    }
    
    

    /**
     * @return the busId
     */
    public int getBusId() {
        return busId;
    }

    /**
     * @param busId the busId to set
     */
    public void setBusId(int busId) {
        this.busId = busId;
    }

    /**
     * @return the mon
     */
    public boolean isMon() {
        return mon;
    }

    /**
     * @param mon the mon to set
     */
    public void setMon(boolean mon) {
        this.mon = mon;
    }

    /**
     * @return the tue
     */
    public boolean isTue() {
        return tue;
    }

    /**
     * @param tue the tue to set
     */
    public void setTue(boolean tue) {
        this.tue = tue;
    }

    /**
     * @return the wed
     */
    public boolean isWed() {
        return wed;
    }

    /**
     * @param wed the wed to set
     */
    public void setWed(boolean wed) {
        this.wed = wed;
    }

    /**
     * @return the thu
     */
    public boolean isThu() {
        return thu;
    }

    /**
     * @param thu the thu to set
     */
    public void setThu(boolean thu) {
        this.thu = thu;
    }

    /**
     * @return the fri
     */
    public boolean isFri() {
        return fri;
    }

    /**
     * @param fri the fri to set
     */
    public void setFri(boolean fri) {
        this.fri = fri;
    }

    /**
     * @return the sat
     */
    public boolean isSat() {
        return sat;
    }

    /**
     * @param sat the sat to set
     */
    public void setSat(boolean sat) {
        this.sat = sat;
    }

    /**
     * @return the sun
     */
    public boolean isSun() {
        return sun;
    }

    /**
     * @param sun the sun to set
     */
    public void setSun(boolean sun) {
        this.sun = sun;
    }
    
    
    
}
