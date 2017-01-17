package com.lanma.customviewproject.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 作者 任强强 on 2016/8/29 14:25.
 * 按压ImageView时图标变色
 */
public class AlphaImageView extends ImageView {
    private int mTintColor = Color.parseColor("#77FF5964");

    public AlphaImageView(Context context) {
        this(context, null);
    }

    public AlphaImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphaImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setColorFilter(mTintColor);
                break;
            case MotionEvent.ACTION_UP:
                setColorFilter(null);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setTintColor(int tintColor) {
        this.mTintColor = tintColor;
    }

}
