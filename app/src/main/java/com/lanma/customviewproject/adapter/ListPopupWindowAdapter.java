package com.lanma.customviewproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanma.customviewproject.R;

import java.util.List;

/**
 * 作者 任强强 on 2017/1/18 12:15.
 */

public class ListPopupWindowAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public ListPopupWindowAdapter(Context context, List<String> list) {
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
        ListPopupWindowHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_list_view_layout, null);
            holder = new ListPopupWindowHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ListPopupWindowHolder) convertView.getTag();
        }
        holder.mTextView.setText(mList.get(position));
        return convertView;
    }

    private static class ListPopupWindowHolder {
        private TextView mTextView;

        ListPopupWindowHolder(View view) {
            mTextView = (TextView) view.findViewById(R.id.itemMainListTitle);
        }
    }
}
