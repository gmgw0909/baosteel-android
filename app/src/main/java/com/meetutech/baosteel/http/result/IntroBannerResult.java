package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-02
//*********************************************************

import java.io.Serializable;

public class IntroBannerResult implements Serializable{

  private boolean success;
  private int statusCode;

  private IntroBannerData data;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public IntroBannerData getData() {
    return data;
  }

  public void setData(IntroBannerData data) {
    this.data = data;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }
}
