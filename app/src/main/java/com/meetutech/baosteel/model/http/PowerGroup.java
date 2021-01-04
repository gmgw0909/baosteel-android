package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-08-12
//*********************************************************

import com.meetutech.baosteel.model.MTDataObject;
import java.util.List;

public class PowerGroup extends MTDataObject{

  private String id;
  private String name;
  private List<PowerType> types;

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

  public List<PowerType> getTypes() {
    return types;
  }

  public void setTypes(List<PowerType> types) {
    this.types = types;
  }
}
