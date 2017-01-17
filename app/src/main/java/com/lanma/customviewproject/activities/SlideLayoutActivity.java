package com.lanma.customviewproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lanma.customviewproject.R;

public class SlideLayoutActivity extends AppCompatActivity {
    private String TAG = SlideLayoutActivity.class.getSimpleName();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_layout);
        setTitle("SlideLayoutActivity");
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new myAdapter());
    }

    private class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return LayoutInflater.from(SlideLayoutActivity.this).inflate(R.layout.item_list_view_layout, null);
        }
    }
}
