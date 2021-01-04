package com.meetutech.baosteel.http.body;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.body
// Author: culm at 2017-08-22
//*********************************************************

import java.io.Serializable;

public class ExperimentApprovalBody implements Serializable{

  public static final String STATUS_AGREE="agree";
  public static final String STATUS_DISAGREE="disagree";

  private String status;


  public ExperimentApprovalBody(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
