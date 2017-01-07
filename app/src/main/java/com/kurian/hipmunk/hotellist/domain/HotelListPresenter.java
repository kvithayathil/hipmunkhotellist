package com.kurian.hipmunk.hotellist.domain;

import com.kurian.hipmunk.hotellist.api.HotelApiService;
import com.kurian.hipmunk.hotellist.api.HotelNetworkAdapter;
import com.kurian.hipmunk.hotellist.api.HotelResponse;
import com.kurian.hipmunk.hotellist.ui.HotelListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Kurian on 07/01/2017.
 */

public class HotelListPresenter extends BasePresenter<HotelListView> {

    private final HotelApiService apiService;

    public HotelListPresenter(HotelNetworkAdapter adapter){
        this.apiService = adapter.getNetworkService(HotelApiService.class);
    }


    /**
     * Fetch hotel list from the api service, convert then supply the result to the attached view
     */
    public void getHotels() {
        apiService.fetchHotels()
                .enqueue(new Callback<HotelResponse.Container>() {
                    @Override
                    public void onResponse(Call<HotelResponse.Container> call,
                                           Response<HotelResponse.Container> response) {
                        //Response could be successful (200-300) or an error (400-500)
                        if(response.isSuccessful()) {

                            List<HotelItem> hotelItems = new ArrayList<>();

                            for(HotelResponse hotelResponse: response.body().hotels) {
                                hotelItems.add(HotelItem.builder()
                                        .hotelName(hotelResponse.hotelName)
                                        .imageUrl(hotelResponse.imageUrl)
                                        .build());
                            }

                            if(isViewBound()) {
                                getView().updateHotelList(hotelItems);
                            }

                        } else {
                            if(isViewBound()) {
                                getView().showErrorMessage(response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<HotelResponse.Container> call, Throwable t) {
                        //Failed to connect to the server (e.g. no internet connection)
                        Timber.e(t);
                        getView().showErrorMessage(t.getMessage());
                    }
                });

    }


}
