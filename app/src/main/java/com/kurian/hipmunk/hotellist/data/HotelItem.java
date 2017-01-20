package com.kurian.hipmunk.hotellist.data;

import com.google.auto.value.AutoValue;

/**
 * A separate domain object to supply to the UI
 * Created by Kurian on 07/01/2017.
 */
@AutoValue
public abstract class HotelItem {

    public abstract String hotelName();
    public abstract String imageUrl();

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder hotelName(String name);
        public abstract Builder imageUrl(String url);
        public abstract HotelItem build();
    }

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_HotelItem.Builder();
    }
}
