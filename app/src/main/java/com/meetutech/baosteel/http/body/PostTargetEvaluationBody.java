package com.meetutech.baosteel.http.body;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.body
// Author: culm at 2017-08-14
//*********************************************************

import java.io.Serializable;

public class PostTargetEvaluationBody implements Serializable{

  private float rating;
  private String description;
  private String senderId;
  private String targetId;

  public PostTargetEvaluationBody(float rating, String description, String senderId,
      String targetId) {
    this.rating = rating;
    this.description = description;
    this.senderId = senderId;
    this.targetId = targetId;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public String getTargetId() {
    return targetId;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }
}
