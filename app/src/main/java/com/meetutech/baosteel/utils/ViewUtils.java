package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-04-26
//*********************************************************

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.meetutech.baosteel.BSApplication;

public class ViewUtils {

  public static final float WIDTH=720.0f;
  public static final float HEIGHT=1080.0f;

  public static final float SMALL_FONT_SCALE=0.9f;
  public static final float NORMAL_FONT_SCALE=1.0f;
  public static final float LARGE_FONT_SCALE=1.1f;

  private static float FONT_SCALE=1.0f;

  public static float LANDSCAPE_RATE=WIDTH/HEIGHT;

  public static DisplayMetrics getScreenResolution(Activity activity) {
    DisplayMetrics dm = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    return dm;
  }

  public static float getFontScale() {
    return FONT_SCALE;
  }

  public static void setFontScale(float fontScale) {
    FONT_SCALE = fontScale;
  }

  public static void adjustViewTextSize(TextView v,int size){
    v.setTextSize(TypedValue.COMPLEX_UNIT_PX, FONT_SCALE*getDimenByRateWidth(size));
  }

  public static void adjustViewTextSizeLandscape(TextView v,int size){
    v.setTextSize(TypedValue.COMPLEX_UNIT_PX, LANDSCAPE_RATE*FONT_SCALE*getDimenByRateHeight(size));
  }


  public static int getDimenByRateWidth(float dimen)
  {
    return ((int)(BSApplication.getDisplayMetrics().widthPixels*(dimen/WIDTH)));
  }


  public static int getDimenByRateHeight(float dimen)
  {
    return ((int)(BSApplication.getDisplayMetrics().heightPixels*(dimen/HEIGHT)));
  }

  public static View getRootView(Activity context) {
    return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
  }

  public static void setTypeFace(Context context,TextView tv,String typeface){
    tv.setTypeface(TypeFaceUtils.getTypeByName(context,typeface));
  }
  public static String ellipsize(String input, int maxCharacters, int charactersAfterEllipsis) {
    if(maxCharacters < 3) {
      throw new IllegalArgumentException("maxCharacters must be at least 3 because the ellipsis already take up 3 characters");
    }
    if(maxCharacters - 3 > charactersAfterEllipsis) {
      throw new IllegalArgumentException("charactersAfterEllipsis must be less than maxCharacters");
    }
    if (input == null || input.length() < maxCharacters) {
      return input;
    }
    return input.substring(0, maxCharacters - 3 - charactersAfterEllipsis) + "..." + input.substring(input.length() - charactersAfterEllipsis);
  }
}
