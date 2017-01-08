package com.kurian.hipmunk.hotellist.ui.hotelslistscreen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kurian.hipmunk.hotellist.HotelListApp;
import com.kurian.hipmunk.hotellist.R;
import com.kurian.hipmunk.hotellist.domain.HotelItem;
import com.kurian.hipmunk.hotellist.domain.HotelListPresenter;
import com.kurian.hipmunk.hotellist.domain.PresenterFactory;
import com.kurian.hipmunk.hotellist.ui.base.BasePresenterFragment;

import java.util.List;

/**
 * Created by Kurian on 07/01/2017.
 */

public class HotelListFragment extends BasePresenterFragment<HotelListPresenter, HotelListView>
        implements HotelListView {

    private static final String TAG = HotelListFragment.class.getCanonicalName();

    private final String KEY_LAYOUT_MANAGER = "layout_manager";

    private HotelListPresenter presenter;
    private HotelListAdapter adapter;

    private RecyclerView hotelReyclerView;
    private Parcelable listState;
    private SwipeRefreshLayout refreshLayout;
    private TextView loadingMessage;

    private boolean isDestroyedBySystem = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        adapter = new HotelListAdapter(getContext());

        View view = inflater.inflate(R.layout.hotel_list_layout, container, false);

        loadingMessage = (TextView) view.findViewById(R.id.id_empty_list);

        hotelReyclerView = (RecyclerView) view.findViewById(R.id.id_hotel_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hotelReyclerView.setLayoutManager(layoutManager);
        hotelReyclerView.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_container);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getHotels();
            }
        });

        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        listState = hotelReyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(KEY_LAYOUT_MANAGER, listState);
        super.onSaveInstanceState(outState);
        isDestroyedBySystem = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(KEY_LAYOUT_MANAGER);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getHotels();

        if(listState != null) {
            hotelReyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }

        isDestroyedBySystem = false;
    }

    @Override
    public void updateHotelList(List<HotelItem> hotelItems) {
        adapter.updateList(hotelItems);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading(boolean toggle) {

        //Determine the visibility of the loading animation/text and display the appropriate message
        //pending on result count
        if(adapter.getItemCount() == 0) {
            if(toggle) {
                loadingMessage.setText(R.string.progress_text);
            } else {
                loadingMessage.setText(R.string.empty_list);
            }

            loadingMessage.setVisibility(View.VISIBLE);
        } else {
            loadingMessage.setVisibility(View.GONE);
        }

        refreshLayout.setRefreshing(toggle);
    }

    @Override
    protected String tag() {
        return TAG;
    }

    @Override
    protected PresenterFactory<HotelListPresenter> getPresenterFactory() {
        return new HotelListPresenterFactory(PreferenceManager.getDefaultSharedPreferences(getContext())
                ,((HotelListApp)getActivity().getApplication()).providesNetworkAdapter());
    }

    @Override
    protected void onPresenterPrepared(HotelListPresenter presenter) {
        this.presenter = presenter;
    }
}
