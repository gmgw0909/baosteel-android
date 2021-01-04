package com.meetutech.baosteel.model;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model
// Author: culm at 2017-04-26
//*********************************************************

import java.io.Serializable;
import org.json.JSONObject;

public class MTDataObject extends JSONObject implements Serializable {
  private Object _content;

  public MTDataObject(Object _content) {
    this._content = _content;
  }

  public MTDataObject() {
  }

  public Object get_content() {
    return _content;
  }

}
