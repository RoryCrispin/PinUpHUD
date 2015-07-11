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

package com.rozzles.pinup;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rozzles.pinup.newsSource.NewsSourceItem;
import com.shirwa.simplistic_rss.RssItem;

import java.util.ArrayList;

/**
 * Created by Rory on 11/07/2015 for PinUp
 * com.rozzles.pinup
 */
public class ListHelper {
    ArrayList<NewsSourceItem> newsSources;
    ListView newsList;
    NewsSourceItem currentNewsSourceItem;
    Context context;
    int listViewID;
    private ArrayAdapter<String> adapter;

    public ListHelper() {
    }

    public int getListViewID() {
        return listViewID;
    }

    public void setListViewID(int listViewID) {
        this.listViewID = listViewID;
    }

    public void init(Context cx, ListView newsList, int listViewID, ArrayAdapter<String> adapter) {
        newsSources = new ArrayList<>();
        this.context = cx;
        this.newsList = newsList;
        this.listViewID = listViewID;

//        adapter = new ArrayAdapter<>(cx,listViewID, getTopStories(currentNewsSourceItem,0,true));
//        adapter.set
        newsList.setAdapter(adapter);


    }

    public void addNewsSource(NewsSourceItem newSource) {
        newsSources.add(newSource);
    }

    public ArrayList<String> getTopStories(NewsSourceItem newsSourceItem, int startPoint, boolean reloadBeforeRead) {
        ArrayList<String> threeArticles = new ArrayList<>();
        for (int i = startPoint; i < startPoint + 3; i++) {
            newsSourceItem.loadLatestItems();
            threeArticles.add(i - startPoint, (newsSourceItem.getLatestItems(false).get(i).getDescription() + " \n " + getTimeSince(newsSourceItem.getLatestItems(false).get(i))));
        }
        return threeArticles;
    }

    private String getTimeSince(RssItem rssItem) {
        String timeSince = "";
        try {
            timeSince = rssItem.getTimeSincePost();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return timeSince;
    }

    public void refresh_list_view() {
        adapter.clear();
        adapter.addAll(getTopStories(getCurrentNewsSource(), 0, true));
        adapter.notifyDataSetChanged();
        newsList.invalidateViews();
        newsList.refreshDrawableState();
    }

    public NewsSourceItem getCurrentNewsSource() {
        return currentNewsSourceItem;
    }

    public void setCurrentNewsSourceItem(NewsSourceItem newsSourceItem) {
        this.currentNewsSourceItem = newsSourceItem;
    }


}
