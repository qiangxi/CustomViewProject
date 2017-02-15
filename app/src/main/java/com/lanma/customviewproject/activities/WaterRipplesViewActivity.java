package com.lanma.customviewproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.views.WaterRipplesView;

public class WaterRipplesViewActivity extends AppCompatActivity {
    private WaterRipplesView ripplesView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_ripples_view);
        setTitle("WaterRipplesViewActivity");
        ripplesView = (WaterRipplesView) findViewById(R.id.waterRipplesView);
        button = (Button) findViewById(R.id.startAnimation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ripplesView.isAnimIsStart()) {
                    button.setText("开始动画");
                    ripplesView.stopAnim();
                } else {
                    button.setText("停止动画");
                    ripplesView.startAnim();
                }
            }
        });
    }
}
