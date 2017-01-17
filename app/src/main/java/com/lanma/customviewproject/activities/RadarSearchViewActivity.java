package com.lanma.customviewproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.views.RadarSearchView;

public class RadarSearchViewActivity extends AppCompatActivity {
    private RadarSearchView mRadarSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("RadarSearchViewActivity");
        setContentView(R.layout.activity_radar_search_view);
        mRadarSearchView = (RadarSearchView) findViewById(R.id.mRadarSearchView);
    }

    public void click(View view) {
        if (mRadarSearchView.isShowing()) {
            mRadarSearchView.dismiss();
        } else {
            mRadarSearchView.show();
        }
    }
}
