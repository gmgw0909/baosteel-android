package com.meetutech.baosteel.http.body;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.body
// Author: culm at 2017-08-24
//*********************************************************

import com.meetutech.baosteel.utils.UserUtils;
import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

public class NotificationListBody implements Serializable {

  private static final String FMT_FILTER =
      "{\"limit\": 50,\"order\": \"createdAt DESC\",\"fields\": {\"id\": true,\"title\": true,\"text\": true,\"createdAt\": true},\"where\": {\"and\": [{\"type\": \"android\"},{\"title\": {\"nlike\": \"%%新信息\"}},{\"success\": true},{\"devices\": {\"ilike\": \"%%%s%%\"}}]}}";

  private String deviceToken;

  public NotificationListBody(String deviceToken) {
    this.deviceToken = deviceToken;
  }

  public SortedMap<String, String> getHttpParams() {
    SortedMap<String, String> params = new TreeMap<>();

    params.put("access_token", UserUtils.updateUserToken());
    params.put("filter",String.format(FMT_FILTER,deviceToken));

    return params;
  }
}
