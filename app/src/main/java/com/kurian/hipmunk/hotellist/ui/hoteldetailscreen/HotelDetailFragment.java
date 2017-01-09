package com.kurian.hipmunk.hotellist.ui.hoteldetailscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kurian.hipmunk.hotellist.R;
import com.kurian.hipmunk.hotellist.data.HotelItem;

/**
 * Created by Kurian on 08/01/2017.
 */

public class HotelDetailFragment extends Fragment {

    private static final String KEY_HOTEL_NAME = "key_hotel_name";
    private static final String KEY_HOTEL_IMAGE_URL = "key_hotel_image_url";

    private ImageView hotelImage;
    private Toolbar toolbar;

    public static HotelDetailFragment newInstance(HotelItem hotelItem) {

        Bundle args = new Bundle();
        args.putString(KEY_HOTEL_NAME, hotelItem.hotelName());
        args.putString(KEY_HOTEL_IMAGE_URL, hotelItem.imageUrl());
        HotelDetailFragment fragment = new HotelDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hotel_detail, container, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        toolbar = (Toolbar) view.findViewById(R.id.id_toolbar);
        toolbar.setTitle(getArguments().getString(KEY_HOTEL_NAME));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        hotelImage = (ImageView) view.findViewById(R.id.id_hotel_image);
        Glide.with(this).load(getArguments()
                .getString(KEY_HOTEL_IMAGE_URL))
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(hotelImage);

        return view;
    }
}
