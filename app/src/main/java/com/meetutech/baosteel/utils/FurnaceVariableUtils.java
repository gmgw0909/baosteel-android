package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-11-17
//*********************************************************

import com.meetutech.baosteel.model.http.FurnaceVariable;

public class FurnaceVariableUtils {

  public static String getFurnaceVariableName(String vid){
    String varName=vid;

    FurnaceVariable furnaceVariable= AppDBUtils.getFurnaceVariablesById(vid);

    if(furnaceVariable!=null){
      varName=furnaceVariable.getName();
    }

    return varName;
  }

}
