package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-08-12
//*********************************************************

import java.io.Serializable;
import java.util.List;

public class Condition implements Serializable{
  private String op;
  private String name;
  private String vid;
  private List<String> value;

  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
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

  public List<String> getValue() {
    return value;
  }

  public void setValue(List<String> value) {
    this.value = value;
  }

  public String returnWorkConditon(){

    String res="";

    switch (op){
      case "value-in-range":
        res=String.format("%s范围在%s-%s之间",name,value.get(0),value.get(1));
        break;
      case "percentage-range":
        res=String.format("%s与预设值（%s）误差不超过%s%%",name,value.get(0),value.get(1));
        break;
      case "value-in-percentage-range":
        res=String.format("%s范围在%s%%-%s%%之间",name,value.get(0),value.get(1));
        break;
      case "diff-in-minute":
        res=String.format("%s一分钟内变化不超过%s",name,value.get(0));
        break;
    }

    return res;

  }


}
