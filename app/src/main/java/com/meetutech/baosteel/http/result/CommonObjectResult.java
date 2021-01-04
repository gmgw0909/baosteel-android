package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-15
//*********************************************************

import java.io.Serializable;

public class CommonObjectResult<T> implements Serializable{

  private boolean success;
  private int statusCode;
  private String error;
  private T data;

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }


}
