package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-08-19
//*********************************************************

import com.google.gson.annotations.SerializedName;
import com.meetutech.baosteel.model.MTDataObject;

public class Curve extends MTDataObject{
  private String label;
  private @SerializedName("realtime-x") String realtime_x;
  private @SerializedName("realtime-y") String realtime_y;
  private @SerializedName("snapshot-x") String snapshot_x;
  private @SerializedName("snapshot-y") String snapshot_y;
  private String color;
  private @SerializedName("shot-dot") String shot_dot;
  private @SerializedName("show-fillcolor") String show_fillcolor;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getRealtime_x() {
    return realtime_x;
  }

  public void setRealtime_x(String realtime_x) {
    this.realtime_x = realtime_x;
  }

  public String getRealtime_y() {
    return realtime_y;
  }

  public void setRealtime_y(String realtime_y) {
    this.realtime_y = realtime_y;
  }

  public String getSnapshot_x() {
    return snapshot_x;
  }

  public void setSnapshot_x(String snapshot_x) {
    this.snapshot_x = snapshot_x;
  }

  public String getSnapshot_y() {
    return snapshot_y;
  }

  public void setSnapshot_y(String snapshot_y) {
    this.snapshot_y = snapshot_y;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getShot_dot() {
    return shot_dot;
  }

  public void setShot_dot(String shot_dot) {
    this.shot_dot = shot_dot;
  }

  public String getShow_fillcolor() {
    return show_fillcolor;
  }

  public void setShow_fillcolor(String show_fillcolor) {
    this.show_fillcolor = show_fillcolor;
  }

  @Override public String toString() {
    return "Curve{" + "label='" + label + '\'' + ", realtime_x='" + realtime_x + '\''
        + ", realtime_y='" + realtime_y + '\'' + ", snapshot_x='" + snapshot_x + '\''
        + ", snapshot_y='" + snapshot_y + '\'' + ", color='" + color + '\'' + ", shot_dot='"
        + shot_dot + '\'' + ", show_fillcolor='" + show_fillcolor + '\'' + '}';
  }
}
