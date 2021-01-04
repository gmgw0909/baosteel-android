package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-02
//*********************************************************

import java.io.Serializable;

public class HtmlValue implements Serializable{

  private String html;

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }
}
