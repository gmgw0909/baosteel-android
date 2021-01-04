package com.meetutech.baosteel.ui.exp;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.exp
// Author: culm at 2018-01-26
//*********************************************************

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.ui.base.BaseActivity;
import me.relex.circleindicator.CircleIndicator;

@Route(path = RouterConstant.EXPERIMENT_REALTIME_DATA) public class Experiment312RealtimeDataActivity extends BaseActivity{


  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.tv_header_back) TextView tv_header_back;
  public @BindView(R.id.vp_content) ViewPager vp_content;
  public @BindView(R.id.indicator) CircleIndicator indicator;
  public @BindView(R.id.btn_chat) Button btn_chat;

  public @Autowired(name = "title") String header_title;
  public @Autowired(name = "experimentId") String experimentId;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
}
