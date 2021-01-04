package com.meetutech.baosteel.ui.exp;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui
// Author: culm at 2017-05-01
//*********************************************************

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.adapter.ExpAdviceListAdapter;
import com.meetutech.baosteel.model.http.ExpertAdvice;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.dialog.WheelDialogFragment;
import java.util.ArrayList;
import java.util.List;

@Route(path="/expert/advice")
public class ExpertAdviceListActivity extends BaseActivity{

  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.list_advice) ListView list_advice;
  private List<ExpertAdvice> expAdvices;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_expert_advice);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    initViews();
    loadViews();
  }

  private void initViews() {
    ViewUtils.adjustViewTextSize(tv_header_title,38);

  }
  private List<ExpertAdvice> generateExp() {
    List<ExpertAdvice> exps=new ArrayList<>();

    exps.add(new ExpertAdvice("0","总体建议"));
    exps.add(new ExpertAdvice("1","实验1"));
    exps.add(new ExpertAdvice("2","实验2"));
    exps.add(new ExpertAdvice("3","实验3"));
    exps.add(new ExpertAdvice("4","实验4"));
    return exps;
  }

  private String[] generateExpArray() {
    String[] exps=new String[5];

    exps[0]="总体建议";
    exps[1]="实验1";
    exps[2]="实验2";
    exps[3]="实验3";
    exps[4]="实验4";

    return exps;
  }

  private void loadViews() {
    tv_header_title.setText(getString(R.string.txt_exp_advice_title));
    expAdvices=generateExp();
    list_advice.setAdapter(new ExpAdviceListAdapter(this,expAdvices));
  }

  @OnClick(R.id.btn_header_back)
  public void onClose(){
    finish();
  }

  @OnClick(R.id.btn_header_add)
  public void onAddAdvice(){
    final WheelDialogFragment wheelViewDialogFragment = WheelDialogFragment
        .newInstance(generateExpArray(),
            "选择实验",
            "确定", true, false, false);
    wheelViewDialogFragment.setWheelDialogListener(new WheelDialogFragment.OnWheelDialogListener() {
      @Override
      public void onClickLeft(String value) {

      }

      @Override
      public void onClickRight(String value) {
        wheelViewDialogFragment.dismiss();
        ARouter.getInstance().build("/expert/submitAdvice")
            .navigation(ExpertAdviceListActivity.this);
      }

      @Override
      public void onValueChanged(String value) {

      }
    });
    wheelViewDialogFragment.show(getSupportFragmentManager(), "");
  }

}
