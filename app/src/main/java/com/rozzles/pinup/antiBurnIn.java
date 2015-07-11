package com.rozzles.pinup;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class antiBurnIn {
    private boolean news_anim_down = true;
    private int current_y_view_position = 0;
    public antiBurnIn() {
    }

    public void shift_view_position(FrameLayout movFrame) {

        if (news_anim_down) {
            animate_moving_frameLayout(0, 100, movFrame);
            current_y_view_position = current_y_view_position + 100;
            if (current_y_view_position == 300) {
                news_anim_down = false;
            }
        } else {
            animate_moving_frameLayout(0, -100, movFrame);
            current_y_view_position = current_y_view_position - 100;
            if (current_y_view_position == 0) {
                news_anim_down = true;
            }
        }

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