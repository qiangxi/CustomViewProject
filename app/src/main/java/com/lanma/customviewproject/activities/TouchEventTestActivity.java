package com.lanma.customviewproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.views.TouchEventTest;
import com.orhanobut.logger.Logger;

public class TouchEventTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event_test);
        setTitle("TouchEventTestActivity");
        TouchEventTest testView = (TouchEventTest) findViewById(R.id.TouchEventTest);
        testView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("setOnClickListener,view.getClass():" + v.getClass() + "");
            }
        });
        testView.setOnViewClick(new TouchEventTest.onViewClick() {
            @Override
            public void onClick(float scrollX, float scrollY) {
                Logger.e("x轴移动了:" + scrollX + " px,y轴移动了:" + scrollY + " px", "");
            }
        });

    }
}
