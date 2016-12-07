package com.bignerdranch.android.carfinder;

import java.util.Date;

/**
 * Created by Michael on 11/20/2016.
 */
public class Car {
    private String mType;
    private String mFloor;
    private Date mDate;
    private double mLat;
    private double mLon;

    public Car() {
        mDate = new Date();
    }
    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getFloor() {
        return mFloor;
    }

    public void setFloor(String floor) {
        mFloor = floor;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLon() {
        return mLon;
    }

    public void setLon(double lon) {
        mLon = lon;
    }

    public String getPhotoFilename() {
        return "IMG_" + getDate().toString() + ".jpg";
    }
}
