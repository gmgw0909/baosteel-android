package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-02
//*********************************************************

import java.io.Serializable;

public class LoginResult implements Serializable{

  private long  ttl;
  private String userId;
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getTtl() {
    return ttl;
  }

  public void setTtl(long ttl) {
    this.ttl = ttl;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
