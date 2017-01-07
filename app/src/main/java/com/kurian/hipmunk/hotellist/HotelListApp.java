package com.kurian.hipmunk.hotellist;

import android.app.Application;

import com.kurian.hipmunk.hotellist.api.HotelNetworkAdapter;

import timber.log.Timber;

/**
 * Created by Kurian on 07/01/2017.
 */

public class HotelListApp extends Application {

    private HotelNetworkAdapter hotelNetworkAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    public HotelNetworkAdapter providesNetworkAdapter() {
        if(hotelNetworkAdapter != null) {
            hotelNetworkAdapter = new HotelNetworkAdapter();
        }

        return hotelNetworkAdapter;
    }
}
