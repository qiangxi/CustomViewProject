package com.lanma.customviewproject.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.adapter.ListPopupWindowAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListPopupWindowActivity extends AppCompatActivity {

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_popup_window);
        initData();
    }

    private void initData() {
        mList.add("创建群聊");
        mList.add("加好友/群");
        mList.add("扫一扫");
        mList.add("面对面快传");
        mList.add("付款");
        mList.add("拍摄");
    }

    public void showPopupWindow(View view) {
        ListPopupWindow popupWindow = new ListPopupWindow(this);
        popupWindow.setDropDownGravity(Gravity.TOP);
        popupWindow.setAdapter(new ListPopupWindowAdapter(this, mList));
        popupWindow.setWidth(view.getWidth());
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnchorView(view);
        popupWindow.setVerticalOffset(10);
        popupWindow.setAnimationStyle(android.R.style.Animation_Activity);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#323232")));
        popupWindow.show();
        ListView listView = popupWindow.getListView();
        listView.setDivider(new ColorDrawable(Color.parseColor("#000000")));
        listView.setDividerHeight(1);
    }

    public void showPopupWindow1(View view) {
        ListPopupWindow popupWindow = new ListPopupWindow(this);
        popupWindow.setDropDownGravity(Gravity.TOP);
        popupWindow.setAdapter(new ListPopupWindowAdapter(this, mList));
        popupWindow.setWidth(view.getWidth());
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnchorView(view);
        popupWindow.setVerticalOffset(10);
        popupWindow.setAnimationStyle(android.R.style.Animation_Activity);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#323232")));
        popupWindow.show();
        ListView listView = popupWindow.getListView();
        listView.setDivider(new ColorDrawable(Color.parseColor("#000000")));
        listView.setDividerHeight(1);
    }
}
