package com.meetutech.baosteel.ui.adapter;

import android.content.Context;

import com.google.gson.internal.LinkedTreeMap;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.http.result.AllPartItem;
import com.meetutech.baosteel.ui.adapter.base.CommonBaseAdapter;
import com.meetutech.baosteel.utils.NumberUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建者 LeeBoo
 * 创建时间 2020/8/8
 */

public class OnePartItemAdapter extends CommonBaseAdapter<LinkedTreeMap<String, Double>> {
    public OnePartItemAdapter(Context context, List<LinkedTreeMap<String, Double>> data) {
        super(context, data, false);
    }

    @Override
    protected void convert(ViewHolder holder, LinkedTreeMap<String, Double> data, int position) {
        String dw = "";
        if (getHead(data).getKey().contains("流量") || getHead(data).getKey().contains("O")) {
            dw = "(Nm3/h)";
        } else if (getHead(data).getKey().contains("压力")) {
            dw = "(Pa)";
        } else if (getHead(data).getKey().contains("温度")) {
            dw = "(摄氏度)";
        }
        holder.setText(R.id.tv_key, getHead(data).getKey() + "\n" + dw);
        holder.setText(R.id.tv_value, NumberUtils.getTwoN(getHead(data).getValue()));
    }

    public <K, V> Map.Entry<K, V> getHead(LinkedTreeMap<K, V> map) {
        return map.entrySet().iterator().next();
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_all_part_sub;
    }
}
