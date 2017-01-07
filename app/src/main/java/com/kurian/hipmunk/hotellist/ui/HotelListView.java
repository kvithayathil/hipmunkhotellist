package com.kurian.hipmunk.hotellist.ui;

import com.kurian.hipmunk.hotellist.domain.HotelItem;

import java.util.List;

/**
 * Created by Kurian on 07/01/2017.
 */

public interface HotelListView extends MvpView {

    void updateHotelList(List<HotelItem> hotelItems);

    void showErrorMessage(String errorMessage);
}
