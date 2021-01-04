package com.meetutech.baosteel.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.utils.NumberUtils;

import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    List<LinkedTreeMap<String, Double>> list;

    public MyAdapter(Context context, List<LinkedTreeMap<String, Double>> list) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = layoutInflater.inflate(R.layout.item_tv, null);
        TextView tv = (TextView) v.findViewById(R.id.tv);
        String key = getKeyOrNull(list.get(position));
        tv.setText(key.equals("null") ? "" : NumberUtils.getTwoN(list.get(position).get(key)) + "(℃)");
        return v;
    }

    /**
     * 获取map中第一个key值
     *
     * @param map 数据源
     * @return
     */
    private String getKeyOrNull(Map<String, Double> map) {
        String obj = null;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            obj = entry.getKey();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }
}
