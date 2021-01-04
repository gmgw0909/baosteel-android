package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-11-20
//*********************************************************

import java.io.Serializable;
import java.util.List;

public class WorkCondition implements Serializable{
  private String op;
  private List<String> value;
  private List<String> unit;
  private String name;
  private String vid;

  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
  }

  public List<String> getValue() {
    return value;
  }

  public void setValue(List<String> value) {
    this.value = value;
  }

  public List<String> getUnit() {
    return unit;
  }

  public void setUnit(List<String> unit) {
    this.unit = unit;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVid() {
    return vid;
  }

  public void setVid(String vid) {
    this.vid = vid;
  }

  @Override public String toString() {
    return "WorkCondition{" + "op='" + op + '\'' + ", value=" + value + ", unit=" + unit
        + ", name='" + name + '\'' + ", vid='" + vid + '\'' + '}';
  }
}
