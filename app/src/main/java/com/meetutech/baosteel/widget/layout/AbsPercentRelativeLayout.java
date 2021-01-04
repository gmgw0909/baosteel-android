package com.meetutech.baosteel.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

//**********************************************************
// Assignment: Tarcle-user-android
// Package:com.Tarcle.app.widget.percentLayout
// Author: culm at 2015-09-06
//*********************************************************
public class AbsPercentRelativeLayout extends RelativeLayout {
  private final AbsPercentLayoutHelper
      mHelper = new AbsPercentLayoutHelper(this);

  public AbsPercentRelativeLayout(Context context) {
    super(context);
  }

  public AbsPercentRelativeLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AbsPercentRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public AbsPercentRelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new AbsPercentRelativeLayout.LayoutParams(this.getContext(), attrs);
  }

  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    this.mHelper.adjustChildren(widthMeasureSpec, heightMeasureSpec);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    if(this.mHelper.handleMeasuredStateTooSmall()) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }

  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    this.mHelper.restoreOriginalParams();
  }

  public static class LayoutParams extends android.widget.RelativeLayout.LayoutParams implements
      AbsPercentLayoutHelper.PercentLayoutParams {
    private AbsPercentLayoutHelper.PercentLayoutInfo mPercentLayoutInfo;

    public LayoutParams(Context c, AttributeSet attrs) {
      super(c, attrs);
      this.mPercentLayoutInfo = AbsPercentLayoutHelper.getPercentLayoutInfo(c, attrs);
    }

    public LayoutParams(int width, int height) {
      super(width, height);
    }

    public LayoutParams(android.view.ViewGroup.LayoutParams source) {
      super(source);
    }

    public LayoutParams(ViewGroup.MarginLayoutParams source) {
      super(source);
    }

    public AbsPercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo() {
      if(this.mPercentLayoutInfo == null) {
        this.mPercentLayoutInfo = new AbsPercentLayoutHelper.PercentLayoutInfo();
      }

      return this.mPercentLayoutInfo;
    }

    protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
      AbsPercentLayoutHelper.fetchWidthAndHeight(this, a, widthAttr, heightAttr);
    }
  }
}
