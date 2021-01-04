package com.meetutech.baosteel.adapter;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.adapter
// Author: culm at 2017-05-01
//*********************************************************

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.model.http.ExpertAdvice;
import com.meetutech.baosteel.utils.ViewUtils;
import java.util.List;

public class ExpAdviceListAdapter extends BaseAdapter {

  private Context ctx;
  private List<ExpertAdvice> data;

  public ExpAdviceListAdapter(Context ctx,List<ExpertAdvice> data){
    this.ctx=ctx;
    this.data=data;
  }

  @Override
  public int getCount() {
    return data == null ? 0 : data.size();
  }

  @Override
  public Object getItem(int i) {
    return i;
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View convertView, ViewGroup viewGroup) {
    View view = convertView;
    final ViewHolder holder;
    ExpertAdvice exp=data.get(i);
    if (convertView == null) {
      view = View.inflate(ctx, R.layout.item_list_exp_advice, null);
      holder = new ViewHolder();
      holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
      ViewUtils.adjustViewTextSize(holder.tv_name,35);


      AbsListView.LayoutParams absLayoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, ViewUtils.getDimenByRateWidth(106));
      view.setLayoutParams(absLayoutParams);
      view.setTag(holder);

    } else {
      holder = (ViewHolder) view.getTag();
    }

    try {
      loadData(holder, exp);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return view;
  }

  private void loadData(ViewHolder holder, ExpertAdvice exp) throws Exception{
      holder.tv_name.setText(exp.getName());
  }

  public static class ViewHolder {
    public TextView tv_name;

  }

}
