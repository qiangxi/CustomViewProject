package com.lanma.customviewproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.views.RippleView;

public class RippleViewActivity extends AppCompatActivity {
    private RippleView mRippleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("RippleViewActivity");
        setContentView(R.layout.activity_ripple_view);
        mRippleView = (RippleView) findViewById(R.id.rippleView);
    }


    public void button(View view) {
        if (mRippleView.isRippleSpreading()) {
            mRippleView.closeRippleSpread();
        } else {
            mRippleView.startRippleSpread();
        }
    }
}
