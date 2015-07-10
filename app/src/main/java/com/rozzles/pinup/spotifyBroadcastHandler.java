package com.rozzles.pinup;

import android.content.Intent;
import android.content.IntentFilter;

public class spotifyBroadcastHandler {

    //    private final com.rozzles.pinup.PinUp pinUp = new PinUp();
    public spotifyBroadcastHandler() {
    }

    public IntentFilter spotify_intent_filter() {
        IntentFilter iF = new IntentFilter();
        iF.addAction("com.spotify.music.metadatachanged");
        iF.addAction("com.spotify.music.playbackstatechanged");
        iF.addAction("com.spotify.music.queuechanged");
        return iF;

    }

    String spotify_broadcast_reciever(Intent intent) {
        // This is sent with all broadcasts, regardless of type. The value is taken from
        // System.currentTimeMillis(), which you can compare to in order to determine how
        // old the event is.
        long timeSentInMs = intent.getLongExtra("timeSent", 0L);

        String action = intent.getAction();
        if (action.equals("com.spotify.music.metadatachanged")) {
            String trackId = intent.getStringExtra("id");
            String artistName = intent.getStringExtra("artist");
            String albumName = intent.getStringExtra("album");
            String trackName = intent.getStringExtra("track");
            System.out.println("tn: " + trackName);

            return (trackName + " - " + artistName);
//            PinUp.set_nowPlayingLabel(trackName + " - " + artistName + " - " + albumName);

//        } else if (action.equals(BroadcastTypes.PLAYBACK_STATE_CHANGED)) {
//            boolean playing = intent.getBooleanExtra("playing", false);
//            //int positionInMs = intent.getIntExtra("playbackPosition", 0);
//            // Do something with extracted information
//        } else if (action.equals(BroadcastTypes.QUEUE_CHANGED)) {
//            // Sent only as a notification, your app may want to respond accordingly.
//        }
        }
        return null;
    }

}