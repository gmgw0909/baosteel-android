package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-11-17
//*********************************************************

import com.meetutech.baosteel.model.MTDataObject;
import java.util.Date;
import java.util.HashMap;

public class FurnaceVariable extends MTDataObject {

  private String id;
  private String name;
  private String address;
  private String path;
  private String period;
  private String type;
  private String location;
  private String codeName;
  private String furnaceId;
  private Date createdAt;
  private Date updatedAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getCodeName() {
    return codeName;
  }

  public void setCodeName(String codeName) {
    this.codeName = codeName;
  }

  public String getFurnaceId() {
    return furnaceId;
  }

  public void setFurnaceId(String furnaceId) {
    this.furnaceId = furnaceId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public static HashMap<String, String> TYPE2UNIT = new HashMap<>();

  static {
    TYPE2UNIT.put("temperature", "°C");
    TYPE2UNIT.put("flow", "m³/h");
    TYPE2UNIT.put("gas", "ppm");
    TYPE2UNIT.put("pressure", "kPa");
    TYPE2UNIT.put("percent", "%");

  }
}
