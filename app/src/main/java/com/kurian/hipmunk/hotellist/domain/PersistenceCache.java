package com.kurian.hipmunk.hotellist.domain;

import java.util.List;

/**
 * Cache item that can be implemented with a persistence method of your choice
 * Created by Kurian on 08/01/2017.
 */

public interface PersistenceCache {

    /**
     * Retrieve data from local persistence
     * @return List of hotel items
     */
    List<HotelItem> getLocalData();

    /**
     * Save data to local persistence
     * @param items data to save
     */
    void saveLocalData(List<HotelItem> items);
}
