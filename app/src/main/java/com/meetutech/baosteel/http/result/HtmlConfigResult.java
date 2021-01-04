package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-02
//*********************************************************

import java.io.Serializable;

public class HtmlConfigResult implements Serializable{

  private boolean success;
  private HtmlResult[] data;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public HtmlResult[] getData() {
    return data;
  }

  public void setData(HtmlResult[] data) {
    this.data = data;
  }
}
