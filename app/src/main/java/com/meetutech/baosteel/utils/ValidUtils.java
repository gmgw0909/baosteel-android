package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-04-27
//*********************************************************

import android.text.TextUtils;

public class ValidUtils {
  public static boolean checkPhone(String mobiles) {
    String telRegex = "[1][34578]\\d{9}" ;
    if (TextUtils.isEmpty(mobiles)) return false ;
    else return mobiles.matches( telRegex ) ;
  }
  public static boolean checkPassword(String password){
    if(TextUtils.isEmpty(password)){
      return false;
    }
    return true;
  }
}
