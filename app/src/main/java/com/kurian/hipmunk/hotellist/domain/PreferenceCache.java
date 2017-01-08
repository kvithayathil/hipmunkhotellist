package com.kurian.hipmunk.hotellist.domain;

import android.content.SharedPreferences;

import com.kurian.hipmunk.hotellist.api.HotelResponse;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

/**
 * Simple implementation of a Cache manager using sharedprefs
 * Created by Kurian on 08/01/2017.
 */

public class PreferenceCache implements PersistenceCache {

    private final SharedPreferences sharedPreferences;

    private static final String KEY_CACHE_ITEMS = "list_cache";

    private final JsonAdapter<List<HotelResponse>> jsonAdapter;

    public PreferenceCache(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        Type hotelList = Types.newParameterizedType(List.class, HotelResponse.class);
        jsonAdapter = new Moshi.Builder().build().adapter(hotelList);
    }


    @Override
    public List<HotelItem> getLocalData() {

        String cacheList = sharedPreferences.getString(KEY_CACHE_ITEMS, "");

        if(cacheList == null || cacheList.isEmpty()) {
            return Collections.EMPTY_LIST;
        } else {
            try {
                List<HotelResponse> items =  jsonAdapter.fromJson(cacheList);
                List<HotelItem> hotelItems = new ArrayList<>(items.size());
                for(HotelResponse i : items) {
                    hotelItems.add(HotelItem.builder()
                            .imageUrl(i.imageUrl)
                            .hotelName(i.hotelName).build());
                }

                return hotelItems;

            } catch (IOException e) {
                Timber.e(e);
                return Collections.EMPTY_LIST;
            }
        }
    }

    @Override
    public void saveLocalData(List<HotelItem> items) {

        List<HotelResponse> cacheResponse = new ArrayList<>(items.size());
        for(HotelItem i : items) {
            cacheResponse.add(new HotelResponse(i.imageUrl(), i.hotelName()));
        }

        sharedPreferences.edit().putString(KEY_CACHE_ITEMS, jsonAdapter.toJson(cacheResponse)).apply();
    }
}
