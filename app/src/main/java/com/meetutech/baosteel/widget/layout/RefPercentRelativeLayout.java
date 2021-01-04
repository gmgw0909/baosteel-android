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
public class RefPercentRelativeLayout extends RelativeLayout {

  private final RefPercentLayoutHelper
      mHelper = new RefPercentLayoutHelper(this);

  public RefPercentRelativeLayout(Context context) {
    super(context);
  }

  public RefPercentRelativeLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public RefPercentRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public RefPercentRelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new RefPercentRelativeLayout.LayoutParams(this.getContext(), attrs);
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

  public static class LayoutParams extends RelativeLayout.LayoutParams implements
      RefPercentLayoutHelper.PercentLayoutParams {
    private RefPercentLayoutHelper.PercentLayoutInfo mPercentLayoutInfo;

    public LayoutParams(Context c, AttributeSet attrs) {
      super(c, attrs);
      this.mPercentLayoutInfo = RefPercentLayoutHelper.getPercentLayoutInfo(c, attrs);
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

    public RefPercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo() {
      if(this.mPercentLayoutInfo == null) {
        this.mPercentLayoutInfo = new RefPercentLayoutHelper.PercentLayoutInfo();
      }

      return this.mPercentLayoutInfo;
    }

    protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
      RefPercentLayoutHelper.fetchWidthAndHeight(this, a, widthAttr, heightAttr);
    }
  }
}
