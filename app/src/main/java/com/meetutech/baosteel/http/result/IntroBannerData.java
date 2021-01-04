package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-02
//*********************************************************

import java.io.Serializable;

public class IntroBannerData implements Serializable{

  private String id;
  private String title;
  private String key;
  private IntroBannerValue content;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public IntroBannerValue getContent() {
    return content;
  }

  public void setContent(IntroBannerValue content) {
    this.content = content;
  }
}
