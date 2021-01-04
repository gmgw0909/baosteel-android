package com.meetutech.baosteel.http.body;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.body
// Author: culm at 2017-05-02
//*********************************************************

import com.meetutech.baosteel.utils.UserUtils;
import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConfigBody implements Serializable {

  public static final String FMT_KEY = "{\"where\":{\"key\":\"%s\"}}";

  private String key;
  private boolean isAuth;

  public ConfigBody(String key, boolean isAuth) {
    this.key = key;
    this.isAuth = isAuth;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public boolean isAuth() {
    return isAuth;
  }

  public void setAuth(boolean auth) {
    isAuth = auth;
  }

  public SortedMap<String, String> getHttpParams() {
    SortedMap<String, String> params = new TreeMap<>();

    params.put("filter", String.format(FMT_KEY, getKey()));

    if (isAuth) {
      params.put("access_token", UserUtils.updateUserToken());
    }

    return params;
  }
}
