package com.meetutech.baosteel.widget.layout;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.widget
// Author: culm at 2017-04-25
//*********************************************************

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.R;

public class AbsPercentLayoutHelper {
  private static final String TAG = "PercentLayout";
  private final ViewGroup mHost;

  public AbsPercentLayoutHelper(ViewGroup host) {
    this.mHost = host;
  }

  public static void fetchWidthAndHeight(ViewGroup.LayoutParams params, TypedArray array, int widthAttr, int heightAttr) {
    params.width = array.getLayoutDimension(widthAttr, 0);
    params.height = array.getLayoutDimension(heightAttr, 0);
  }

  public void adjustChildren(int widthMeasureSpec, int heightMeasureSpec) {
    if(Log.isLoggable("PercentLayout", Log.DEBUG)) {
      Log.d("PercentLayout",
          "adjustChildren: " + this.mHost + " widthMeasureSpec: " + View.MeasureSpec.toString(
              widthMeasureSpec) + " heightMeasureSpec: " + View.MeasureSpec.toString(
              heightMeasureSpec));
    }

    //int widthHint = View.MeasureSpec.getSize(widthMeasureSpec);
    //int heightHint = View.MeasureSpec.getSize(heightMeasureSpec);
    int widthHint = BSApplication.getDisplayMetrics().widthPixels;
    int heightHint = BSApplication.getDisplayMetrics().widthPixels;
    int i = 0;

    for(int N = this.mHost.getChildCount(); i < N; ++i) {
      View view = this.mHost.getChildAt(i);
      ViewGroup.LayoutParams params = view.getLayoutParams();
      if(Log.isLoggable("PercentLayout", Log.DEBUG)) {
        Log.d("PercentLayout", "should adjust " + view + " " + params);
      }

      if(params instanceof AbsPercentLayoutHelper.PercentLayoutParams) {
        AbsPercentLayoutHelper.PercentLayoutInfo info = ((AbsPercentLayoutHelper.PercentLayoutParams)params).getPercentLayoutInfo();
        if(Log.isLoggable("PercentLayout", Log.DEBUG)) {
          Log.d("PercentLayout", "using " + info);
        }

        if(info != null) {
          if(params instanceof ViewGroup.MarginLayoutParams) {
            info.fillMarginLayoutParams((ViewGroup.MarginLayoutParams)params, widthHint, heightHint);
          } else {
            info.fillLayoutParams(params, widthHint, heightHint);
          }
        }
      }
    }

  }

  public static AbsPercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo(Context context, AttributeSet attrs) {
    AbsPercentLayoutHelper.PercentLayoutInfo info = null;
    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PercentLayout_Layout);
    float value = array.getFraction(R.styleable.PercentLayout_Layout_layout_widthPercent, 1, 1, -1.0F);
    if(value != -1.0F) {
      if(Log.isLoggable("PercentLayout", Log.VERBOSE)) {
        Log.v("PercentLayout", "percent width: " + value);
      }

      info = info != null?info:new AbsPercentLayoutHelper.PercentLayoutInfo();
      info.widthPercent = value;
    }

    value = array.getFraction(R.styleable.PercentLayout_Layout_layout_heightPercent, 1, 1, -1.0F);
    if(value != -1.0F) {
      if(Log.isLoggable("PercentLayout", Log.VERBOSE)) {
        Log.v("PercentLayout", "percent height: " + value);
      }

      info = info != null?info:new AbsPercentLayoutHelper.PercentLayoutInfo();
      info.heightPercent = value;
    }

    value = array.getFraction(R.styleable.PercentLayout_Layout_layout_marginPercent, 1, 1, -1.0F);
    if(value != -1.0F) {
      if(Log.isLoggable("PercentLayout", Log.VERBOSE)) {
        Log.v("PercentLayout", "percent margin: " + value);
      }

      info = info != null?info:new AbsPercentLayoutHelper.PercentLayoutInfo();
      info.leftMarginPercent = value;
      info.topMarginPercent = value;
      info.rightMarginPercent = value;
      info.bottomMarginPercent = value;
    }

    value = array.getFraction(R.styleable.PercentLayout_Layout_layout_marginLeftPercent, 1, 1, -1.0F);
    if(value != -1.0F) {
      if(Log.isLoggable("PercentLayout", Log.VERBOSE)) {
        Log.v("PercentLayout", "percent left margin: " + value);
      }

      info = info != null?info:new AbsPercentLayoutHelper.PercentLayoutInfo();
      info.leftMarginPercent = value;
    }

    value = array.getFraction(R.styleable.PercentLayout_Layout_layout_marginTopPercent, 1, 1, -1.0F);
    if(value != -1.0F) {
      if(Log.isLoggable("PercentLayout", Log.VERBOSE)) {
        Log.v("PercentLayout", "percent top margin: " + value);
      }

      info = info != null?info:new AbsPercentLayoutHelper.PercentLayoutInfo();
      info.topMarginPercent = value;
    }

