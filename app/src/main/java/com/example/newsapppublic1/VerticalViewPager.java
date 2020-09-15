package com.example.newsapppublic1;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {
        float MIN_SCALE = 0.75f;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void transformPage(View view, float position) {

            if (position < -1) {
                view.setAlpha(0);
                float yPosition = position * view.getHeight();
                view.setTranslationY(-yPosition);
            } else if (position <= 0) {    // [-1,0]
                view.setAlpha(1);

                view.setTranslationX(view.getWidth() * -position);
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);
            } else if (position >= 0) {    // [-1,0]
                view.setAlpha(1 - position / 3);
                view.setScaleX(1 - position / 3);
                view.setScaleY(1 - position / 3);
                view.setTranslationX(view.getWidth() * -position);
                float yPosition = position * view.getHeight();
                view.setTranslationY(-position - 1);
            } else if (position <= 1) {


                view.setAlpha(1);

                view.setTranslationX(view.getWidth() * -position);

                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);
            } else {
                view.setAlpha(0);
                float yPosition = position * view.getHeight();
                view.setTranslationY(-yPosition);
            }
        }
    }

}