package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-04-26
//*********************************************************

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.meetutech.baosteel.R;
import java.util.Iterator;
import java.util.List;

public class AppUtils {
  /**
   * @return
   * @brief Get this application packet name
   */
  public static String getAppPacketName(Context context) {
    return context.getPackageName();
  }
  public static String getVersion(Context context)//获取版本号
  {
    try {
      PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return pi.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return context.getString(R.string.version_unknown);
    }
  }
  public static String getAppName(Context context,int pID) {
    String processName = null;
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List l = am.getRunningAppProcesses();
    Iterator i = l.iterator();
    while (i.hasNext()) {
      ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
      try {
        if (info.pid == pID) {
          processName = info.processName;
          return processName;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return processName;
  }
}
