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

import com.shirwa.simplistic_rss.RssItem;
import com.shirwa.simplistic_rss.RssReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Rory on 08/07/2015 for ${Project_Name}
 */
public class PinUp extends DreamService implements OnTouchListener {

    private final com.rozzles.pinup.antiBurnIn antiBurnIn = new antiBurnIn();
    private final com.rozzles.pinup.spotifyBroadcastHandler spotifyBroadcastHandler = new spotifyBroadcastHandler();
    Timer regular_timer = new Timer();
    private List<RssItem> RssItems = null;
    private String url = "http://feeds.bbci.co.uk/news/world/rss.xml";
    private TextView nowPlaying_label;
    private ListView newsList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> threeArticles;
    private int news_index_pos = 0; //sometimes snake case is better ok

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            System.out.println(intent.getAction());

            if (intent.getAction() == "com.rozzles.PinUp.shiftUI") {
                FrameLayout movFrame = (FrameLayout) findViewById(R.id.movFrame);
                antiBurnIn.shift_view_position(movFrame);
            } else {
                set_nowPlayingLabel(spotifyBroadcastHandler.spotify_broadcast_reciever(intent));
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
        loadNewsfeed();
//        set_nowPlayingLabel();
        registerReceiver(mReceiver, spotifyBroadcastHandler.spotify_intent_filter());

        TimerTask regular_timer_task_obj = new regular_timer_task();

        regular_timer.scheduleAtFixedRate(regular_timer_task_obj, 0, 300000);
    }

    public void assign_ui_components() {
        nowPlaying_label = (TextView) findViewById(R.id.nowPlaying_label);
        newsList = (ListView) findViewById(R.id.newsList);

    }

    private void loadNewsfeed() {
        fetchRss(url);
        threeArticles = fill_nf_array(0);
        System.out.println(RssItems.get(0).getPubDate().getTime());
        adapter = new ArrayAdapter<>(this, R.layout.news_list_view, threeArticles);
        newsList.setAdapter(adapter);
    }

    private ArrayList<String> fill_nf_array(int startPoint) {
        ArrayList<String> threeArticles = new ArrayList<String>();
        for (int i = startPoint; i < startPoint + 3; i++) {
            threeArticles.add(i - startPoint, (RssItems.get(i).getDescription() + " \n " + getTimeSince(i - startPoint)));
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
        regular_timer.cancel();
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
                refresh_list_view();
                newsList.startAnimation(animationFadeIn);
            }
        });

        newsList.startAnimation(animationFadeOut);
//        antiBurnIn.shift_view_position(v);
    }

    private void load_next_three_articles() {
        news_index_pos = news_index_pos + 4;
        threeArticles = fill_nf_array(news_index_pos);
    }

    private void refresh_list_view() {
        adapter.clear();
        adapter.addAll(threeArticles);
        adapter.notifyDataSetChanged();
        newsList.invalidateViews();
        newsList.refreshDrawableState();
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