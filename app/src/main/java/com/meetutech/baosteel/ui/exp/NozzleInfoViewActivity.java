package com.meetutech.baosteel.ui.exp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.model.http.ExperimentInfo;
import com.meetutech.baosteel.model.http.Nozzle;
import com.meetutech.baosteel.model.http.NozzleVariable;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.DBManager;
import com.meetutech.baosteel.utils.NetworkUtils;
import com.meetutech.baosteel.utils.ViewUtils;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.info
// Author: culm at 2017-04-27
//*********************************************************
@Route(path = RouterConstant.NOZZLE_INFO) public class NozzleInfoViewActivity extends BaseActivity {

  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.tv_header_back) TextView tv_header_back;
  public @BindView(R.id.ll_content) LinearLayout ll_content;

  public @Autowired(name = "title") String header_title;
  public @Autowired(name = "experimentId") String experimentId;
  private List<Nozzle> nozzleList;
  private ExperimentInfo currExp;
  private List<LinearLayout> ll_containers;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nozzle_info);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    initViews();
    loadData();
    loadNetworkData();
  }

  private void loadNetworkData() {
    for(Nozzle nozzle:nozzleList){
      loadVariableList(nozzle.getId(),ll_content);
    }
  }

  private void loadVariableList(String nozzleId, final View mainContainer) {
    if (!NetworkUtils.isOnline(this)) {
      Toasty.error(this, getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
      return;
    }

    new RestClient().getApiService()
        .getNozzleVariables(nozzleId, new InfoBody(true).getHttpParams(),
            new Callback<CommonListResult<NozzleVariable>>() {
              @Override
              public void success(CommonListResult<NozzleVariable> res, Response response) {
                if (!res.isSuccess()) {
                  Toasty.error(NozzleInfoViewActivity.this, getString(R.string.load_error),
                      Toast.LENGTH_SHORT).show();
                  return;
                }

                generateVariableView(res.getData(), (LinearLayout) mainContainer);
              }

              @Override public void failure(RetrofitError error) {

              }
            });
  }

  private void generateVariableView(List<NozzleVariable> data, LinearLayout ll_content) {

    RelativeLayout rl_header = new RelativeLayout(this);
    rl_header.setBackgroundResource(R.drawable.bg_scroll_listheader);
    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewUtils.getDimenByRateWidth(70));

    TextView tv_tips_header = new TextView(this);

    RelativeLayout.LayoutParams params_tv_name =
        new RelativeLayout.LayoutParams(ViewUtils.getDimenByRateWidth(180),
            ViewGroup.LayoutParams.MATCH_PARENT);

    tv_tips_header.setTextColor(Color.WHITE);
    tv_tips_header.setTypeface(null, Typeface.BOLD);
    tv_tips_header.setBackgroundColor(getResources().getColor(R.color.header_light_blue));
    ViewUtils.adjustViewTextSize(tv_tips_header, 28);
    tv_tips_header.setGravity(Gravity.CENTER);

    tv_tips_header.setText(getString(R.string.nozzle_variableList));
    tv_tips_header.setPadding(ViewUtils.getDimenByRateWidth(30), 0,
        ViewUtils.getDimenByRateWidth(30), 0);
    rl_header.addView(tv_tips_header, params_tv_name);
    ll_content.addView(rl_header, params);
    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewUtils.getDimenByRateWidth(80));
    params.leftMargin=ViewUtils.getDimenByRateWidth(25);

    for(NozzleVariable nozzleVariable:data){
      ll_content.addView(generateSubView(nozzleVariable.getName(),nozzleVariable.generateValue()),params);
    }
  }

  private void loadData() {
    currExp =
        (ExperimentInfo) DBManager.getCache(CacheKeyConstant.EXPERIMENT_CACHE_ID + experimentId);
    if (currExp == null) {
      Toasty.error(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
      return;
    }
    nozzleList = currExp.getNozzles();
    if (nozzleList == null || nozzleList.size() == 0) {
      Toasty.error(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
      return;
    }
    tv_header_title.setText(nozzleList.get(0).getName());
    ll_containers = new ArrayList<>(nozzleList.size());
    generateNozzleBaseInfoView(nozzleList);
  }

  private void generateNozzleBaseInfoView(List<Nozzle> nozzleList) {

    if (nozzleList == null || nozzleList.size() == 0) {
      return;
    }

    ll_content.removeAllViews();

    for (Nozzle n : nozzleList) {

      RelativeLayout rl_header = new RelativeLayout(this);
      rl_header.setBackgroundResource(R.drawable.bg_scroll_listheader);
      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewUtils.getDimenByRateWidth(70));

      TextView tv_tips_header = new TextView(this);

      RelativeLayout.LayoutParams params_tv_name =
          new RelativeLayout.LayoutParams(ViewUtils.getDimenByRateWidth(180),
              ViewGroup.LayoutParams.MATCH_PARENT);

      tv_tips_header.setTextColor(Color.WHITE);
      tv_tips_header.setTypeface(null, Typeface.BOLD);
      tv_tips_header.setBackgroundColor(getResources().getColor(R.color.header_light_blue));
      ViewUtils.adjustViewTextSize(tv_tips_header, 28);
      tv_tips_header.setGravity(Gravity.CENTER);

      tv_tips_header.setText(getString(R.string.nozzle_base_param));
      tv_tips_header.setPadding(ViewUtils.getDimenByRateWidth(30), 0,
          ViewUtils.getDimenByRateWidth(30), 0);
      rl_header.addView(tv_tips_header, params_tv_name);
      ll_content.addView(rl_header, params);

      //烧嘴编号
      params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewUtils.getDimenByRateWidth(80));
      params.leftMargin=ViewUtils.getDimenByRateWidth(25);
      ll_content.addView(
          generateSubView(getString(R.string.nozzle_serialNumber), n.getSerialNumber()), params);
      //烧嘴类型
      ll_content.addView(
          generateSubView(getString(R.string.nozzle_type), n.getType()), params);
      //烧嘴能介
      ll_content.addView(
          generateSubView(getString(R.string.nozzle_medium), n.getMedium()), params);
      //点火枪类型
      ll_content.addView(
          generateSubView(getString(R.string.nozzle_pointGunType), n.getPointGunType()), params);
      //点火枪能介
      ll_content.addView(
          generateSubView(getString(R.string.nozzle_pointGunMedium), n.getPointGunMedium()), params);
    }
  }

  public View generateSubView(String title, String value) {
    RelativeLayout mainView = new RelativeLayout(this);

    TextView tv_name=new TextView(this);
    RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.CENTER_VERTICAL);

    ViewUtils.adjustViewTextSize(tv_name,25);
    tv_name.setTextColor(Color.BLACK);
    tv_name.setText(title);
    mainView.addView(tv_name,params);

    TextView tv_content = new TextView(this);
    params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.CENTER_VERTICAL);
    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    params.rightMargin=ViewUtils.getDimenByRateWidth(25);

    tv_content.setText(value);
    tv_content.setBackgroundResource(R.drawable.bg_workstat_item);
    tv_content.setTextColor(Color.WHITE);
    tv_content.setPadding(ViewUtils.getDimenByRateWidth(20), ViewUtils.getDimenByRateWidth(10),
        ViewUtils.getDimenByRateWidth(20), ViewUtils.getDimenByRateWidth(10));

    mainView.addView(tv_content,params);

    params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewUtils.getDimenByRateWidth(1));
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

    View divider=new View(this);
    divider.setBackgroundColor(getResources().getColor(R.color.divider_grey));

    mainView.addView(divider,params);

    return mainView;
  }

  private void initViews() {
    ViewUtils.adjustViewTextSize(tv_header_back, 38);
    ViewUtils.adjustViewTextSize(tv_header_title, 35);
  }

  @OnClick(R.id.rl_header_back) public void onBackClick() {
    finish();
  }
}
