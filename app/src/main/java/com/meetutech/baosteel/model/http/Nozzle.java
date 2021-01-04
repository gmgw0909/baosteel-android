package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-08-12
//*********************************************************

import android.text.TextUtils;
import java.io.Serializable;
import java.util.Date;

public class Nozzle implements Serializable{
  private String id;
  private String name;
  private String serialNumber;
  private String type;
  private String parameter;
  private String medium;
  private String pointGunType;
  private String pointGunMedium;
  private String mainGasPipeNozzleOtherDescription;
  private String mainAirPipeNozzleOtherDescription;
  private String description;
  private Date createdAt;
  private Date updatedAt;
  private boolean enabled;

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

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  public String getMedium() {
    return TextUtils.isEmpty(medium)?"空":medium;
  }

  public void setMedium(String medium) {
    this.medium = medium;
  }

  public String getPointGunType() {
    return TextUtils.isEmpty(pointGunType)?"空":pointGunType;
  }

  public void setPointGunType(String pointGunType) {
    this.pointGunType = pointGunType;
  }

  public String getPointGunMedium() {
    return TextUtils.isEmpty(pointGunMedium)?"空":pointGunMedium;
  }

  public void setPointGunMedium(String pointGunMedium) {
    this.pointGunMedium = pointGunMedium;
  }

  public String getMainGasPipeNozzleOtherDescription() {
    return mainGasPipeNozzleOtherDescription;
  }

  public void setMainGasPipeNozzleOtherDescription(String mainGasPipeNozzleOtherDescription) {
    this.mainGasPipeNozzleOtherDescription = mainGasPipeNozzleOtherDescription;
  }

  public String getMainAirPipeNozzleOtherDescription() {
    return mainAirPipeNozzleOtherDescription;
  }

  public void setMainAirPipeNozzleOtherDescription(String mainAirPipeNozzleOtherDescription) {
    this.mainAirPipeNozzleOtherDescription = mainAirPipeNozzleOtherDescription;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
}
