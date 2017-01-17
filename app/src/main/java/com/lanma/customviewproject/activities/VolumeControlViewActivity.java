package com.lanma.customviewproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.views.VolumeControlView;

public class VolumeControlViewActivity extends AppCompatActivity {
    private VolumeControlView mControlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("VolumeControlViewActivity");
        setContentView(R.layout.activity_volume_control_view);
        mControlView = (VolumeControlView) findViewById(R.id.volumeControlView);
        mControlView.setCircleText("50%");
    }

    public void add(View view) {
        mControlView.addVolume();
    }

    public void reduce(View view) {
        mControlView.reduceVolume();
    }
}
