package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-08-17
//*********************************************************

import com.meetutech.baosteel.model.MTDataObject;
import java.util.Date;

public class NozzleVariable extends MTDataObject{
  private String id;
  private String name;
  private String key;
  private String value;
  private String description;
  private String unit;
  private String nozzleId;
  private Date createdAt;
  private Date updatedAt;
  private boolean enabled;
  private String originId;
  private VariableOrigin origin;

  public String generateValue() {


    StringBuffer res=new StringBuffer();

    if(origin!=null){

      String[] originData=origin.getValue().split("/");

      int realValue= (int) Float.parseFloat(value);

      if(originData.length>0&&realValue<originData.length){
        res.append(originData[realValue]);
      }

    } else {
      try {
        res.append(String.format("%.2f", Float.parseFloat(value)));
      } catch (NumberFormatException e){
        res.append(value);
      }
    }
    if(unit!=null){
      res.append(unit);
    }

    return res.toString();
  }

  public static class VariableOrigin extends MTDataObject{
    private String id;
    private String value;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    @Override public String toString() {
      return "VariableOrigin{" + "id='" + id + '\'' + ", value='" + value + '\'' + '}';
    }
  }

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

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getNozzleId() {
    return nozzleId;
  }

  public void setNozzleId(String nozzleId) {
    this.nozzleId = nozzleId;
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

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getOriginId() {
    return originId;
  }

  public void setOriginId(String originId) {
    this.originId = originId;
  }

  public VariableOrigin getOrigin() {
    return origin;
  }

  public void setOrigin(VariableOrigin origin) {
    this.origin = origin;
  }
}
