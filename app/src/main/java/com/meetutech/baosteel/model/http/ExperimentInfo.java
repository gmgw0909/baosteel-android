package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-05-25
//*********************************************************

import android.content.Context;
import android.content.res.Resources;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.model.MTDataObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExperimentInfo extends MTDataObject {
  private String id;
  private String name;
  private String serialNumber;
  private String description;
  private String status;
  private Date startTime;
  private int expectedLength;
  private Date endTime;
  private List<String> targetIds;
  private List<String> keyVariableIds;
  private List<KeyVariable> keyVariables;
  private PowerGroup powerGroup;
  private boolean submitApproval;
  private String projectId;
  private String furanceId;

  private Furnace furnace;
  private WorkStat workstat;
  private List<Approval> approvals;
  private List<Nozzle> nozzles;
  private List<Target> targets;

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

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public int getExpectedLength() {
    return expectedLength;
  }

  public void setExpectedLength(int expectedLength) {
    this.expectedLength = expectedLength;
  }

  public List<String> getTargetIds() {
    return targetIds;
  }

  public void setTargetIds(List<String> targetIds) {
    this.targetIds = targetIds;
  }

  public PowerGroup getPowerGroup() {
    return powerGroup;
  }

  public void setPowerGroup(PowerGroup powerGroup) {
    this.powerGroup = powerGroup;
  }

  public boolean isSubmitApproval() {
    return submitApproval;
  }

  public void setSubmitApproval(boolean submitApproval) {
    this.submitApproval = submitApproval;
  }

  public Furnace getFurnace() {
    return furnace;
  }

  public void setFurnace(Furnace furnace) {
    this.furnace = furnace;
  }

  public List<Approval> getApprovals() {
    return approvals;
  }

  public void setApprovals(List<Approval> approvals) {
    this.approvals = approvals;
  }

  public List<Nozzle> getNozzles() {
    return nozzles;
  }

  public void setNozzles(List<Nozzle> nozzles) {
    this.nozzles = nozzles;
  }

  public List<Target> getTargets() {
    return targets;
  }

  public void setTargets(List<Target> targets) {
    this.targets = targets;
  }

  public WorkStat getWorkstat() {
    return workstat;
  }

  public void setWorkstat(WorkStat workstat) {
    this.workstat = workstat;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getFuranceId() {
    return furanceId;
  }

  public void setFuranceId(String furanceId) {
    this.furanceId = furanceId;
  }

  public List<String> getKeyVariableIds() {
    return keyVariableIds;
  }

  public void setKeyVariableIds(List<String> keyVariableIds) {
    this.keyVariableIds = keyVariableIds;
  }

  public List<KeyVariable> getKeyVariables() {
    return keyVariables;
  }

  public void setKeyVariables(List<KeyVariable> keyVariables) {
    this.keyVariables = keyVariables;
  }

  public String getDynamicDuration(Context ctx) {
    String res = "";
    if (getStatus().equals("finished")) {
      res = String.format(ctx.getString(R.string.txt_experiment_list_duration),
          new SimpleDateFormat("yyyy-MM-dd HH:mm").format(getStartTime()),
          new SimpleDateFormat("yyyy-MM-dd HH:mm").format(getEndTime()));
    } else {
      res = String.format(ctx.getString(R.string.txt_experiment_list_duration_no_endtime),
          new SimpleDateFormat("yyyy-MM-dd HH:mm").format(getStartTime()), getExpectedLength());
    }
    return res;
  }
  public String getDynamicStartTime() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(getStartTime());
  }

  public String getDurationLabel(){
    return String.format("%d小时",getExpectedLength());
  }

  public String getDynamicStatus() {
    String res = "未审批";
    switch (getStatus()) {
      case "init":
        res = "未审批";
        break;
      case "approval":
        res = "审批中";
        break;
      case "approved":
        res = "审批完";
        break;
      case "stop":
        res = "未开始";
        break;
      case "running":
        res = "进行中";
        break;
      case "pending":
        res = "暂停";
        break;
      case "finished":
        res = "已结束";
        break;
      default:
        break;
    }
    return res;
  }

  public String getDynamicApproval() {
    String res = "未审批";
    switch (getStatus()) {
      case "init":
        res = "未审批";
        break;
      case "approval":
        res = "审批中";
        break;
      case "approved":
        res = "审批完";
        break;
      case "stop":
        res = "未开始";
        break;
      case "running":
        res = "进行中";
        break;
      case "pending":
        res = "暂停";
        break;
      case "finished":
        res = "已结束";
        break;
      default:
        break;
    }
    return res;
  }

  public int getDynamicStatusColor() {
    int res = Resources.getSystem().getColor(android.R.color.holo_green_light);
    switch (getStatus()) {
      case "init":
        res = Resources.getSystem().getColor(android.R.color.holo_blue_light);
        break;
      case "approval":
        res = Resources.getSystem().getColor(android.R.color.holo_blue_light);
        break;
      case "approved":
        res = Resources.getSystem().getColor(android.R.color.holo_blue_light);
        break;
      case "stop":
        res = Resources.getSystem().getColor(android.R.color.holo_blue_light);
        break;
      case "running":
        res = Resources.getSystem().getColor(android.R.color.holo_green_light);
        break;
      case "pending":
        res = Resources.getSystem().getColor(android.R.color.holo_blue_dark);
        break;
      case "finished":
        res = Resources.getSystem().getColor(android.R.color.darker_gray);;
        break;
      default:
        break;
    }
    return res;
  }

  @Override public String toString() {
    return "ExperimentInfo{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", serialNumber='"
        + serialNumber + '\'' + ", description='" + description + '\'' + ", status='" + status
        + '\'' + ", startTime=" + startTime + ", expectedLength=" + expectedLength + ", endTime="
        + endTime + ", targetIds=" + targetIds + ", powerGroup=" + powerGroup + ", submitApproval="
        + submitApproval + ", projectId='" + projectId + '\'' + ", furanceId='" + furanceId + '\''
        + ", furnace=" + furnace + ", approvals=" + approvals + ", nozzles=" + nozzles
        + ", targets=" + targets + ", workstat=" + workstat + '}';
  }
}

