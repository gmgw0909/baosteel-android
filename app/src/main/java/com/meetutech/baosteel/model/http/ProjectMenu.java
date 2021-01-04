package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-04-28
//*********************************************************

import com.meetutech.baosteel.model.data.Project;
import java.io.Serializable;

public class ProjectMenu implements Serializable {

  private String id;
  private String name;
  private String url;
  private String key;
  private String content;
  private Project project;

  public ProjectMenu() {

  }

  public ProjectMenu(String name, String url) {
    this.name = name;
    this.url = url;
  }
  public ProjectMenu(String name, String url,Project project) {
    this.name = name;
    this.url = url;
    this.project=project;
  }

  public ProjectMenu(String name, String url, String key) {
    this.name = name;
    this.url = url;
    this.key = key;
  }
  public ProjectMenu(String name, String url, String key,String content) {
    this.name = name;
    this.url = url;
    this.key = key;
    this.content=content;
  }

  public ProjectMenu(String name, String url, String key,String content,Project project) {
    this.name = name;
    this.url = url;
    this.key = key;
    this.content=content;
    this.project=project;
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  @Override public String toString() {
    return "ProjectMenu{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", url='" + url + '\''
        + ", key='" + key + '\'' + ", content='" + content + '\'' + ", project=" + project + '}';
  }
}
