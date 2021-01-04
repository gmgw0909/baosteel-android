package com.meetutech.baosteel.controller;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.controller
// Author: culm at 2017-04-27
//*********************************************************

import android.text.TextUtils;
import android.widget.Toast;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.exception.MTValidException;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.ForgotPwdForm;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.result.CommonObjectResult;
import com.meetutech.baosteel.http.result.LoginResult;
import com.meetutech.baosteel.http.result.UserInfoResult;
import com.meetutech.baosteel.model.MTDataObject;
import com.meetutech.baosteel.model.form.LoginForm;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.DBManager;
import com.meetutech.baosteel.utils.NetworkUtils;
import com.meetutech.baosteel.utils.UserUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import es.dmoral.toasty.Toasty;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginController {

  private BaseActivity activity;

  public LoginController(BaseActivity activity) {
    this.activity = activity;
  }

  public void submitLogin(LoginForm form) {
    BProgressDialog.showProgressDialog(activity);
    new RestClient().getApiService().login(form, new Callback<CommonObjectResult<LoginResult>>() {
      @Override public void success(CommonObjectResult<LoginResult> res, Response response) {
        LoginResult loginResult=res.getData();
        if(!res.isSuccess()){
          BProgressDialog.dismissProgressDialog();
          Toasty.error(activity, TextUtils.isEmpty(res.getError())?activity.getString(R.string.login_error):res.getError(), Toast.LENGTH_SHORT, true).show();
          return;
        }

        UserUtils.bindPushService(activity);

        DBManager.insertCache(CacheKeyConstant.AUTH_KEY,
            new MTDataObject(loginResult.getToken()));
        DBManager.insertCache(CacheKeyConstant.AUTH_IS_LOGIN, new MTDataObject(new Boolean(true)));

        DBManager.insertCache(CacheKeyConstant.AUTH_CURR_USER_ID,new MTDataObject(loginResult.getUserId()));

        Toasty.success(activity, activity.getString(R.string.login_success), Toast.LENGTH_SHORT, true).show();

        UserUtils.initUserConfig();

        new RestClient().getApiService().getUserInfo(loginResult.getUserId(), new InfoBody(true).getHttpParams(),
            new Callback<UserInfoResult>() {
              @Override public void success(UserInfoResult userInfoResult, Response response) {
                BProgressDialog.dismissProgressDialog();
                DBManager.insertCache(CacheKeyConstant.AUTH_INFO, new MTDataObject(userInfoResult.getData()));
                ARouter.getInstance().build("/main/start").navigation(activity);
              }

              @Override public void failure(RetrofitError error) {
                BProgressDialog.dismissProgressDialog();
                ARouter.getInstance().build("/main/start").navigation(activity);
              }
            });


      }

      @Override public void failure(RetrofitError error) {
        BProgressDialog.dismissProgressDialog();
        Toasty.error(activity, activity.getString(R.string.login_error), Toast.LENGTH_SHORT, true).show();
      }
    });
  }

  public boolean checkLoginForm(LoginForm form) throws MTValidException {
    return form.checkForm(activity);
  }

  public void forgotPassword(String phone) {
    if(TextUtils.isEmpty(phone)){
      Toasty.error(activity,activity.getString(R.string.valid_phone),Toast.LENGTH_SHORT).show();
      return;
    }
    if(!NetworkUtils.isOnline(activity)){
      Toasty.error(activity,activity.getString(R.string.network_unavailable),Toast.LENGTH_SHORT).show();
      return;
    }

    BProgressDialog.showProgressDialog(activity);
    new RestClient().getApiService().passwordReset(new ForgotPwdForm(phone),
        new Callback<CommonObjectResult<Object>>() {
          @Override public void success(CommonObjectResult<Object> res,
              Response response) {
            BProgressDialog.dismissProgressDialog();
            if(!res.isSuccess()){
              Toasty.error(activity,TextUtils.isEmpty(res.getError())?activity.getString(R.string.post_info_failed):res.getError(),Toast.LENGTH_SHORT).show();
              return;
            }

            Toasty.success(activity,activity.getString(R.string.post_password_reset_success),Toast.LENGTH_SHORT).show();

          }

          @Override public void failure(RetrofitError error) {
            BProgressDialog.dismissProgressDialog();
            Toasty.error(activity,activity.getString(R.string.post_info_failed),Toast.LENGTH_SHORT).show();
          }
        });
  }
}
