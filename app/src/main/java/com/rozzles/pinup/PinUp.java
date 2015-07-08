package com.rozzles.pinup;

import android.os.StrictMode;
import android.service.dreams.DreamService;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shirwa.simplistic_rss.RssItem;
import com.shirwa.simplistic_rss.RssReader;
import java.util.List;

/**
 * Created by Rory on 08/07/2015 for ${Project_Name}
 */
public class PinUp extends DreamService implements OnClickListener {

    List<RssItem> RssItems = null;



   String url = "http://feeds.bbci.co.uk/news/world/rss.xml";
   ListView newsList  ;

//    String[] threeArticles = {};


    @Override
    public void onDreamingStarted() {
//daydream started

        setInteractive(true);
        setFullscreen(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.dream_main);
        fetchRss(url);

        String[] threeArticles = {RssItems.get(0).getDescription(),
                RssItems.get(1).getDescription(),
                RssItems.get(3).getDescription()
        };


        newsList = (ListView) findViewById(R.id.newsList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                 R.layout.news_list_view, threeArticles);
        newsList.setAdapter(adapter);


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

    public void onClick(View v) {
//handle clicks
        System.out.println("oops!");
    }
}