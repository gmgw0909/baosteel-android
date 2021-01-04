package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-05-15
//*********************************************************

import com.meetutech.baosteel.common.HTTPConstant;
import java.io.Serializable;

public class Infos<T> implements Serializable{

  private String id;
  private String name;
  private String key;
  private T content;
  private String image;
  private boolean enabled;

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

  public T getContent() {
    return content;
  }

  public void setContent(T content) {
    this.content = content;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public static class Html implements Serializable {
    private String html;

    public String getHtml() {
      return html;
    }

    public void setHtml(String html) {
      this.html = html;
    }
  }

  public String getImageFullUrl(){
    return HTTPConstant.STATIC_FILES_HOST+getImage();
  }

}
