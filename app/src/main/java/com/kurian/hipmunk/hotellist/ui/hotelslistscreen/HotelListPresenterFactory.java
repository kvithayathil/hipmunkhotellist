package com.kurian.hipmunk.hotellist.ui.hotelslistscreen;

import android.content.SharedPreferences;

import com.kurian.hipmunk.hotellist.api.HotelNetworkAdapter;
import com.kurian.hipmunk.hotellist.domain.HotelDataManager;
import com.kurian.hipmunk.hotellist.domain.HotelListPresenter;
import com.kurian.hipmunk.hotellist.domain.PreferenceCache;
import com.kurian.hipmunk.hotellist.domain.PresenterFactory;

/**
 * Created by Kurian on 08/01/2017.
 */

public class HotelListPresenterFactory implements PresenterFactory<HotelListPresenter> {

    private HotelDataManager dataManager;

    public HotelListPresenterFactory(SharedPreferences sharedPreferences,
                                     HotelNetworkAdapter networkAdapter) {
        this.dataManager = new HotelDataManager(new PreferenceCache(sharedPreferences),
                networkAdapter);
    }

    @Override
    public HotelListPresenter create() {
        return new HotelListPresenter(dataManager);
    }
}