    value = array.getFraction(R.styleable.PercentLayout_Layout_layout_marginRightPercent, 1, 1, -1.0F);
    if(value != -1.0F) {
      if(Log.isLoggable("PercentLayout", Log.VERBOSE)) {
        Log.v("PercentLayout", "percent right margin: " + value);
      }

      info = info != null?info:new AbsPercentLayoutHelper.PercentLayoutInfo();
      info.rightMarginPercent = value;
    }

    value = array.getFraction(R.styleable.PercentLayout_Layout_layout_marginBottomPercent, 1, 1, -1.0F);
    if(value != -1.0F) {
      if(Log.isLoggable("PercentLayout", Log.VERBOSE)) {
        Log.v("PercentLayout", "percent bottom margin: " + value);
      }

      info = info != null?info:new AbsPercentLayoutHelper.PercentLayoutInfo();
      info.bottomMarginPercent = value;
    }

    value = array.getFraction(R.styleable.PercentLayout_Layout_layout_marginStartPercent, 1, 1, -1.0F);
    if(value != -1.0F) {
      if(Log.isLoggable("PercentLayout", Log.VERBOSE)) {
        Log.v("PercentLayout", "percent start margin: " + value);
      }

      info = info != null?info:new AbsPercentLayoutHelper.PercentLayoutInfo();
      info.startMarginPercent = value;
    }

    value = array.getFraction(R.styleable.PercentLayout_Layout_layout_marginEndPercent, 1, 1, -1.0F);
    if(value != -1.0F) {
      if(Log.isLoggable("PercentLayout", Log.VERBOSE)) {
        Log.v("PercentLayout", "percent end margin: " + value);
      }

      info = info != null?info:new AbsPercentLayoutHelper.PercentLayoutInfo();
      info.endMarginPercent = value;
    }

    value = array.getFraction(R.styleable.PercentLayout_Layout_textSize, 1, 1, -1.0F);
    if(value != -1.0F) {
      if(Log.isLoggable("PercentLayout", Log.VERBOSE)) {
        Log.v("PercentLayout", "percent start margin: " + value);
      }

      info = info != null?info:new AbsPercentLayoutHelper.PercentLayoutInfo();
      info.textSize = value;
    }

    array.recycle();
    if(Log.isLoggable("PercentLayout", Log.DEBUG)) {
      Log.d("PercentLayout", "constructed: " + info);
    }

