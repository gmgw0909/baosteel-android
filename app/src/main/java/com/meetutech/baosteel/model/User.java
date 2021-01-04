package com.meetutech.baosteel.model;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model
// Author: culm at 2017-04-26
//*********************************************************

import com.meetutech.chatkit.commons.models.IUser;

public class User implements IUser {
  private String userID;
  private String name;

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  @Override public String getId() {
    return getUserID();
  }

  public String getName() {
    return name;
  }

  @Override public String getAvatar() {
    return "";
  }

  public void setName(String name) {
    this.name = name;
  }

}
