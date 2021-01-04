package com.meetutech.baosteel.adapter;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.adapter
// Author: culm at 2017-05-01
//*********************************************************

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class CommonInfoViewPageAdapter extends PagerAdapter {

  private Context ctx;
  private List<View> viewList;

  public CommonInfoViewPageAdapter(Context ctx, List<View> viewList) {
    this.ctx = ctx;
    this.viewList = viewList;
  }

  @Override public int getItemPosition(Object object) {
    int index = viewList.indexOf(object);
    if (index == -1) {
      return POSITION_NONE;
    } else {
      return index;
    }
  }

  public View getView(int position) {
    return viewList.get(position);
  }

  @Override public int getCount() {
    return viewList == null ? 0 : viewList.size();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  public int addView(View v) {
    return addView(v, viewList.size());
  }

  public int addView(View v, int position) {
    viewList.add(position, v);
    return position;
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView(viewList.get(position));
  }

  public int removeView(ViewPager pager, View v) {
    return removeView(pager, viewList.indexOf(v));
  }

  public int removeView(ViewPager pager, int position) {

    pager.setAdapter(null);
    viewList.remove(position);
    pager.setAdapter(this);
    return position;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    View v = viewList.get(position);
    container.addView(v);
    return v;
  }
}
