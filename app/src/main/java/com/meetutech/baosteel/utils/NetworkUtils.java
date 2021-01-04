package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-08-09
//*********************************************************

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.meetutech.baosteel.R;

public class NetworkUtils {
  public static boolean isOnline(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    return netInfo != null && netInfo.isConnectedOrConnecting();
  }

  public static String getDefaultErrorMessage(Context ctx,String errorMessage){
    String res=ctx.getString(R.string.load_error);
    if(!TextUtils.isEmpty(errorMessage)){
      res=errorMessage;
    }
    return res;
  }

}
