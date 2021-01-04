package com.meetutech.baosteel.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

//**********************************************************
// Assignment: Tarcle-user-android
// Package:com.tarcle.app.widget.percent
// Author: culm at 2015-10-09
//*********************************************************
public class AbsPercentLinearLayout extends LinearLayout {

  private AbsPercentLayoutHelper mAbsPercentLayoutHelper;

  public AbsPercentLinearLayout(Context context, AttributeSet attrs) {
    super(context, attrs);

    mAbsPercentLayoutHelper = new AbsPercentLayoutHelper(this);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    mAbsPercentLayoutHelper.adjustChildren(widthMeasureSpec, heightMeasureSpec);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    if (mAbsPercentLayoutHelper.handleMeasuredStateTooSmall()) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    mAbsPercentLayoutHelper.restoreOriginalParams();
  }

  @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new LayoutParams(getContext(), attrs);
  }

  public static class LayoutParams extends LinearLayout.LayoutParams
      implements AbsPercentLayoutHelper.PercentLayoutParams {
    private AbsPercentLayoutHelper.PercentLayoutInfo mPercentLayoutInfo;

    public LayoutParams(Context c, AttributeSet attrs) {
      super(c, attrs);
      mPercentLayoutInfo = AbsPercentLayoutHelper.getPercentLayoutInfo(c, attrs);
    }

    @Override public AbsPercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo() {
      return mPercentLayoutInfo;
    }

    @Override protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
      AbsPercentLayoutHelper.fetchWidthAndHeight(this, a, widthAttr, heightAttr);
    }

    public LayoutParams(int width, int height) {
      super(width, height);
    }

    public LayoutParams(ViewGroup.LayoutParams source) {
      super(source);
    }

    public LayoutParams(MarginLayoutParams source) {
      super(source);
    }
  }
}
