package com.kurian.hipmunk.hotellist.domain;

import com.kurian.hipmunk.hotellist.domain.BasePresenter;

/**
 * Created by Kurian on 08/01/2017.
 */

public interface PresenterFactory<P extends BasePresenter> {

    P create();
}
