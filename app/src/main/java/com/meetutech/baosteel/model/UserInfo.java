package com.meetutech.baosteel.model;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model
// Author: culm at 2017-05-25
//*********************************************************

import com.meetutech.chatkit.commons.models.IUser;
import java.io.Serializable;

public class UserInfo implements Serializable,IUser {

  public static final String DEFAULT_AVATAR_URL="file:///android_asset/image/default_avatar.jpg";

  public UserInfo() {
  }

  public UserInfo(String id) {
    this.id = id;
  }

  private String id="";
  private String name="";
  private  String username;
  private boolean phoneVerified;
  private String type;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  @Override public String getAvatar() {
    return DEFAULT_AVATAR_URL;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean isPhoneVerified() {
    return phoneVerified;
  }

  public void setPhoneVerified(boolean phoneVerified) {
    this.phoneVerified = phoneVerified;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
