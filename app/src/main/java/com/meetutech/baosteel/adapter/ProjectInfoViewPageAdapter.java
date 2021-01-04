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

public class ProjectInfoViewPageAdapter extends PagerAdapter{

  private Context ctx;
  private List<String> data;
  private List<View> viewList;
  public ProjectInfoViewPageAdapter(Context ctx,List<View> viewList, List<String> data) {
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
  }
}
