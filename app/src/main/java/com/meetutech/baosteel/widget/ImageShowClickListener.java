package com.meetutech.baosteel.widget;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.widget
// Author: culm at 2017-08-09
//*********************************************************

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.meetutech.baosteel.ui.base.BaseActivity;

public class ImageShowClickListener implements View.OnClickListener {

  private String source_url;
  private BaseActivity activity;

  public ImageShowClickListener(String source_url, BaseActivity activity) {
    this.source_url = source_url;
    this.activity = activity;
  }

  @Override public void onClick(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setDataAndType(Uri.parse(source_url), "image/*");
    activity.startActivity(intent);
  }
}
