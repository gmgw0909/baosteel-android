package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-08-12
//*********************************************************

import com.meetutech.baosteel.model.MTDataObject;
import java.util.List;

class BaseChartTemplate extends MTDataObject{

  private String id;
  private String name;
  private String type;
  private String description;
  private List<String> chartDefineIds;

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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getChartDefineIds() {
    return chartDefineIds;
  }

  public void setChartDefineIds(List<String> chartDefineIds) {
    this.chartDefineIds = chartDefineIds;
  }
}
