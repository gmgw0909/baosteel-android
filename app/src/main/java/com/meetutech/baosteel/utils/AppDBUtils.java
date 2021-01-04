package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-04-26
//*********************************************************

import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.model.MTDataObject;
import com.meetutech.baosteel.model.data.Project;
import com.meetutech.baosteel.model.http.FurnaceVariable;
import java.util.List;

public class AppDBUtils {

  public static void updateFurnaceVariables(List<FurnaceVariable> data){
    if(data==null||data.size()==0){
      return;
    }
    for(FurnaceVariable furnaceVariable:data){
      DBManager.insertCache(CacheKeyConstant.FURNACE_VARIABLES_ID+furnaceVariable.getId(),new MTDataObject(furnaceVariable));
    }
  }

  public static FurnaceVariable getFurnaceVariablesById(String vid){
    return (FurnaceVariable) DBManager.getCache(CacheKeyConstant.FURNACE_VARIABLES_ID+vid);
  }

  public static void updateProjectList(List<Project> data) {
    for(Project p:data){
      DBManager.insertCache(CacheKeyConstant.PROJECT_SIMPLE_INFO_ID+p.getId(),new MTDataObject(p));
    }
  }

  public static Project getProjectSimpleInfo(String pid){
    return (Project) DBManager.getCache(CacheKeyConstant.PROJECT_SIMPLE_INFO_ID+pid);
  }
}
