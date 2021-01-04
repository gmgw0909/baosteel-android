package com.meetutech.baosteel.model.data;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.data
// Author: culm at 2017-05-15
//*********************************************************

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable{

  private String id;
  private String name;
  private String description;
  private String status;
  private boolean longTerm;
  private String createdAt;
  private String updatedAt;
  private boolean enabled;
  private String reportFileUrl;
  private String reportFileName;
  private String reportFileSize;
  private Date reportValidPeriod;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isLongTerm() {
    return longTerm;
  }

  public void setLongTerm(boolean longTerm) {
    this.longTerm = longTerm;
  }

  @Override public String toString() {
    return "Project{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", description='"
        + description + '\'' + ", status='" + status + '\'' + ", createdAt='" + createdAt + '\''
        + ", updatedAt='" + updatedAt + '\'' + ", enabled=" + enabled + '}';
  }

  public String getReportFileUrl() {
    return reportFileUrl;
  }

  public void setReportFileUrl(String reportFileUrl) {
    this.reportFileUrl = reportFileUrl;
  }

  public String getReportFileName() {
    return reportFileName;
  }

  public void setReportFileName(String reportFileName) {
    this.reportFileName = reportFileName;
  }

  public String getReportFileSize() {
    return reportFileSize;
  }

  public void setReportFileSize(String reportFileSize) {
    this.reportFileSize = reportFileSize;
  }

  public Date getReportValidPeriod() {
    return reportValidPeriod;
  }

  public void setReportValidPeriod(Date reportValidPeriod) {
    this.reportValidPeriod = reportValidPeriod;
  }
}
