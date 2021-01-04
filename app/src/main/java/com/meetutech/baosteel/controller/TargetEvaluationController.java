package com.meetutech.baosteel.controller;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.controller
// Author: culm at 2017-08-14
//*********************************************************

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.body.PostTargetEvaluationBody;
import com.meetutech.baosteel.http.result.CommonObjectResult;
import com.meetutech.baosteel.model.http.TargetEvaluations;
import com.meetutech.baosteel.ui.exp.TargetEvaluationActivity;
import com.meetutech.baosteel.utils.NetworkUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import es.dmoral.toasty.Toasty;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TargetEvaluationController {

  private TargetEvaluationActivity activity;

  public TargetEvaluationController(TargetEvaluationActivity activity) {
    this.activity = activity;
  }

  public void postComment(final TargetEvaluationActivity.CommentUIGroup commentUIGroup, String senderId,
      String targetId, float rating, String text, final Button btn_post) {

    if (!NetworkUtils.isOnline(activity)) {
      Toasty.error(activity, activity.getString(R.string.network_unavailable), Toast.LENGTH_SHORT)
          .show();
      return;
    }

    String validationText=valForm(rating,text);

    if(!TextUtils.isEmpty(validationText)){
      Toasty.error(activity,validationText,Toast.LENGTH_SHORT).show();
      return;
    }

    BProgressDialog.showProgressDialog(activity);

    PostTargetEvaluationBody body=new PostTargetEvaluationBody(rating,text,senderId,targetId);

    new RestClient().getApiService()
        .postTargetEvaluation(targetId, new InfoBody(true).getHttpParams(), body,
            new Callback<CommonObjectResult<TargetEvaluations>>() {
              @Override public void success(CommonObjectResult<TargetEvaluations> res,
                  Response response) {
                BProgressDialog.dismissProgressDialog();
                if(!res.isSuccess()){
                  Toasty.error(activity,activity.getString(R.string.post_info_failed),Toast.LENGTH_SHORT).show();
                  Log.i(TargetEvaluationController.class.getSimpleName(),res.toString());
                }
                Toasty.success(activity,activity.getString(R.string.post_info_success),Toast.LENGTH_SHORT).show();
                commentUIGroup.setEvaluationId(res.getData().getId());
                btn_post.setVisibility(View.GONE);
              }

              @Override public void failure(RetrofitError error) {
                BProgressDialog.dismissProgressDialog();
                Toasty.error(activity,activity.getString(R.string.post_info_failed),Toast.LENGTH_SHORT).show();
                Log.i(TargetEvaluationController.class.getSimpleName(),error.toString());
              }
            });
  }

  private String valForm(float rating, String text) {

    if(rating<=0){
      return activity.getString(R.string.error_rating_null);
    }
    if(TextUtils.isEmpty(text)){
      return activity.getString(R.string.error_description_null);
    }

    return null;
  }

  public void putComment(String evaluationId,String senderId,String targetId, float rating, String text,final Button btn_post) {

    if (!NetworkUtils.isOnline(activity)) {
      Toasty.error(activity, activity.getString(R.string.network_unavailable), Toast.LENGTH_SHORT)
          .show();
      return;
    }

    BProgressDialog.showProgressDialog(activity);

    PostTargetEvaluationBody body=new PostTargetEvaluationBody(rating,text,senderId,targetId);

    new RestClient().getApiService()
        .putTargetEvaluation(targetId,evaluationId, new InfoBody(true).getHttpParams(), body,
            new Callback<CommonObjectResult<Object>>() {
              @Override public void success(CommonObjectResult<Object> objectCommonObjectResult,
                  Response response) {
                BProgressDialog.dismissProgressDialog();
                if(!objectCommonObjectResult.isSuccess()){
                  Toasty.error(activity,activity.getString(R.string.post_info_failed),Toast.LENGTH_SHORT).show();
                  Log.i(TargetEvaluationController.class.getSimpleName(),objectCommonObjectResult.toString());
                }
                Toasty.success(activity,activity.getString(R.string.post_info_success),Toast.LENGTH_SHORT).show();
                btn_post.setVisibility(View.GONE);
              }

              @Override public void failure(RetrofitError error) {
                BProgressDialog.dismissProgressDialog();
                Toasty.error(activity,activity.getString(R.string.post_info_failed),Toast.LENGTH_SHORT).show();
                Log.i(TargetEvaluationController.class.getSimpleName(),error.toString());
              }
            });


  }
}
