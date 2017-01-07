package com.kurian.hipmunk.hotellist.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kurian on 07/01/2017.
 */

public interface HotelApiService {

    @GET("coding_challenge")
    Call<HotelResponse.Container> fetchHotels();
}
