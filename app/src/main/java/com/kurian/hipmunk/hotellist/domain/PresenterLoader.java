package com.kurian.hipmunk.hotellist.domain;

import android.content.Context;
import android.support.v4.content.Loader;

import com.kurian.hipmunk.hotellist.domain.BasePresenter;
import com.kurian.hipmunk.hotellist.domain.PresenterFactory;

import timber.log.Timber;

/**
 * Created by Kurian on 08/01/2017.
 */

public final class PresenterLoader<P extends BasePresenter> extends Loader<P> {

    private final PresenterFactory<P> factory;
    private final String tag;

    private P presenter;

    public PresenterLoader(Context context, PresenterFactory<P> factory, String tag) {
        super(context);
        this.factory = factory;
        this.tag = tag;
    }

    @Override
    protected void onStartLoading() {
        Timber.d("onStartLoading %1$s", tag);

        if(presenter != null) {
            deliverResult(presenter);
            return;
        }

        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        Timber.d("onForceLoad %1$s", tag);
        presenter = factory.create();
        deliverResult(presenter);
    }

    @Override
    public void deliverResult(P data) {
        Timber.d("deliverResult %1$s", tag);
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        Timber.d("onReset %1$s", tag);
        if(presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }
}
