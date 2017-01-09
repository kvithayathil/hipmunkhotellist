package com.kurian.hipmunk.hotellist.domain;

import com.kurian.hipmunk.hotellist.data.HotelItem;
import com.kurian.hipmunk.hotellist.ui.hotelslistscreen.HotelListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurian on 07/01/2017.
 */

public class HotelListPresenter extends BasePresenter<HotelListView>
        implements HotelDataManager.OnLoadListener {

    private final HotelDataManager dataManager;
    private List<HotelItem> hotelItemsCache;

    public HotelListPresenter(HotelDataManager dataManager) {
        this.dataManager = dataManager;
        this.hotelItemsCache = new ArrayList<>();
    }


    /**
     * Fetch hotel list from the api service, convert then supply the result to the attached view
     */
    public void getHotels() {
        if(isViewBound()) {
            getView().showLoading(true);
        }
        dataManager.loadData(this);
    }

    @Override
    public void onLoad(List<HotelItem> items) {
        if(isViewBound()) {
            getView().updateHotelList(items);
            getView().showLoading(dataManager.isLoadingFromNetwork());
        }
    }

    @Override
    public void onError(String message) {
        if(isViewBound()) {
            getView().showErrorMessage(message);
            getView().showLoading(false);
        }
    }

    @Override
    public void onDestroy() {
        hotelItemsCache.clear();
    }
}
