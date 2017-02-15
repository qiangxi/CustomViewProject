package com.lanma.customviewproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lanma.customviewproject.R;

public class SampleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_view);
        setTitle("SampleViewActivity");
    }
}
