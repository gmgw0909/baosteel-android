package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-12-27
//*********************************************************

import java.io.Serializable;

public class CameraThumbnail implements Serializable{

  private String id;
  private String name;
  private String key;
  private String image;

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

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
