/*
 * Rory Crispin -rorycrispin.co.uk- rozzles.com
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

import java.util.List;

/**
 * Created by Rory on 11/07/2015 for PinUp
 * com.rozzles.pinup.newsSource
 */
public class NewsSourceItem {
    String title;
    String rssUrl;
    List<RssItem> latestItems = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public List<RssItem> getLatestItems(Boolean reloadBeforeRead) {
        if (reloadBeforeRead) {
            loadLatestItems();
            return latestItems;
        } else {
            return latestItems;
        }
    }

    public void setLatestItems(List<RssItem> latestItems) {
        this.latestItems = latestItems;
    }

    public void loadLatestItems() {
        NewsSourceHandler nsh = new NewsSourceHandler();
        this.latestItems = nsh.fetchRssItems(this.rssUrl);
    }

}




