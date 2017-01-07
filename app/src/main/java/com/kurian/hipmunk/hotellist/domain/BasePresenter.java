package com.kurian.hipmunk.hotellist.domain;

import com.kurian.hipmunk.hotellist.ui.MvpView;

import java.lang.ref.WeakReference;

/**
 * Created by Kurian on 07/01/2017.
 */

public class BasePresenter<V extends MvpView> {

    private WeakReference<V> viewRef;


    public void bindView(V view) {
        this.viewRef = new WeakReference<>(view);
    }

    public void unbindView() {
        this.viewRef.clear();
    }

    public boolean isViewBound() {
        return (viewRef != null) && (viewRef.get() != null);
    }

    public V getView() {
        return viewRef.get();
    }
}
