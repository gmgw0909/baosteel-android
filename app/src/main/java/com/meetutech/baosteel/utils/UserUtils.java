package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-08-17
//*********************************************************

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.data.UserConfig;
import com.meetutech.baosteel.model.MTDataObject;
import com.meetutech.baosteel.receiver.BDPushMessageReceiver;
import com.meetutech.baosteel.ui.MainActivity;
import com.meetutech.baosteel.ui.user.LoginActivity;

public class UserUtils {

  private static UserConfig currConfig;

  public static boolean logoutTimeout(Activity context){
    try {
      UserUtils.logout(context);
      context.startActivity(new Intent(context, LoginActivity.class));
      context.finish();
      if (MainActivity.instance != null) {
        MainActivity.instance.finish();
      }
    } catch (Exception e){
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean checkAccountLogin() {
    if (DBManager.getCache(CacheKeyConstant.AUTH_KEY) != null) {
      updateUserToken();
      BSApplication.mainUserId = (String) DBManager.getCache(CacheKeyConstant.AUTH_CURR_USER_ID);
      return true;
    }
    return false;
  }

  public static String updateUserToken(){
    if(TextUtils.isEmpty(BSApplication.currAuthToken)) {
      BSApplication.currAuthToken=(String) DBManager.getCache(CacheKeyConstant.AUTH_KEY);
    }
    return BSApplication.currAuthToken;
  }


  public static boolean initUserConfig() {
    if (getUserConfig() != null) {
      return false;
    }

    UserConfig config = new UserConfig();
    config.setFontScale(ViewUtils.NORMAL_FONT_SCALE);
    config.setPush(true);

    return DBManager.insertCache(CacheKeyConstant.APP_USER_CONFIG + BSApplication.mainUserId,
        new MTDataObject(config));
  }

  private static UserConfig getUserConfig() {

    if (TextUtils.isEmpty(BSApplication.mainUserId) | !checkAccountLogin()) {
      return null;
    }
    Object o = DBManager.getCache(CacheKeyConstant.APP_USER_CONFIG + BSApplication.mainUserId);
    if (o == null) {
      return null;
    }
    return (UserConfig) o;
  }

  public static boolean removeUserConfig() {
    return DBManager.removeCache(CacheKeyConstant.APP_USER_CONFIG + BSApplication.mainUserId);
  }

  public static boolean applyUserConfig() {
    currConfig = getUserConfig();
    if (currConfig == null) {
      return false;
    }
    if (currConfig.getFontScale() > 0) {
      ViewUtils.setFontScale(currConfig.getFontScale());
    }
    //推送开关
    BDPushMessageReceiver.isPush = currConfig.isPush();

    return true;
  }

  public static boolean setFontScale(float scale) {
    currConfig = getUserConfig();
    if (currConfig == null) {
      return false;
    }

    currConfig.setFontScale(scale);

    DBManager.insertCache(CacheKeyConstant.APP_USER_CONFIG + BSApplication.mainUserId,
        new MTDataObject(currConfig));

    ViewUtils.setFontScale(scale);

    return true;
  }
  public static float getFontScale() {
    currConfig = getUserConfig();
    if (currConfig == null) {
      return ViewUtils.NORMAL_FONT_SCALE;
    }

    return currConfig.getFontScale();
  }

  public static boolean getPushSwitch() {
    currConfig = getUserConfig();
    if (currConfig == null) {
      return false;
    }

    return currConfig.isPush();
  }

  public static boolean setPushSwitch(Activity activity,boolean isPush) {
    currConfig = getUserConfig();
    if (currConfig == null) {
      return false;
    }

    currConfig.setPush(isPush);

    DBManager.insertCache(CacheKeyConstant.APP_USER_CONFIG + BSApplication.mainUserId,
        new MTDataObject(currConfig));

    BDPushMessageReceiver.isPush=isPush;

    if(isPush) {
      BDPshUtils.initWithApiKey(activity);
    } else {
      BDPshUtils.unBindForApp(activity);
    }
    return true;
  }

  public static void logout(Context context) {
    DBManager.removeCache(CacheKeyConstant.AUTH_IS_LOGIN);
    DBManager.removeCache(CacheKeyConstant.AUTH_INFO);
    DBManager.removeCache(CacheKeyConstant.AUTH_KEY);
    BDPshUtils.unBindForApp(context);
    BSApplication.mainUserId=null;
    BSApplication.currAuthToken=null;
  }

  public static void bindPushService(Activity Activity){
    BDPshUtils.initWithApiKey(Activity);
  }
}
