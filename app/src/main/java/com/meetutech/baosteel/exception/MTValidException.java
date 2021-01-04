package com.meetutech.baosteel.exception;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.exception
// Author: culm at 2017-04-27
//*********************************************************

public class MTValidException extends CommonException{

  protected String message;
  protected String action;
  public MTValidException(String message,String ...action) {
    this.message = message;
    if(action!=null&&action.length>0){
      this.action=action[0];
    }
  }

  @Override public String getMessage() {
    return message;
  }

  public String getAction() {
    return action;
  }
}
