package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-08-16
//*********************************************************

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

public class BDPshUtils {
  public static final String TAG = BDPshUtils.class.getSimpleName();
  public static String logStringCache = "";
  public static final String PUSH_API_KEY = "p4aauOiNYw4EjgT88fBeV8Yv";

  // 获取ApiKey
  public static String getMetaValue(Context context, String metaKey) {
    Bundle metaData = null;
    String apiKey = null;
    if (context == null || metaKey == null) {
      return null;
    }
    try {
      ApplicationInfo ai = context.getPackageManager()
          .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
      if (null != ai) {
        metaData = ai.metaData;
      }
      if (null != metaData) {
        apiKey = metaData.getString(metaKey);
      }
    } catch (PackageManager.NameNotFoundException e) {
      Log.e(TAG, "error " + e.getMessage());
    }
    return apiKey;
  }

  public static void initWithApiKey(Activity activity) {
    PushManager.startWork(activity.getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
        PUSH_API_KEY);
  }

  public static void unBindForApp(Context context) {
    PushManager.stopWork(context);
  }
}