    return info;
  }

  public void restoreOriginalParams() {
    int i = 0;

    for(int N = this.mHost.getChildCount(); i < N; ++i) {
      View view = this.mHost.getChildAt(i);
      ViewGroup.LayoutParams params = view.getLayoutParams();
      if(Log.isLoggable("PercentLayout", Log.DEBUG)) {
        Log.d("PercentLayout", "should restore " + view + " " + params);
      }

      if(params instanceof AbsPercentLayoutHelper.PercentLayoutParams) {
        AbsPercentLayoutHelper.PercentLayoutInfo info = ((AbsPercentLayoutHelper.PercentLayoutParams)params).getPercentLayoutInfo();
        if(Log.isLoggable("PercentLayout", Log.DEBUG)) {
          Log.d("PercentLayout", "using " + info);
        }

        if(info != null) {
          if(params instanceof ViewGroup.MarginLayoutParams) {
            info.restoreMarginLayoutParams((ViewGroup.MarginLayoutParams)params);
          } else {
            info.restoreLayoutParams(params);
          }
        }
      }
    }

  }

  public boolean handleMeasuredStateTooSmall() {
    boolean needsSecondMeasure = false;
    int i = 0;

    for(int N = this.mHost.getChildCount(); i < N; ++i) {
      View view = this.mHost.getChildAt(i);
      ViewGroup.LayoutParams params = view.getLayoutParams();
      if(Log.isLoggable("PercentLayout", Log.DEBUG)) {
        Log.d("PercentLayout", "should handle measured state too small " + view + " " + params);
      }

      if(params instanceof AbsPercentLayoutHelper.PercentLayoutParams) {
        AbsPercentLayoutHelper.PercentLayoutInfo info = ((AbsPercentLayoutHelper.PercentLayoutParams)params).getPercentLayoutInfo();
        if(info != null) {
          if(shouldHandleMeasuredWidthTooSmall(view, info)) {
            needsSecondMeasure = true;
            params.width = -2;
          }

          if(shouldHandleMeasuredHeightTooSmall(view, info)) {
            needsSecondMeasure = true;
            params.height = -2;
          }
        }
      }
    }

    if(Log.isLoggable("PercentLayout", Log.DEBUG)) {
      Log.d("PercentLayout", "should trigger second measure pass: " + needsSecondMeasure);
    }

    return needsSecondMeasure;
  }

  private static boolean shouldHandleMeasuredWidthTooSmall(View view, AbsPercentLayoutHelper.PercentLayoutInfo info) {
    int state = ViewCompat.getMeasuredWidthAndState(view) & -16777216;
    return state == 16777216 && info.widthPercent >= 0.0F && info.mPreservedParams.width == -2;
  }

  private static boolean shouldHandleMeasuredHeightTooSmall(View view, AbsPercentLayoutHelper.PercentLayoutInfo info) {
    int state = ViewCompat.getMeasuredHeightAndState(view) & -16777216;
    return state == 16777216 && info.heightPercent >= 0.0F && info.mPreservedParams.height == -2;
  }

  public interface PercentLayoutParams {
    AbsPercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo();
  }

  public static class PercentLayoutInfo {
    public float widthPercent = -1.0F;
    public float heightPercent = -1.0F;
    public float leftMarginPercent = -1.0F;
    public float topMarginPercent = -1.0F;
    public float rightMarginPercent = -1.0F;
    public float bottomMarginPercent = -1.0F;
    public float startMarginPercent = -1.0F;
    public float endMarginPercent = -1.0F;
    final ViewGroup.MarginLayoutParams mPreservedParams = new ViewGroup.MarginLayoutParams(0, 0);
    public float textSize=-1.0F;

    public PercentLayoutInfo() {
    }

    public void fillLayoutParams(ViewGroup.LayoutParams params, int widthHint, int heightHint) {
      this.mPreservedParams.width = params.width;
      this.mPreservedParams.height = params.height;
      if(this.widthPercent >= 0.0F) {
        params.width = (int)((float)widthHint * this.widthPercent);
      }

      if(this.heightPercent >= 0.0F) {
        params.height = (int)((float)heightHint * this.heightPercent);
      }

      if(Log.isLoggable("PercentLayout", Log.DEBUG)) {
        Log.d("PercentLayout",
            "after fillLayoutParams: (" + params.width + ", " + params.height + ")");
      }

    }

    public void fillMarginLayoutParams(ViewGroup.MarginLayoutParams params, int widthHint, int heightHint) {
      this.fillLayoutParams(params, widthHint, heightHint);
      this.mPreservedParams.leftMargin = params.leftMargin;
      this.mPreservedParams.topMargin = params.topMargin;
      this.mPreservedParams.rightMargin = params.rightMargin;
      this.mPreservedParams.bottomMargin = params.bottomMargin;
      MarginLayoutParamsCompat.setMarginStart(this.mPreservedParams,
          MarginLayoutParamsCompat.getMarginStart(params));
      MarginLayoutParamsCompat.setMarginEnd(this.mPreservedParams,
          MarginLayoutParamsCompat.getMarginEnd(params));
      if(this.leftMarginPercent >= 0.0F) {
        params.leftMargin = (int)((float)widthHint * this.leftMarginPercent);
      }

      if(this.topMarginPercent >= 0.0F) {
        params.topMargin = (int)((float)heightHint * this.topMarginPercent);
      }

      if(this.rightMarginPercent >= 0.0F) {
        params.rightMargin = (int)((float)widthHint * this.rightMarginPercent);
      }

      if(this.bottomMarginPercent >= 0.0F) {
        params.bottomMargin = (int)((float)heightHint * this.bottomMarginPercent);
      }

      if(this.startMarginPercent >= 0.0F) {
        MarginLayoutParamsCompat.setMarginStart(params,
            (int) ((float) widthHint * this.startMarginPercent));
      }

      if(this.endMarginPercent >= 0.0F) {
        MarginLayoutParamsCompat.setMarginEnd(params,
            (int) ((float) widthHint * this.endMarginPercent));
      }

      if(Log.isLoggable("PercentLayout", Log.DEBUG)) {
        Log.d("PercentLayout",
            "after fillMarginLayoutParams: (" + params.width + ", " + params.height + ")");
      }

    }

    public String toString() {
      return String.format(
          "PercentLayoutInformation width: %f height %f, margins (%f, %f,  %f, %f, %f, %f)",
          new Object[] {
              Float.valueOf(this.widthPercent), Float.valueOf(this.heightPercent),
              Float.valueOf(this.leftMarginPercent), Float.valueOf(this.topMarginPercent),
              Float.valueOf(this.rightMarginPercent), Float.valueOf(this.bottomMarginPercent),
              Float.valueOf(this.startMarginPercent), Float.valueOf(this.endMarginPercent)
          });
    }

    public void restoreMarginLayoutParams(ViewGroup.MarginLayoutParams params) {
      this.restoreLayoutParams(params);
      params.leftMargin = this.mPreservedParams.leftMargin;
      params.topMargin = this.mPreservedParams.topMargin;
      params.rightMargin = this.mPreservedParams.rightMargin;
      params.bottomMargin = this.mPreservedParams.bottomMargin;
      MarginLayoutParamsCompat.setMarginStart(params,
          MarginLayoutParamsCompat.getMarginStart(this.mPreservedParams));
      MarginLayoutParamsCompat.setMarginEnd(params,
          MarginLayoutParamsCompat.getMarginEnd(this.mPreservedParams));
    }

    public void restoreLayoutParams(ViewGroup.LayoutParams params) {
      params.width = this.mPreservedParams.width;
      params.height = this.mPreservedParams.height;
    }
  }
}
