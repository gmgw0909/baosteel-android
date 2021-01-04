package com.meetutech.baosteel.http.result;

import java.io.Serializable;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-11-24
//*********************************************************

public class ExperimentCamera implements Serializable {

  private String id;
  private String name;
  private String ip;

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

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  @Override public String toString() {
    return "ExperimentCamera{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", ip='" + ip
        + '\'' + '}';
  }
}
