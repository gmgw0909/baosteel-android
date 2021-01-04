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

public class ListFilterBody implements Serializable {

  private boolean isAuth;

  public ListFilterBody(boolean isAuth) {
    this.isAuth = isAuth;
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

    return params;
  }
}
