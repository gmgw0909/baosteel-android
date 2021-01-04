package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-05-25
//*********************************************************

import com.meetutech.baosteel.common.HTTPConstant;
import com.meetutech.baosteel.model.MTDataObject;
import com.meetutech.baosteel.model.UserInfo;
import com.meetutech.chatkit.commons.models.IMessage;
import com.meetutech.chatkit.commons.models.IUser;
import com.meetutech.chatkit.commons.models.MessageContentType;
import java.util.Date;

public class MessageObject extends MTDataObject implements  IMessage,MessageContentType.Image,MessageContentType.Audio,MessageContentType.Video {

  public static final String TYPE_TEXT="text";
  public static final String TYPE_AUDIO="audio";
  public static final String TYPE_IMAGE="image";
  public static final String TYPE_VIDEO="video";

  private String id;
  private String type;
  private String message;
  private String experimentId;
  private String senderId="";
  private UserInfo sender;
  private Date createdAt;
  private Date updatedAt;

  public String getId() {
    return id;
  }

  @Override public String getText() {
    return getMessage();
  }

  @Override public IUser getUser() {
    return sender;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getExperimentId() {
    return experimentId;
  }

  public void setExperimentId(String experimentId) {
    this.experimentId = experimentId;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public UserInfo getSender() {
    return sender;
  }

  public void setSender(UserInfo sender) {
    this.sender = sender;
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

  @Override public String getImageUrl() {
    return getType().equals("image")? HTTPConstant.getStaticFileFullPath(getMessage()):null;
  }

  @Override public String getAudioUrl() {
    return getType().equals("audio")? HTTPConstant.getStaticFileFullPath(getMessage()):null;
  }

  @Override public String getVideoUrl() {
    return getType().equals("video")? HTTPConstant.getStaticFileFullPath(getMessage()):null;
  }
}
