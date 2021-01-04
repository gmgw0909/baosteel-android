package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-08-18
//*********************************************************

import com.meetutech.baosteel.model.MTDataObject;
import java.util.Date;

public class VariableData extends MTDataObject{

  private String id;
  private long date;
  private String varableId;
  private Float value;
  private String experimentId;
  private Date createdAt;
  private Date updatedAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public String getVarableId() {
    return varableId;
  }

  public void setVarableId(String varableId) {
    this.varableId = varableId;
  }

  public Float getValue() {
    return value;
  }

  public void setValue(Float value) {
    this.value = value;
  }

  public String getExperimentId() {
    return experimentId;
  }

  public void setExperimentId(String experimentId) {
    this.experimentId = experimentId;
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

  @Override public String toString() {
    return "VariableData{" + "id='" + id + '\'' + ", date=" + date + ", varableId='" + varableId
        + '\'' + ", value='" + value + '\'' + ", experimentId='" + experimentId + '\''
        + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
  }
}
