package com.meetutech.baosteel.ui.exp;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui
// Author: culm at 2017-05-01
//*********************************************************

import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import es.dmoral.toasty.Toasty;

@Route(path="/expert/submitAdvice")
public class ExpertAdviceSubmitActivity extends BaseActivity{

  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.et_advice) EditText et_advice;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_expert_submit);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    initViews();
    loadViews();
  }

  private void initViews() {
    ViewUtils.adjustViewTextSize(tv_header_title,38);
    ViewUtils.adjustViewTextSize(et_advice,30);

  }


  private void loadViews() {
    tv_header_title.setText(getString(R.string.txt_exp_advice_submit_title));
  }

  @OnClick(R.id.btn_header_back)
  public void onClose(){
    finish();
  }

  @OnClick(R.id.btn_header_submit)
  public void onSubmit(){
    BProgressDialog.showProgressDialog(this);
    handler.postDelayed(runnable,1500);
  }

  Handler handler=new Handler();
  Runnable runnable=new Runnable() {
    @Override public void run() {
      runOnUiThread(new Runnable() {
        @Override public void run() {
          BProgressDialog.dismissProgressDialog();
          Toasty.success(ExpertAdviceSubmitActivity.this,"提交成功！", Toast.LENGTH_SHORT,true).show();
          finish();
        }
      });
    }
  };

}
