package com.rozzles.pinup;

import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class antiBurnIn {
    public antiBurnIn() {
    }

    public void animate_moving_frameLayout(int xTo, int yTo, RelativeLayout content_frame) {
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