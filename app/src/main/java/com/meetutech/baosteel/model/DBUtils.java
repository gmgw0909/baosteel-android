package com.meetutech.baosteel.model;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model
// Author: culm at 2017-08-13
//*********************************************************

import android.util.Log;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.model.http.ExperimentInfo;
import com.meetutech.baosteel.utils.DBManager;
import java.util.List;

public class DBUtils {

  public static void updateExperimentList(List<ExperimentInfo> data) {
    for (ExperimentInfo info : data) {
      Log.i("culm","插入数据："+info.toString());
      DBManager.insertCache(CacheKeyConstant.EXPERIMENT_CACHE_ID + info.getId(),
          new MTDataObject(info));
    }

  }

  public static ExperimentInfo getExperiment(String id) {
    Log.i("culm","find experiment id :"+id);
    return (ExperimentInfo) DBManager.getCache(CacheKeyConstant.EXPERIMENT_CACHE_ID + id);
  }
}
