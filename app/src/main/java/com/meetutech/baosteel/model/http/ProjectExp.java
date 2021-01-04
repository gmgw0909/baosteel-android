package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-05-01
//*********************************************************

import java.io.Serializable;

public class ProjectExp implements Serializable{

  private String id;
  private String name;
  private String subDesc;
  private String state;
  private String duration;

  public ProjectExp(){}

  public ProjectExp(String id, String name, String subDesc, String state, String duration) {
    this.id = id;
    this.name = name;
    this.subDesc = subDesc;
    this.state = state;
    this.duration = duration;
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

  public String getSubDesc() {
    return subDesc;
  }

  public void setSubDesc(String subDesc) {
    this.subDesc = subDesc;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }
}
