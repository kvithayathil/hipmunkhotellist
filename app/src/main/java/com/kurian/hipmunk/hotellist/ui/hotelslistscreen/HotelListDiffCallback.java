package com.kurian.hipmunk.hotellist.ui.hotelslistscreen;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import com.kurian.hipmunk.hotellist.data.HotelItem;

import java.util.List;

/**
 * Created by Kurian on 07/01/2017.
 */

public class HotelListDiffCallback extends DiffUtil.Callback {

    private final List<HotelItem> oldList;
    private final List<HotelItem> newList;

    public HotelListDiffCallback(List<HotelItem> oldList, List<HotelItem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        return TextUtils.equals(oldList.get(oldItemPosition).hotelName(),
                newList.get(newItemPosition).hotelName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return TextUtils.equals(oldList.get(oldItemPosition).imageUrl(),
                newList.get(newItemPosition).imageUrl());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
