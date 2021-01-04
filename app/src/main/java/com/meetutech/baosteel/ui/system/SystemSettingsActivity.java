package com.meetutech.baosteel.ui.system;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.system
// Author: culm at 2017-05-01
//*********************************************************

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kyleduo.switchbutton.SwitchButton;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.UserUtils;
import com.meetutech.baosteel.utils.ViewUtils;

@Route(path="/system/setting")
public class SystemSettingsActivity extends BaseActivity
    implements CompoundButton.OnCheckedChangeListener {
  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.tv_tips_font_small) TextView tv_tips_font_small;
  public @BindView(R.id.tv_tips_font_medium) TextView tv_tips_font_medium;
  public @BindView(R.id.tv_tips_font_large) TextView tv_tips_font_large;
  public @BindView(R.id.tv_tips_version) TextView tv_tips_version;
  public @BindView(R.id.rl_smallfont) View rl_smallfont;
  public @BindView(R.id.rl_mediumfont) View rl_mediumfont;
  public @BindView(R.id.rl_largefont) View rl_largefont;
  public @BindView(R.id.sb_code) SwitchButton sb_code;

  public @BindView(R.id.tv_tips_openpush) TextView tv_tips_openpush;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_system_setting);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    initViews();
    loadViews();
  }

  private void loadViews() {
    tv_header_title.setText(getString(R.string.txt_setting_title));
    sb_code.setOnCheckedChangeListener(this);
    sb_code.setChecked(UserUtils.getPushSwitch());
    loadFontScaleView(UserUtils.getFontScale());
  }

  private void loadFontScaleView(float scale){

    if(scale<=ViewUtils.SMALL_FONT_SCALE){
      onSmallFontClick(null);
    } else if(scale>=ViewUtils.LARGE_FONT_SCALE){
      onLargeFontClick(null);
    } else {
      onMediumFontClick(null);
    }

  }

  private void initViews() {
    ViewUtils.adjustViewTextSize(tv_header_title,38);
    ViewUtils.adjustViewTextSize(tv_tips_openpush,38);
    ViewUtils.adjustViewTextSize(tv_tips_font_small,38);
    ViewUtils.adjustViewTextSize(tv_tips_font_medium,38);
    ViewUtils.adjustViewTextSize(tv_tips_font_large,38);
    ViewUtils.adjustViewTextSize(tv_tips_version,28);
    ViewUtils.adjustViewTextSize((TextView) findViewById(R.id.tv_tips_service),26);
    ViewUtils.adjustViewTextSize((TextView) findViewById(R.id.tv_tips_and),26);
    ViewUtils.adjustViewTextSize((TextView) findViewById(R.id.tv_tips_privacy),26);
    ViewUtils.adjustViewTextSize((TextView) findViewById(R.id.btn_logout),34);

  }

  @OnClick(R.id.btn_header_close)
  public void onClose(){
    finish();
  }
  @OnClick(R.id.rl_smallfont)
  public void onSmallFontClick(View v){
    findViewById(R.id.icon_check_small).setVisibility(View.VISIBLE);
    findViewById(R.id.icon_check_medium).setVisibility(View.INVISIBLE);
    findViewById(R.id.icon_check_large).setVisibility(View.INVISIBLE);

    UserUtils.setFontScale(ViewUtils.SMALL_FONT_SCALE);
    initViews();
  }
  @OnClick(R.id.rl_mediumfont)
  public void onMediumFontClick(View v){
    findViewById(R.id.icon_check_small).setVisibility(View.INVISIBLE);
    findViewById(R.id.icon_check_medium).setVisibility(View.VISIBLE);
    findViewById(R.id.icon_check_large).setVisibility(View.INVISIBLE);
    UserUtils.setFontScale(ViewUtils.NORMAL_FONT_SCALE);
    initViews();
  }
  @OnClick(R.id.rl_largefont)
  public void onLargeFontClick(View v){
    findViewById(R.id.icon_check_small).setVisibility(View.INVISIBLE);
    findViewById(R.id.icon_check_medium).setVisibility(View.INVISIBLE);
    findViewById(R.id.icon_check_large).setVisibility(View.VISIBLE);
    UserUtils.setFontScale(ViewUtils.LARGE_FONT_SCALE);
    initViews();
  }

  @OnClick(R.id.btn_logout)
  public void onLogout(View v){
    UserUtils.logoutTimeout(this);
  }



  @Override public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    UserUtils.setPushSwitch(this,b);
  }
}
