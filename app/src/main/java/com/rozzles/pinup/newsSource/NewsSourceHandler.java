/*
 * Rory Crispin --rorycrispin.co.uk -- rozzles.com
 *
 * Distributed under the Attribution-NonCommercial-ShareAlike 4.0 International License, full conditions can be found here:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * This is free software, and you are welcome to redistribute it under certain conditions;
 *
 *  Go crazy,
 *  Rozz xx
 */

package com.rozzles.pinup.newsSource;

import com.shirwa.simplistic_rss.RssItem;
import com.shirwa.simplistic_rss.RssReader;

import java.util.List;

/**
 * Created by Rory on 11/07/2015 for PinUp
 * com.rozzles.pinup.newsSource
 */
public class NewsSourceHandler {

    public List<RssItem> fetchRssItems(String url) {
        RssReader rssReader = new RssReader(url);
        try {
            return rssReader.getItems();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
