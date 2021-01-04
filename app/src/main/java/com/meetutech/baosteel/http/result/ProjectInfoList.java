package com.meetutech.baosteel.http.result;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.result
// Author: culm at 2017-05-02
//*********************************************************

import com.meetutech.baosteel.model.http.ProjectInfo;
import java.io.Serializable;

public class ProjectInfoList implements Serializable{

  private ProjectInfo[] proj_list;

  public ProjectInfo[] getProj_list() {
    return proj_list;
  }

  public void setProj_list(ProjectInfo[] proj_list) {
    this.proj_list = proj_list;
  }
}
