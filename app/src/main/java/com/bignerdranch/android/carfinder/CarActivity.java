package com.bignerdranch.android.carfinder;

import android.support.v4.app.Fragment;

/**
 * Created by Michael on 12/5/2016.
 */
public class CarActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new CarFragment();
    }
}
