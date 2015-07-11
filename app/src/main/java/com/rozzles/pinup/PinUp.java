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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.service.dreams.DreamService;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rozzles.pinup.newsSource.NewsSourceItem;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class PinUp extends DreamService implements OnTouchListener {

    private final com.rozzles.pinup.antiBurnIn antiBurnIn = new antiBurnIn();
    private final com.rozzles.pinup.spotifyBroadcastHandler spotifyBroadcastHandler = new spotifyBroadcastHandler();
    Timer regular_timer = new Timer();
    ListHelper listHelper;
    private TextView nowPlaying_label;
    private ListView newsList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> threeArticles;
    private int news_index_pos = 0;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), "com.rozzles.PinUp.shiftUI")) {
                FrameLayout movFrame = (FrameLayout) findViewById(R.id.movFrame);
                antiBurnIn.shift_view_position(movFrame);
            } else {
                set_nowPlayingLabel(spotifyBroadcastHandler.spotify_broadcast_receiver(intent));
            }
        }
    };

    public void set_nowPlayingLabel(String text) {
        if (text != null) nowPlaying_label.setText(text);
    }

    @Override
    public void onDreamingStarted() {
//daydream started
        setInteractive(true);
        setFullscreen(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.dream_main);
        assign_ui_components();
        NewsSourceItem bbcWorldNews = new NewsSourceItem();
        listHelper = new ListHelper();
        bbcWorldNews.setRssUrl("http://feeds.bbci.co.uk/news/technology/rss.xml");
        bbcWorldNews.getLatestItems(true);
        listHelper.setCurrentNewsSourceItem(bbcWorldNews);
        loadNewsfeed();
        initListHelper();
        registerReceiver(mReceiver, spotifyBroadcastHandler.spotify_intent_filter());
        TimerTask regular_timer_task_obj = new regular_timer_task();
        regular_timer.scheduleAtFixedRate(regular_timer_task_obj, 0, 300000);
        System.out.println(bbcWorldNews.getLatestItems(false).get(0).getTitle());
    }

    public void assign_ui_components() {
        nowPlaying_label = (TextView) findViewById(R.id.nowPlaying_label);
        newsList = (ListView) findViewById(R.id.newsList);
    }

    private void initListHelper() {
        adapter = new ArrayAdapter<String>(this, R.layout.news_list_view, listHelper.getTopStories(listHelper.currentNewsSourceItem, 0, true));
        listHelper.init(this, newsList, R.layout.news_list_view, adapter);
    }

    private void loadNewsfeed() {
//        fetchRss(url);
//        threeArticles = fill_nf_array(0);
//        System.out.println(RssItems.get(0).getPubDate().getTime());
        newsList.getContext();
    }

    @Override
    public void onDreamingStopped() {
//daydream stopped
        regular_timer.cancel();
        unregisterReceiver(mReceiver);
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

    private void anim_load_articles(View v) {
        final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        animationFadeOut.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                load_next_three_articles();
                listHelper.refresh_list_view();
                newsList.startAnimation(animationFadeIn);
            }
        });

        newsList.startAnimation(animationFadeOut);
//        antiBurnIn.shift_view_position(v);
    }

    private void load_next_three_articles() { //TODO build this
//        news_index_pos = news_index_pos + 4;
//        threeArticles = fill_nf_array(news_index_pos);
    }

    public void view_touched(View v) {
//        antiBurnIn.shift_view_position(v);

    }

    class regular_timer_task extends TimerTask {

        public void run() {

            Intent i = new Intent();
            i.setAction("com.rozzles.PinUp.shiftUI");
            sendBroadcast(i);

        }
    }


}