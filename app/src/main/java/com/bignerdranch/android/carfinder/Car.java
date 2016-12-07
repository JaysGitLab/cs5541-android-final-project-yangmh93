package com.bignerdranch.android.carfinder;

import android.location.Location;

import java.util.Date;
import java.io.Serializable;

/**
 * Created by Michael on 11/20/2016.
 */
@SuppressWarnings("serial")
public class Car implements Serializable {

    private String mType;
    private String mFloor;
    private Date mDate;

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

    public String getPhotoFilename() {
        return "IMG_" + getDate().toString() + ".jpg";
    }
}
