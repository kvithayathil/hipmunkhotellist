package com.kurian.hipmunk.hotellist.ui.hotelslistscreen;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.kurian.hipmunk.hotellist.R;
import com.kurian.hipmunk.hotellist.domain.HotelItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurian on 07/01/2017.
 */

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.HotelItemViewHolder> {

    private List<HotelItem> hotelItems;
    private LayoutInflater layoutInflater;

    public HotelListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        hotelItems = new ArrayList<>();
    }

    @Override
    public HotelItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_list_item, parent, false);
        return new HotelItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelItemViewHolder holder, int position) {
        holder.bindView(hotelItems.get(position));
    }

    public void updateList(List<HotelItem> hotelItems) {
        /*
        DiffUtil.DiffResult diffResult
                = DiffUtil.calculateDiff(new HotelListDiffCallback(this.hotelItems, hotelItems));
        diffResult.dispatchUpdatesTo(this);
        */

        this.hotelItems = hotelItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return hotelItems.size();
    }

    static class HotelItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView hotelName;
        private final ImageView hotelImage;

        private RequestManager requestManager;

        public HotelItemViewHolder(View view) {
            super(view);
            hotelName = (TextView)view.findViewById(R.id.id_hotel_name);
            hotelImage = (ImageView)view.findViewById(R.id.id_hotel_image);
            requestManager = Glide.with(view.getContext());
        }

        public void bindView(HotelItem hotel) {
            hotelName.setText(hotel.hotelName());
            requestManager
                    .load(hotel.imageUrl())
                    .centerCrop()
                    .into(hotelImage);
        }
    }
}
