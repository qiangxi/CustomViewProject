package com.lanma.customviewproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.entity.MainListInfo;

import java.util.List;

/**
 * 作者 任强强 on 2017/1/17 10:24.
 */

public class MainListAdapter extends BaseAdapter {
    private Context mContext;
    private List<MainListInfo> mList;

    public MainListAdapter(Context context, List<MainListInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        MainListViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_list_view_layout, null);
            holder = new MainListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MainListViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(mList.get(position).getItemTitle());
        return convertView;
    }

    private static class MainListViewHolder {
        private TextView mTextView;

        public MainListViewHolder(View view) {
            mTextView = (TextView) view.findViewById(R.id.itemMainListTitle);
        }
    }
}
