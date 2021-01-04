package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-05-25
// Description: /Projects/{projectId}/projectInfos=>data=>item
//*********************************************************

import com.meetutech.baosteel.common.HTTPConstant;
import java.io.Serializable;

public class ProjectDetails implements Serializable{

  private String id;
  private String name;
  private String key;
  private String image;
  private Content content;
  private String projectId;

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

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Content getContent() {
    return content;
  }

  public void setContent(Content content) {
    this.content = content;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getImageFullUrl(){
    return HTTPConstant.STATIC_FILES_HOST+getImage();
  }

  public static class Content implements Serializable{
    private String html;

    public String getHtml() {
      return html;
    }

    public void setHtml(String html) {
      this.html = html;
    }

    @Override public String toString() {
      return "Content{" + "html='" + html + '\'' + '}';
    }
  }

  @Override public String toString() {
    return "ProjectDetails{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", key='" + key
        + '\'' + ", image='" + image + '\'' + ", content=" + content + ", projectId='" + projectId
        + '\'' + '}';
  }
}
