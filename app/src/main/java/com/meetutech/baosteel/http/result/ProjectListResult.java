package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-02
//*********************************************************

import java.io.Serializable;

public class ProjectListResult implements Serializable {
  private boolean success;
  private ProjectInfoResult[] data;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public ProjectInfoResult[] getData() {
    return data;
  }

  public void setData(ProjectInfoResult[] data) {
    this.data = data;
  }
}
