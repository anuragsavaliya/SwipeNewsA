package com.example.newsapppublic1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class My_Imageview extends ImageView {
    public My_Imageview(Context context) {
        super(context);
    }

    public My_Imageview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public My_Imageview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int w = MeasureSpec.getSize(widthMeasureSpec);
            int h = w * d.getIntrinsicHeight() / d.getIntrinsicWidth();
            setMeasuredDimension(w, h);
        } else super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
