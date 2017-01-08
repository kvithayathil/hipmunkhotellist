package com.kurian.hipmunk.hotellist.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.kurian.hipmunk.hotellist.domain.BasePresenter;
import com.kurian.hipmunk.hotellist.domain.PresenterFactory;
import com.kurian.hipmunk.hotellist.domain.PresenterLoader;
import com.kurian.hipmunk.hotellist.ui.MvpView;

import timber.log.Timber;

/**
 * Created by Kurian on 08/01/2017.
 */

public abstract class BasePresenterFragment<P extends BasePresenter<V>, V extends MvpView>
        extends Fragment
        implements LoaderManager.LoaderCallbacks<P> {

    private static final String TAG = BasePresenterFragment.class.getCanonicalName();
    private static final int LOADER_ID = 1001;

    private P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(loaderId(), null, this);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.bindView(getMvpView());
    }

    @Override
    public void onPause() {
        presenter.unbindView();
        super.onPause();
    }

    protected abstract String tag();

    protected  abstract PresenterFactory<P> getPresenterFactory();

    protected abstract void onPresenterPrepared(P presenter);

    protected void onPresenterDestroyed() {
    }

    protected int loaderId() {
        return LOADER_ID;
    }

    protected V getMvpView() {
        return (V) this;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Timber.d("onCreateLoader %1$s", tag());
        return new PresenterLoader(getContext(), getPresenterFactory(), tag());
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P presenter) {
        Timber.d("onLoadFinished %1$s", tag());
        this.presenter = presenter;
        onPresenterPrepared(presenter);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        Timber.d("onLoaderReset %1$s", tag());
        presenter = null;
        onPresenterDestroyed();
    }
}
