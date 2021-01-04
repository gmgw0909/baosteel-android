package com.meetutech.baosteel.ui;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui
// Author: culm at 2017-04-26
//*********************************************************

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.model.MTDataObject;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.DBManager;
import com.squareup.picasso.Picasso;

public class SplashActivity extends BaseActivity {

  //UI Anotation
  @BindView(R.id.iv_bg) ImageView iv_bg;

  private static String SPLASH_BG_URL = "";

  @Override public void onCreate(Bundle savedInstanceState) {
    Toast.makeText(this, "url:" + SPLASH_BG_URL, Toast.LENGTH_SHORT).show();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    initViews();
  }

  private void initViews() {
    DBManager.insertCache(CacheKeyConstant.APP_SPLASH_IMAGE,
        new MTDataObject("http://culm.me/content/images/2017/04/login_bg.png"));
    SPLASH_BG_URL = (String) DBManager.getCache(CacheKeyConstant.APP_SPLASH_IMAGE);

    Picasso.with(this).load(SPLASH_BG_URL).fit().into(iv_bg);
  }
}
