package com.bignerdranch.android.carfinder;

import java.util.Date;

/**
 * Created by Michael on 11/20/2016.
 */
public class Car {
    private Date mDate;
    private double mLat;
    private double mLon;

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
}
