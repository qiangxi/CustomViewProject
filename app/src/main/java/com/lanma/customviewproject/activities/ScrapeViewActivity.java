package com.lanma.customviewproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.views.ScrapeView;

public class ScrapeViewActivity extends AppCompatActivity {
    private ScrapeView mScrapeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrape_view);
        mScrapeView = (ScrapeView) findViewById(R.id.scrapeView);
    }
}
