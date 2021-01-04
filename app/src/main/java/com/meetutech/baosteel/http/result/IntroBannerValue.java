package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-02
//*********************************************************

import java.io.Serializable;
import java.util.List;

public class IntroBannerValue implements Serializable{

  private List<String> bannerkeys;

  public List<String> getBannerkeys() {
    return bannerkeys;
  }

  public void setBannerkeys(List<String> bannerkeys) {
    this.bannerkeys = bannerkeys;
  }
}
