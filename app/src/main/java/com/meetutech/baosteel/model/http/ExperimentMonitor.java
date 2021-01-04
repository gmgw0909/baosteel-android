package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-05-02
//*********************************************************

import java.io.Serializable;

public class ExperimentMonitor implements Serializable {

  private String id;
  private String name;
  private String state;
  private String background_url;

  public ExperimentMonitor() {
  }

  public ExperimentMonitor(String id, String name, String state, String background_url) {
    this.id = id;
    this.name = name;
    this.state = state;
    this.background_url = background_url;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getBackground_url() {
    return background_url;
  }

  public void setBackground_url(String background_url) {
    this.background_url = background_url;
  }
}
