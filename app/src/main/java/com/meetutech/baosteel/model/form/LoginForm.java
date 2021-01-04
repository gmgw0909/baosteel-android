package com.meetutech.baosteel.model.form;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.form
// Author: culm at 2017-04-27
//*********************************************************

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.exception.MTValidException;
import com.meetutech.baosteel.model.MTFormObject;
import com.meetutech.baosteel.utils.ValidUtils;

public class LoginForm extends MTFormObject{

  private String username;
  private String password;
  private String clientType="Android-"+android.os.Build.BRAND+"-"+ Build.MODEL+"-SDK Version:"+android.os.Build.VERSION.SDK;
  private String deviceId= Settings.Secure.getString(BSApplication.getInstance().getContentResolver(),
      Settings.Secure.ANDROID_ID);
  public LoginForm(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public boolean checkForm(Context context) throws MTValidException {

    if(!ValidUtils.checkPhone(username)){
      throw new MTValidException(context.getString(R.string.valid_phone));
    }
    if(!ValidUtils.checkPassword(password)){
      throw new MTValidException(context.getString(R.string.valid_password));
    }

    return true;
  }
}
