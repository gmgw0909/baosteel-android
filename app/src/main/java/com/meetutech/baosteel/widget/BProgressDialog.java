package com.meetutech.baosteel.widget;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.widget
// Author: culm at 2017-04-25
//*********************************************************

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.widget.textview.DotsTextView;

public class BProgressDialog extends ProgressDialog{
  private Context context;
  private ProgressBar iv_spinup;
  private TextView tv_tips;
  private String tips = "加载中 ";
  private DotsTextView dots;
/*
    private Animation animation;
*/


  public BProgressDialog(Context context) {
    super(context, R.style.ProgressDialogStyle);
    this.context = context;
  }

  public BProgressDialog(Context context, String tips) {
    super(context, R.style.ProgressDialogStyle);
    this.context = context;
    this.tips = tips;
  }

  public BProgressDialog(Context context, int theme) {
    super(context, theme);
    this.context = context;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(R.layout.custom_progress_dialog);
    tv_tips = (TextView) super.findViewById(R.id.tv_tips);
    tv_tips.setText(tips);
    iv_spinup = (ProgressBar) super.findViewById(R.id.iv_progress);
  }

  @Override
  public void dismiss() {
    super.dismiss();
  }


  @Override
  public void show() {
    super.show();
  }

  @Override
  public void setMessage(CharSequence message) {
    try {
      tips = message.toString();
      tv_tips = (TextView) super.findViewById(R.id.tv_tips);
      tv_tips.setText(tips);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void showProgressDialog(Context context,String... message) {
    try {
      if (BSApplication.pb_dialog != null && BSApplication.pb_dialog.isShowing()) {
        BSApplication.pb_dialog.dismiss();
        return;
      }

      BSApplication.pb_dialog = message.length!=0&&!TextUtils.isEmpty(message[0])?new BProgressDialog(context,message[0]):new BProgressDialog(context);
      BSApplication.pb_dialog.setCancelable(false);
      BSApplication.pb_dialog.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void dismissProgressDialog() {
    if (BSApplication.pb_dialog != null && BSApplication.pb_dialog.isShowing()) {
      BSApplication.pb_dialog.dismiss();
      return;
    }
  }
}
