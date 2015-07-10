package com.rozzles.pinup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StrictMode;
import android.service.dreams.DreamService;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.shirwa.simplistic_rss.RssItem;
import com.shirwa.simplistic_rss.RssReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rory on 08/07/2015 for ${Project_Name}
 */
public class PinUp extends DreamService implements OnTouchListener {

    List<RssItem> RssItems = null;

    String url = "http://feeds.bbci.co.uk/news/world/rss.xml";
    ListView newsList;
    ArrayAdapter<String> adapter;

    ArrayList<String> threeArticles;
    int news_index_pos = 0; //sometimes snake case is better ok
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            // This is sent with all broadcasts, regardless of type. The value is taken from
            // System.currentTimeMillis(), which you can compare to in order to determine how
            // old the event is.
            long timeSentInMs = intent.getLongExtra("timeSent", 0L);

            String action = intent.getAction();
            if (action.equals(BroadcastTypes.METADATA_CHANGED)) {
                String trackId = intent.getStringExtra("id");
                String artistName = intent.getStringExtra("artist");
                String albumName = intent.getStringExtra("album");
                String trackName = intent.getStringExtra("track");
//                int trackLengthInSec = intent.getIntExtra("length", 0);
                // Do something with extracted information...

                System.out.println("tn: " + trackName);

            } else if (action.equals(BroadcastTypes.PLAYBACK_STATE_CHANGED)) {
                boolean playing = intent.getBooleanExtra("playing", false);
                //int positionInMs = intent.getIntExtra("playbackPosition", 0);
                // Do something with extracted information
            } else if (action.equals(BroadcastTypes.QUEUE_CHANGED)) {
                // Sent only as a notification, your app may want to respond accordingly.
            }
        }

        final class BroadcastTypes {
            static final String SPOTIFY_PACKAGE = "com.spotify.music";
            static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
            static final String QUEUE_CHANGED = SPOTIFY_PACKAGE + ".queuechanged";
            static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";
        }
    };

    @Override
    public void onDreamingStarted() {
//daydream started

        setInteractive(true);
        setFullscreen(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.dream_main);
        loadNewsfeed();

        IntentFilter iF = new IntentFilter();
        iF.addAction("com.spotify.music.metadatachanged");
        iF.addAction("com.spotify.music.playbackstatechanged");
        iF.addAction("com.spotify.music.queuechanged");
//        iF.addAction("android.intent.action.HEADSET_PLUG");

        registerReceiver(mReceiver, iF);


    }

    private void loadNewsfeed() {
        fetchRss(url);
        threeArticles = fill_nf_array(0);
        System.out.println(RssItems.get(0).getPubDate().getTime());
        newsList = (ListView) findViewById(R.id.newsList);
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

    public void load_next_three_articles(View v) {
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
                news_index_pos = news_index_pos + 4;
                threeArticles = fill_nf_array(news_index_pos);
                refresh_list_view();
                newsList.startAnimation(animationFadeIn);
            }
        });
        newsList.startAnimation(animationFadeOut);

        shift_view_position();


    }

    public void refresh_list_view() {
        adapter.clear();
        adapter.addAll(threeArticles);
        adapter.notifyDataSetChanged();
        newsList.invalidateViews();
        newsList.refreshDrawableState();
    }

    public void shift_view_position() {
        FrameLayout content_frame = (FrameLayout) findViewById(R.id.content_frame);

//        content_frame.setY(1000);
        replace(100, 100, content_frame);
    }

    public void replace(int xTo, int yTo, FrameLayout content_frame) {
        // create set of animations
        AnimationSet replaceAnimation = new AnimationSet(false);
        // animations should be applied on the finish line
        replaceAnimation.setFillAfter(true);

        // create translation animation
        TranslateAnimation trans = new TranslateAnimation(0, 0,
                TranslateAnimation.ABSOLUTE, xTo - content_frame.getLeft(), 0, 0,
                TranslateAnimation.ABSOLUTE, yTo - content_frame.getTop());
        trans.setDuration(1000);

        // add new animations to the set
        replaceAnimation.addAnimation(trans);

        // start our animation
        content_frame.startAnimation(replaceAnimation);
    }




}