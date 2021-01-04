package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-02
//*********************************************************

import com.meetutech.baosteel.model.UserInfo;
import java.io.Serializable;

public class UserInfoResult implements Serializable{
  private boolean success;
  private UserInfo data;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public UserInfo getData() {
    return data;
  }

  public void setData(UserInfo data) {
    this.data = data;
  }
}
