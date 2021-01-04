package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-08-21
//*********************************************************

import android.content.Context;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.data.BSNotification;

public class BSNotificationUtils {

  public static boolean insertNotification(Context ctx,BSNotification notification){
    DBManager.init(ctx);



    return DBManager.insertCache(CacheKeyConstant.NOTIFICATION_CACHE+notification.getTimestamp(),notification);
  }

}
