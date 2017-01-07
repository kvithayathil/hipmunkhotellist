package com.kurian.hipmunk.hotellist.api;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Kurian on 07/01/2017.
 */

public class HotelResponse {

    @Json(name = "image_url")
    public final String imageUrl;
    @Json(name = "name")
    public final String hotelName;

    public HotelResponse(String imageUrl, String hotelName) {
        this.imageUrl = imageUrl;
        this.hotelName = hotelName;
    }

    public static class Container {

        @Json(name = "hotels")
        public final List<HotelResponse> hotels;

        public Container(List<HotelResponse> hotels) {
            this.hotels = hotels;
        }
    }
}
