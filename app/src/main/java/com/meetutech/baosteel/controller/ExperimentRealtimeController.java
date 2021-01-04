package com.meetutech.baosteel.controller;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.controller
// Author: culm at 2017-08-18
//*********************************************************

import android.widget.Toast;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.body.TopDataFilterIdBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.http.result.VariableData;
import com.meetutech.baosteel.ui.exp.ExperimentRealtimeDataActivity;
import com.meetutech.baosteel.widget.BProgressDialog;
import es.dmoral.toasty.Toasty;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ExperimentRealtimeController {

  private ExperimentRealtimeDataActivity activity;
  public static boolean isLoopFetch;
  private Thread topThread;
  private long loopDelay = 1000;

  public ExperimentRealtimeController(ExperimentRealtimeDataActivity activity) {
    this.activity = activity;
  }

  public void startGetAllTopData(String experimentId, final boolean isQuiet) {

    if (!isQuiet) BProgressDialog.showProgressDialog(activity);

    new RestClient().getApiService()
        .getAllTopData(experimentId, new InfoBody(true).getHttpParams(),
            new Callback<CommonListResult<VariableData>>() {
              @Override public void success(CommonListResult<VariableData> res, Response response) {
                BProgressDialog.dismissProgressDialog();
                if (!res.isSuccess()&&!isQuiet) {
                  Toasty.error(activity, activity.getString(R.string.load_error),
                      Toast.LENGTH_SHORT).show();
                  return;
                }

                activity.updateAllUIData(res.getData());
              }

              @Override public void failure(RetrofitError error) {
                BProgressDialog.dismissProgressDialog();
              }
            });
  }

  public void loopGetTopData(final String experimentId) {
    isLoopFetch = true;
    topThread = new Thread() {
      @Override public void run() {
        super.run();

        while (isLoopFetch) {
          try {
            Thread.sleep(loopDelay);
            startGetAllTopData(experimentId,true);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    };
    topThread.start();
  }

  public void startLoopGetData(String experimentId, String variableId) {

    new RestClient().getApiService()
        .getTopDataById(experimentId, new TopDataFilterIdBody(true, variableId).getHttpParams(),
            new Callback<CommonListResult<VariableData>>() {
              @Override
              public void success(CommonListResult<VariableData> variableDataCommonListResult,
                  Response response) {
              }

              @Override public void failure(RetrofitError error) {

              }
            });
  }
}
