package com.lanma.customviewproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.adapter.MainListAdapter;
import com.lanma.customviewproject.entity.MainListInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private List<MainListInfo> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("CustomViewProject");
        mListView = (ListView) findViewById(R.id.listView);
        initData();
    }

    private void initData() {
        mList.add(new MainListInfo("六边形View", HexagonTextViewDemoActivity.class));
        mList.add(new MainListInfo("越界回弹ScrollView", ElasticityScrollViewActivity.class));
        mList.add(new MainListInfo("雷达扫描view", RadarSearchViewActivity.class));
        mList.add(new MainListInfo("RippleView", RippleViewActivity.class));
        mList.add(new MainListInfo("侧滑删除layout", SlideLayoutActivity.class));
        mList.add(new MainListInfo("仿支付宝物流状态", StateLineActivity.class));
        mList.add(new MainListInfo("音量调节View", VolumeControlViewActivity.class));
        mList.add(new MainListInfo("ListPopupWindow", ListPopupWindowActivity.class));

        mListView.setAdapter(new MainListAdapter(this, mList));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, mList.get(position).getTargetActivity());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
