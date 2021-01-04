package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-11-20
//*********************************************************

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WorkStatSnapshot implements Serializable{
  private String id;
  private String name;
  private List<WorkCondition> condition;
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

  public List<WorkCondition> getCondition() {
    return condition;
  }

  public void setCondition(List<WorkCondition> condition) {
    this.condition = condition;
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

  @Override public String toString() {
    return "WorkStatSnapshot{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", condition="
        + condition + ", furnaceId='" + furnaceId + '\'' + ", createdAt=" + createdAt
        + ", updatedAt=" + updatedAt + '}';
  }
}
