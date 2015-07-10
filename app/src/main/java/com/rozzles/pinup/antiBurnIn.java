package com.rozzles.pinup;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class antiBurnIn {
    public antiBurnIn() {
    }

    public void animate_moving_frameLayout(final int xTo, final int yTo, final FrameLayout movFrame) {
        TranslateAnimation anim = new TranslateAnimation(0, xTo, 0, yTo);
        anim.setDuration(1000);
        anim.setFillEnabled(true);

        anim.setAnimationListener(new TranslateAnimation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)
                        movFrame.getLayoutParams();
                params.topMargin += yTo;
                params.leftMargin += xTo;
                movFrame.setLayoutParams(params);
            }
        });
        movFrame.startAnimation(anim);
    }
}