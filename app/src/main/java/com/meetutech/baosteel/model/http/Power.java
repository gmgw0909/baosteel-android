package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-08-12
//*********************************************************

import com.meetutech.baosteel.model.MTDataObject;

public class Power extends MTDataObject{

  private String id;
  private String name;
  private String key;
  private boolean _selected;

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

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public boolean is_selected() {
    return _selected;
  }

  public void set_selected(boolean _selected) {
    this._selected = _selected;
  }
}
