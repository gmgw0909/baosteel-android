package com.meetutech.baosteel.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.utils.DBManager;
import com.meetutech.baosteel.utils.SystemBarTintManager;
import com.meetutech.baosteel.utils.ViewUtils;
import com.umeng.analytics.MobclickAgent;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.base
// Author: culm at 2017-04-25
//*********************************************************

public class BaseActivity extends FragmentActivity {

  protected View mainView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DBManager.init(this);
    setStatusColor(R.color.transparent);
    if (BSApplication.getDisplayMetrics() == null) {
      BSApplication.setDisplayMetrics(ViewUtils.getScreenResolution(this));
    }
    try {
      BSApplication.setDisplayMetrics(ViewUtils.getScreenResolution(this));
      if (!BSApplication.managersInitialized) {
        DBManager.init(getApplicationContext());
        BSApplication.setDisplayMetrics(ViewUtils.getScreenResolution(this));
        BSApplication.managersInitialized = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setStatusColor(int color) {
    SystemBarTintManager tintManager = new SystemBarTintManager(this);
    // 激活状态栏设置
    tintManager.setStatusBarTintEnabled(true);
    tintManager.setStatusBarTintColor(getResources().getColor(color));
  }

  @Override public void setContentView(int layoutResID) {
    mainView = getLayoutInflater().from(this).inflate(layoutResID, null);
    setContentView(mainView);
    //mainView.setFitsSystemWindows(true);
  }

  @Override protected void onResume() {
    DBManager.init(this);
    BSApplication.setDisplayMetrics(ViewUtils.getScreenResolution(this));
    super.onResume();
    MobclickAgent.onResume(this);
  }

  @Override protected void onStop() {
    DBManager.destroy();
    super.onStop();
    MobclickAgent.onPause(this);
  }

  private AlertDialog progressDialog;

  public TextView showProgressDialog() {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setCancelable(false);
    View view = View.inflate(this, R.layout.dialog_loading, null);
    builder.setView(view);
    ProgressBar pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
    TextView tv_hint = (TextView) view.findViewById(R.id.tv_hint);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      pb_loading.setIndeterminateTintList(ContextCompat.getColorStateList(this, R.color.dialog_pro_color));
    }
    tv_hint.setText("视频编译中");
    progressDialog = builder.create();
    progressDialog.show();

    return tv_hint;
  }

  public void closeProgressDialog() {
    try {
      if (progressDialog != null) {
        progressDialog.dismiss();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
