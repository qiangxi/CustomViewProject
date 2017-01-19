package com.lanma.customviewproject.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.views.SlideLayout;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class SlideLayoutActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_layout);
        setTitle("SlideLayoutActivity");
        listView = (ListView) findViewById(R.id.listView);
        initData();
        listView.setAdapter(new myAdapter());
    }

    private void initData() {
        mList.add("删除");
        mList.add("收藏");
        mList.add("点赞");
        mList.add("打赏");
        mList.add("丢硬币");
        mList.add("丢香蕉");
        mList.add("置顶");
        mList.add("忽略");
        mList.add("删除");
        mList.add("收藏");
        mList.add("点赞");
        mList.add("打赏");
        mList.add("丢硬币");
        mList.add("丢香蕉");
        mList.add("置顶");
        mList.add("忽略");
        mList.add("删除");
        mList.add("收藏");
        mList.add("点赞");
        mList.add("打赏");
        mList.add("丢硬币");
        mList.add("丢香蕉");
        mList.add("置顶");
        mList.add("忽略");
        mList.add("删除");
        mList.add("收藏");
        mList.add("点赞");
        mList.add("打赏");
        mList.add("丢硬币");
        mList.add("丢香蕉");
        mList.add("置顶");
        mList.add("忽略");
        mList.add("删除");
        mList.add("收藏");
        mList.add("点赞");
        mList.add("打赏");
        mList.add("丢硬币");
        mList.add("丢香蕉");
        mList.add("置顶");
        mList.add("忽略");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Logger.e("SlideLayoutActivity-onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.e("SlideLayoutActivity-onRestoreInstanceState");
    }

    private class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size() / 2;
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyAdapterViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(SlideLayoutActivity.this).inflate(R.layout.item_list_view_layout, null);
                holder = new MyAdapterViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MyAdapterViewHolder) convertView.getTag();
            }
            holder.textView2.setText(mList.get(2 * position));
            holder.textView3.setText(mList.get(2 * position + 1));
            return convertView;
        }
    }

    private static class MyAdapterViewHolder {
        private TextView textView2;
        private TextView textView3;
        private SlideLayout slideLayout;

        MyAdapterViewHolder(View view) {
            slideLayout = (SlideLayout) view.findViewById(R.id.slideLayout);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            textView3 = (TextView) view.findViewById(R.id.textView3);
        }
    }
}
