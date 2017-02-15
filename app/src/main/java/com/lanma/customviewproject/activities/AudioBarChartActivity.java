package com.lanma.customviewproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.views.AudioBarChart;

public class AudioBarChartActivity extends AppCompatActivity {
    private AudioBarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_bar_chart);
        setTitle("AudioBarChartActivity");
        chart = (AudioBarChart) findViewById(R.id.audioBarChart);
        chart.setBarChartCount(25);
    }
}
