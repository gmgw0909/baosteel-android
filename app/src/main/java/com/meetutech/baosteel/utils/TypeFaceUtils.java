package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-04-26
//*********************************************************

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import java.util.HashMap;
import java.util.Map;

public class TypeFaceUtils {
  public static final String ROOT_PATH = "fonts/";
  public static final String SF_UI_TEXT_MEDIUM = "SF-UI-Text-Medium.otf";
  public static final String SF_UI_TEXT_SEMIBOLD = "SF-UI-Text-SemiboldItalic.otf";

  private static Typeface sf_ui_text_medium;
  private static Typeface sf_ui_text_semibold;

  private static Map<String, Typeface> tfMap = new HashMap<>();

  static {
    tfMap.put(SF_UI_TEXT_MEDIUM,sf_ui_text_medium);
    tfMap.put(SF_UI_TEXT_SEMIBOLD,sf_ui_text_semibold);
  }

  public static Typeface getTypeByName(Context ctx, String name) {

    if (!tfMap.containsKey(name)) {
      return null;
    }
    Typeface tp = tfMap.get(name);
    if (tp == null) {
      AssetManager mgr = ctx.getAssets();
      tp = Typeface.createFromAsset(mgr, ROOT_PATH+name);
      tfMap.put(name, tp);
    }
    return tp;

  }
}
