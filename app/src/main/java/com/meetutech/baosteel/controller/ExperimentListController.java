package com.meetutech.baosteel.controller;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.controller
// Author: culm at 2017-08-17
//*********************************************************

import android.content.Context;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.ExpListInfoBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.model.DBUtils;
import com.meetutech.baosteel.model.http.ExperimentInfo;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ExperimentListController {

  public static void updateExperimentList(final Context ctx, String projectID) {
    new RestClient().getApiService()
        .getProjectExperiment(projectID, new ExpListInfoBody(true).getHttpParams(),
            new Callback<CommonListResult<ExperimentInfo>>() {
              @Override
              public void success(CommonListResult<ExperimentInfo> res, Response response) {
                //更新缓存
                if(!res.isSuccess()){
                  return;
                }
                DBUtils.updateExperimentList(res.getData());
              }

              @Override public void failure(RetrofitError error) {
                error.printStackTrace();
              }
            });
  }


}
