package com.meetutech.baosteel.adapter;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.adapter
// Author: culm at 2017-05-01
//*********************************************************

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class ExpInfoVIewPageAdapter extends PagerAdapter{

  private Context ctx;
  private List<String> data;
  private List<View> viewList;
  public ExpInfoVIewPageAdapter(Context ctx,List<View> viewList, List<String> data) {
    this.ctx = ctx;
    this.viewList=viewList;
    this.data = data;
  }

  @Override public int getCount() {
    return viewList==null?0:viewList.size();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view==object;
  }
  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {

    container.removeView(viewList.get(position));

  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    container.addView(viewList.get(position));
    return viewList.get(position);
    //String description=data.get(position);
    //View item=View.inflate(ctx, R.layout.item_project_view_pager,null);
    //ViewUtils.adjustViewTextSize((TextView) item.findViewById(R.id.tv_description),28);
    //((TextView) item.findViewById(R.id.tv_description)).setText(description);
    //return item;
  }
}
