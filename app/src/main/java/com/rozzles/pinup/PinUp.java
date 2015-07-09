package com.rozzles.pinup;

import android.os.StrictMode;
import android.service.dreams.DreamService;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shirwa.simplistic_rss.RssItem;
import com.shirwa.simplistic_rss.RssReader;

import java.util.List;

/**
 * Created by Rory on 08/07/2015 for ${Project_Name}
 */
public class PinUp extends DreamService implements OnTouchListener {

    List<RssItem> RssItems = null;


    String url = "http://feeds.bbci.co.uk/news/world/rss.xml";
    ListView newsList;
    ArrayAdapter<String> adapter;

//    String[] threeArticles;


    @Override
    public void onDreamingStarted() {
//daydream started

        setInteractive(true);
        setFullscreen(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.dream_main);
        loadNewsfeed();
    }

    private void loadNewsfeed() {
        fetchRss(url);


        String[] threeArticles = fill_nf_array(0);

        System.out.println(RssItems.get(0).getPubDate().getTime());


        newsList = (ListView) findViewById(R.id.newsList);

        adapter = new ArrayAdapter<String>(this,
                R.layout.news_list_view, threeArticles);
        newsList.setAdapter(adapter);
    }

    private String[] fill_nf_array(int startPoint) {
        String[] threeArticles = new String[3];
        for (int i = startPoint; i < startPoint + 3; i++) {
            threeArticles[i - startPoint] = RssItems.get(i - startPoint).getDescription() + " \n " + getTimeSince(i - startPoint);
        }
        return threeArticles;
    }

    private String getTimeSince(int i) {
        String timeSince = "";
        try {
            timeSince = RssItems.get(i).getTimeSincePost();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return timeSince;
    }

    private void fetchRss(String url) {
        RssReader rssReader = new RssReader(url);
        try {
            RssItems = rssReader.getItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDreamingStopped() {
//daydream stopped
    }

    @Override
    public void onAttachedToWindow() {
//setup daydream
    }

    @Override
    public void onDetachedFromWindow() {
//tidy up

        super.onDetachedFromWindow();

    }

    public boolean onTouch(View v, MotionEvent e) {
//handle clicks
        System.out.println("oops!");
        return true;
    }

    public void refresh(View v) {
        fill_nf_array(4);
        adapter.notifyDataSetChanged();
        refreshListView();
    }

    public void refreshListView() {
        Runnable run = new Runnable() {
            public void run() {
                //reload content
//                    arraylist.clear();
//                    arraylist.addAll(db.readAll());
                adapter.notifyDataSetChanged();
                newsList.invalidateViews();
                newsList.refreshDrawableState();
            }


        };
        run.run();
//            System.our.
    }
}