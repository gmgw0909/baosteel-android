package com.meetutech.baosteel.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.meetutech.baosteel.R;
import com.meetutech.baosteel.http.result.AllPartResult2;
import com.meetutech.baosteel.ui.adapter.base.CommonBaseAdapter;

/**
 * 创建者 LeeBoo
 * 创建时间 2020/8/8
 */

public class AllPartAdapter extends CommonBaseAdapter<AllPartResult2> {
    public AllPartAdapter(Context context) {
        super(context, null, false);
    }

    @Override
    protected void convert(ViewHolder holder, AllPartResult2 data, int position) {
        holder.setText(R.id.tv_name, data.getType());
        RecyclerView innRv = holder.getView(R.id.inner_rv);
        innRv.setLayoutManager(new GridLayoutManager(mContext, 2));
        innRv.addItemDecoration(new MyItemDecoration(mContext, LinearLayoutManager.HORIZONTAL));
        AllPartItemAdapter adapter = new AllPartItemAdapter(mContext, data.getValues());
        innRv.setAdapter(adapter);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_all_part;
    }
}
