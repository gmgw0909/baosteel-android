package com.meetutech.baosteel.data;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.data
// Author: culm at 2017-08-17
//*********************************************************

import com.meetutech.baosteel.model.MTDataObject;

public class UserConfig extends MTDataObject{

  private boolean isPush=true;
  private float fontScale;

  public boolean isPush() {
    return isPush;
  }

  public void setPush(boolean push) {
    isPush = push;
  }

  public float getFontScale() {
    return fontScale;
  }

  public void setFontScale(float fontScale) {
    this.fontScale = fontScale;
  }

  @Override public String toString() {
    return "UserConfig{" + "isPush=" + isPush + ", fontScale=" + fontScale + '}';
  }
}
