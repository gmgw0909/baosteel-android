package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-08-12
//*********************************************************

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Target implements Serializable{
  private String id;
  private String name;
  private TargetContent content;
  private String description;
  private boolean showRealtime;
  private boolean showAnalyse;
  private BaseChartTemplate baseTemplate;
  private Date createdAt;
  private Date updatedAt;
  private boolean enabled;
  private List<TargetEvaluations> evaluations;
  private boolean _selected;
  private String analysisAliasX;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAnalysisAliasX() {
    return analysisAliasX;
  }

  public void setAnalysisAliasX(String analysisAliasX) {
    this.analysisAliasX = analysisAliasX;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean is_selected() {
    return _selected;
  }

  public void set_selected(boolean _selected) {
    this._selected = _selected;
  }

  public TargetContent getContent() {
    return content;
  }

  public void setContent(TargetContent content) {
    this.content = content;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isShowRealtime() {
    return showRealtime;
  }

  public void setShowRealtime(boolean showRealtime) {
    this.showRealtime = showRealtime;
  }

  public boolean isShowAnalyse() {
    return showAnalyse;
  }

  public void setShowAnalyse(boolean showAnalyse) {
    this.showAnalyse = showAnalyse;
  }

  public BaseChartTemplate getBaseTemplate() {
    return baseTemplate;
  }

  public void setBaseTemplate(BaseChartTemplate baseTemplate) {
    this.baseTemplate = baseTemplate;
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

  public List<TargetEvaluations> getEvaluations() {
    return evaluations;
  }

  public void setEvaluations(List<TargetEvaluations> evaluations) {
    this.evaluations = evaluations;
  }

  @Override public String toString() {
    return "Target{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", content=" + content
        + ", description='" + description + '\'' + ", showRealtime=" + showRealtime
        + ", showAnalyse=" + showAnalyse + ", baseTemplate=" + baseTemplate + ", createdAt="
        + createdAt + ", updatedAt=" + updatedAt + ", enabled=" + enabled + ", evaluations="
        + evaluations + '}';
  }
}
