package com.meetutech.baosteel.ui.adapter;

import android.content.Context;

import com.meetutech.baosteel.R;
import com.meetutech.baosteel.http.result.AllPartItem;
import com.meetutech.baosteel.http.result.AllPartResult2;
import com.meetutech.baosteel.ui.adapter.base.CommonBaseAdapter;

import java.util.List;

/**
 * 创建者 LeeBoo
 * 创建时间 2020/8/8
 */

public class AllPartItemAdapter extends CommonBaseAdapter<AllPartItem> {
    public AllPartItemAdapter(Context context, List<AllPartItem> data) {
        super(context, data, false);
    }

    @Override
    protected void convert(ViewHolder holder, AllPartItem data, int position) {
        String dw = "";
        if (data.getItemName().contains("流量") || data.getItemName().contains("O")) {
            dw = "(Nm3/h)";
        } else if (data.getItemName().contains("压力")) {
            dw = "(Pa)";
        } else if (data.getItemName().contains("温度")) {
            dw = "(摄氏度)";
        }
        holder.setText(R.id.tv_key, data.getItemName() + dw);
        holder.setText(R.id.tv_value, String.valueOf(data.getItemValue()));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_all_part_sub;
    }
}
