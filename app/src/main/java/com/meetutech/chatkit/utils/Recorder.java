package com.meetutech.chatkit.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.chatkit.utils
// Author: culm at 2017-08-09
//*********************************************************

public class Recorder {
  float time;
  String filePathString;

  public Recorder(float time, String filePathString) {
    super();
    this.time = time;
    this.filePathString = filePathString;
  }

  public float getTime() {
    return time;
  }

  public void setTime(float time) {
    this.time = time;
  }

  public String getFilePathString() {
    return filePathString;
  }

  public void setFilePathString(String filePathString) {
    this.filePathString = filePathString;
  }

}