package com.meetutech.baosteel.http.body;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.body
// Author: culm at 2017-05-02
//*********************************************************

import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.utils.DBManager;
import com.meetutech.baosteel.utils.UserUtils;
import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

public class SnapshotListInfoBody implements Serializable {

  private boolean isAuth;
  private String FMT_FILTER ="{\"where\":{\"release\":1},\"include\":[{\"relation\":\"workstat\"}]}";
  public SnapshotListInfoBody(boolean isAuth) {
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

    params.put("filter",
        String.format(FMT_FILTER, (String) DBManager.getCache(CacheKeyConstant.AUTH_CURR_USER_ID)));
    return params;
  }
}
