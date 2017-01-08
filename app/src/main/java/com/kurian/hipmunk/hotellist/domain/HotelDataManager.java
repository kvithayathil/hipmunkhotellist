package com.kurian.hipmunk.hotellist.domain;

import com.kurian.hipmunk.hotellist.api.HotelApiService;
import com.kurian.hipmunk.hotellist.api.HotelNetworkAdapter;
import com.kurian.hipmunk.hotellist.api.HotelResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Manager class to handle data retrieval from memory, local persistence and network
 * Created by Kurian on 08/01/2017.
 */

public class HotelDataManager {

    private final PersistenceCache cache;
    private final HotelApiService apiService;
    private List<HotelItem> memCache;

    //Keep track of when the network was last queried
    private Long lastNetworkPull = 0L;
    private static final Long NETWORKPULL_DELAY = 1000L * 60L;

    private boolean isLoadingFromNetwork = false;

    public HotelDataManager(PersistenceCache cache, HotelNetworkAdapter networkAdapter) {
        this.cache = cache;
        this.apiService = networkAdapter.getNetworkService(HotelApiService.class);
        this.memCache = new ArrayList<>();
    }

    public void loadData(final OnLoadListener l) {

        if(l == null) {
            throw new RuntimeException("You must implement this contract to load the data");
        }

        //Fetch from the cache first
        memCache = cache.getLocalData();

        //Make sure we don't hammer the endpoint too much
        if(System.currentTimeMillis() - lastNetworkPull > NETWORKPULL_DELAY) {
            isLoadingFromNetwork = true;
            apiService.fetchHotels()
                    .enqueue(new Callback<HotelResponse.Container>() {
                        @Override
                        public void onResponse(Call<HotelResponse.Container> call, Response<HotelResponse.Container> response) {
                            isLoadingFromNetwork = false;

                            if (response.isSuccessful()) {
                                memCache = new ArrayList<>();
                                for (HotelResponse hotelResponse : response.body().hotels) {
                                    memCache.add(HotelItem.builder()
                                            .hotelName(hotelResponse.hotelName)
                                            .imageUrl(hotelResponse.imageUrl)
                                            .build());
                                }
                                cache.saveLocalData(memCache);
                                l.onLoad(memCache);
                            } else {
                                Timber.e(response.message());
                                l.onError(response.message());
                            }

                            lastNetworkPull = System.currentTimeMillis();
                        }

                        @Override
                        public void onFailure(Call<HotelResponse.Container> call, Throwable t) {
                            isLoadingFromNetwork = false;
                            Timber.e(t);
                            l.onError(t.getMessage());
                        }
                    });
        }

        l.onLoad(memCache);
    }

    /**
     * Check if the network request is still running
     * @return true if it's waiting on a network response
     */
    public boolean isLoadingFromNetwork() {
        return isLoadingFromNetwork;
    }

    interface OnLoadListener {

        /**
         * Populate with results
         * @param items data to populate
         */
        void onLoad(List<HotelItem> items);

        void onError(String message);
    }
}
