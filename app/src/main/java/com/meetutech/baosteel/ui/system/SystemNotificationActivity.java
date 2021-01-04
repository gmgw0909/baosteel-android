package com.meetutech.baosteel.ui.system;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui
// Author: culm at 2017-05-01
//*********************************************************

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.adapter.NotificationListAdapter;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.NotificationListBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.model.http.PushNotification;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.DBManager;
import com.meetutech.baosteel.utils.NetworkUtils;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@Route(path="/system/notification")
public class SystemNotificationActivity extends BaseActivity{

  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.list_notification) ListView list_notification;

  private String deviceToken;

  private List<PushNotification> notifications;
  private NotificationListAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_system_notification);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    initViews();
    loadViews();
    loadNetworkData();
  }

  private void loadNetworkData() {

    if(!NetworkUtils.isOnline(this)){
      Toasty.error(this,getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
      return;
    }

    deviceToken= (String) DBManager.getCache(CacheKeyConstant.NOTIFICATION_DEVICE_TOKEN);

    if(TextUtils.isEmpty(deviceToken)){
      Toasty.error(this,getString(R.string.load_error), Toast.LENGTH_SHORT).show();
      return;
    }

    BProgressDialog.showProgressDialog(this);

    new RestClient().getApiService()
        .getPushResults(new NotificationListBody(deviceToken).getHttpParams(),
            new Callback<CommonListResult<PushNotification>>() {
              @Override
              public void success(CommonListResult<PushNotification> res,
                  Response response) {
                BProgressDialog.dismissProgressDialog();
                if(!res.isSuccess()){
                  Toasty.error(SystemNotificationActivity.this,
                      TextUtils.isEmpty(res.getError())?getString(R.string.load_error):res.getError(),Toast.LENGTH_SHORT).show();
                  return;
                }
                notifications.clear();
                notifications.addAll(res.getData());
                adapter.notifyDataSetChanged();
              }

              @Override public void failure(RetrofitError error) {
                BProgressDialog.dismissProgressDialog();
              }
            });


  }

  private void initViews() {
    ViewUtils.adjustViewTextSize(tv_header_title,38);

  }


  private void loadViews() {
    tv_header_title.setText(getString(R.string.txt_notification_title));
    notifications=new ArrayList<>();
    adapter=new NotificationListAdapter(this,notifications);
    list_notification.setAdapter(adapter);
  }

  @OnClick(R.id.btn_header_close)
  public void onClose(){
    finish();
  }

}
