package com.kurian.hipmunk.hotellist.api;

import com.kurian.hipmunk.hotellist.BuildConfig;
import com.squareup.moshi.Moshi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Kurian on 07/01/2017.
 */

public class HotelNetworkAdapter {

    private final Retrofit instance;

    public HotelNetworkAdapter() {

        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        final Moshi moshi = new Moshi.Builder().build();

        instance = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://hipmunk.com/mobile/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
    }

    public <T> T getNetworkService(Class<T> apiService) {
        return instance.create(apiService);
    }
}
