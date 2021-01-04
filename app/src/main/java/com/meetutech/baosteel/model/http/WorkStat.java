package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-08-22
//*********************************************************

import java.io.Serializable;
import java.util.List;

public class WorkStat implements Serializable{
  private String id;
  private String name;
  private List<WorkStatObject> conditions;

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

  public List<WorkStatObject> getConditions() {
    return conditions;
  }

  public void setConditions(List<WorkStatObject> conditions) {
    this.conditions = conditions;
  }

}
