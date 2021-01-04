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

public class MessageFilterBody implements Serializable {

  public static final String SORT_DESC = "DESC";
  public static final String SORT_ASC = "ASC";

  public static final String FMT_KEY = "{\"where\":{\"order\":\"createdAt %s\"},\"include\":{\"relation\":\"sender\",\"scope\":{\"fields\":{\"id\":true,\"name\":true}}}}";

  private String sortType;

  public MessageFilterBody(String sortType) {
    this.sortType = sortType;
  }

  public String getSortType() {
    return sortType;
  }

  public void setSortType(String sortType) {
    this.sortType = sortType;
  }

  public SortedMap<String, String> getHttpParams() {
    SortedMap<String, String> params = new TreeMap<>();

    params.put("filter", String.format(FMT_KEY, getSortType()));

    params.put("access_token", UserUtils.updateUserToken());

    return params;
  }
}
