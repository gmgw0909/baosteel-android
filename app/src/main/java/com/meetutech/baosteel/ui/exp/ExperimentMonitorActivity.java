package com.meetutech.baosteel.ui.exp;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.exp
// Author: culm at 2017-05-02
//*********************************************************

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.adapter.ExpMonitorListAdapter;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.http.result.ExperimentCamera;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.NetworkUtils;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@Route(path = RouterConstant.CAMERA_MONITOR_URL) public class ExperimentMonitorActivity extends BaseActivity {

  @BindView(R.id.tv_header_title) public TextView tv_header_title;
  @BindView(R.id.list_monitor)
  public ListView listView;

  @Autowired public String title;
  @Autowired public String experimentId;
  @Autowired public String projectId;

  private List<ExperimentCamera> list_data;
  private BaseAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_experiment_monitor);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    initViews();
    loadViews();
    loadNetworkData();
  }

  private void loadNetworkData() {

    if (TextUtils.isEmpty(experimentId)) {
      Toasty.error(this, getString(R.string.load_exp_id_empty), Toast.LENGTH_SHORT).show();

      return;
    }

    if (!NetworkUtils.isOnline(this)) {
      Toasty.error(this, getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
      return;
    }

    BProgressDialog.showProgressDialog(this);
    new RestClient().getApiService()
        .getExperimentCamera(experimentId, new InfoBody(true).getHttpParams(),
            new Callback<CommonListResult<ExperimentCamera>>() {
              @Override
              public void success(CommonListResult<ExperimentCamera> res, Response response) {
                BProgressDialog.dismissProgressDialog();
                if (!res.isSuccess() || !TextUtils.isEmpty(res.getError())
                    || res.getData() == null) {
                  Toasty.error(ExperimentMonitorActivity.this,
                      NetworkUtils.getDefaultErrorMessage(ExperimentMonitorActivity.this,
                          res.getError()), Toast.LENGTH_SHORT).show();
                  return;
                }
                list_data=res.getData();
                if(list_data.size()==0){
                  Toasty.warning(ExperimentMonitorActivity.this,getString(R.string.empty_load_monitor), Toast.LENGTH_SHORT).show();
                  finish();
                }
                adapter=new ExpMonitorListAdapter(ExperimentMonitorActivity.this,list_data,experimentId,projectId);
                listView.setAdapter(adapter);
              }

              @Override public void failure(RetrofitError error) {
                BProgressDialog.dismissProgressDialog();
                Toasty.error(ExperimentMonitorActivity.this, getString(R.string.load_error),
                    Toast.LENGTH_SHORT).show();
              }
            });
  }



  private void loadViews() {
    tv_header_title.setText(String.format(getString(R.string.txt_experiment_monitor_title), title));
    list_data=new ArrayList<>();
    adapter=new ExpMonitorListAdapter(this,list_data,experimentId,projectId);
    listView.setAdapter(adapter);
  }

  private void initViews() {
    ViewUtils.adjustViewTextSize(tv_header_title, 38);
  }

  @OnClick(R.id.btn_header_close) public void onClose() {
    finish();
  }
}
