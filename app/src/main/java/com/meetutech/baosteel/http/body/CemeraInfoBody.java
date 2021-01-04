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

public class CemeraInfoBody implements Serializable {

  private boolean isAuth;
  private String cameraId;

  public CemeraInfoBody(boolean isAuth, String cameraId) {
    this.isAuth = isAuth;
    this.cameraId = cameraId;
  }

  public String getCameraId() {
    return cameraId;
  }

  public void setCameraId(String cameraId) {
    this.cameraId = cameraId;
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
    params.put("id",cameraId);

    return params;
  }
}
