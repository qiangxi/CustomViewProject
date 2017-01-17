package com.lanma.customviewproject.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.views.StateLine;

public class StateLineActivity extends AppCompatActivity {
    private StateLine mStateLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("StateLineActivity");
        setContentView(R.layout.activity_state_line);
        mStateLine = (StateLine) findViewById(R.id.stateLine);
        mStateLine.setNewestPointPosition(2);
        mStateLine.setSelectedCircleColor(Color.parseColor("#48AFD7"));
        mStateLine.setSelectedLineColor(Color.parseColor("#48AFD7"));
        mStateLine.setStartPositionText("武汉市");
        mStateLine.setArrivePositionText("兰州市");
    }
}
