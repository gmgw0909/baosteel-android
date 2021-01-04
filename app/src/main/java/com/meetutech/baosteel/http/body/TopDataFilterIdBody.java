package com.meetutech.baosteel.http.body;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.body
// Author: culm at 2017-05-15
//*********************************************************

import com.meetutech.baosteel.utils.UserUtils;
import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

public class TopDataFilterIdBody implements Serializable {

  private boolean isAuth;
  private String variableId;
  public static final String FMT_KEY = "{\"where\":{\"varableId\":\"%s\",\"data\":{\"gt\":\"150302007600\"}}}";
  public TopDataFilterIdBody(boolean isAuth) {
    this.isAuth = isAuth;
  }

  public TopDataFilterIdBody(boolean isAuth, String variableId) {
    this.isAuth = isAuth;
    this.variableId = variableId;
  }

  public boolean isAuth() {
    return isAuth;
  }

  public void setAuth(boolean auth) {
    isAuth = auth;
  }

  public SortedMap<String, String> getHttpParams() {
    SortedMap<String, String> params = new TreeMap<>();

    if (isAuth) {
      params.put("access_token", UserUtils.updateUserToken());
    }
    params.put("filter",String.format(FMT_KEY,variableId));

    return params;
  }
}
