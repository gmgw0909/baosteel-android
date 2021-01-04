package com.meetutech.baosteel.data;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.data
// Author: culm at 2017-08-21
//*********************************************************

import com.meetutech.baosteel.model.MTDataObject;

public class BSNotification extends MTDataObject{

  private String title;
  private String message;
  private long timestamp;
  private boolean isReaded;

  public BSNotification(String title, String message, long timestamp) {
    this.title = title;
    this.message = message;
    this.timestamp = timestamp;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public boolean isReaded() {
    return isReaded;
  }

  public void setReaded(boolean readed) {
    isReaded = readed;
  }
}
