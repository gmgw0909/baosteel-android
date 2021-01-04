package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-02
//*********************************************************

import com.meetutech.baosteel.utils.Base64Utils;

public class HtmlResult {

  private String id;
  private String title;
  private HtmlValue value;

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

  public HtmlValue getValue() {
    return value;
  }

  public void setValue(HtmlValue value) {
    this.value = value;
  }

  public String toHtml(){
    return Base64Utils.decodeString(getValue().getHtml());
  }


}
