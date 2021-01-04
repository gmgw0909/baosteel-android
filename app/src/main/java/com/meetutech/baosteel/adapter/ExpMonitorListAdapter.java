package com.meetutech.baosteel.adapter;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.adapter
// Author: culm at 2017-05-01
//*********************************************************

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.HTTPConstant;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.result.CommonObjectResult;
import com.meetutech.baosteel.http.result.ExperimentCamera;
import com.meetutech.baosteel.model.http.CameraThumbnail;
import com.meetutech.baosteel.utils.MD5Utils;
import com.meetutech.baosteel.utils.ViewUtils;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ExpMonitorListAdapter extends BaseAdapter {

  private Context ctx;
  private List<ExperimentCamera> data;
  private String experimentId;
  private String projectId;

  public ExpMonitorListAdapter(Context ctx, List<ExperimentCamera> data, String experimentId,
      String projectId) {
    this.ctx = ctx;
    this.data = data;
    this.experimentId = experimentId;
    this.projectId = projectId;
  }

  @Override
  public int getCount() {
    return data == null ? 0 : data.size();
  }

  @Override
  public Object getItem(int i) {
    return i;
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View convertView, ViewGroup viewGroup) {
    View view = convertView;
    final ViewHolder holder;
    ExperimentCamera exp=data.get(i);
    if (convertView == null) {
      view = View.inflate(ctx, R.layout.list_item_exp_monitor, null);
      holder = new ViewHolder();
      holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
      holder.btn_play=(Button)view.findViewById(R.id.btn_play);
      holder.iv_bg=(ImageView)view.findViewById(R.id.iv_background);
      ViewUtils.adjustViewTextSize(holder.tv_name,28);
      AbsListView.LayoutParams absLayoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, ViewUtils.getDimenByRateWidth(500f));
      view.setLayoutParams(absLayoutParams);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }

    try {
      loadData(holder, exp);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return view;
  }

  private void loadData(final ViewHolder holder, ExperimentCamera exp) throws Exception{
      holder.tv_name.setText(exp.getName());
      holder.btn_play.setOnClickListener(new VideoCameraClickListener(exp));
      new RestClient().getApiService()
          .getCameraThumbnail(MD5Utils.md5(exp.getId()), new InfoBody(true).getHttpParams(),
              new Callback<CommonObjectResult<CameraThumbnail>>() {
                @Override public void success(CommonObjectResult<CameraThumbnail> res,
                    Response response) {
                    if(!res.isSuccess()||res.getData()==null){
                      return;
                    }

                  Picasso.with(ctx).load(HTTPConstant.getStaticFileFullPath(res.getData().getImage()))
                      .into(holder.iv_bg);

                }

                @Override public void failure(RetrofitError error) {

                }
              });
  }

  public static class ViewHolder {
    public TextView tv_name;
    public Button btn_play;
    public ImageView iv_bg;
  }

  class VideoCameraClickListener implements View.OnClickListener {
    private ExperimentCamera camera;

    public VideoCameraClickListener(ExperimentCamera camera) {
      this.camera = camera;
    }

    @Override public void onClick(View view) {
      ARouter.getInstance()
          .build(RouterConstant.HLS_PLAYER_URL)
          .withString("title", camera.getName())
          .withString("experimentId",experimentId)
          .withString("cameraId",camera.getId())
          .withString("projectId",projectId)
          .withString("uri", String.format(HTTPConstant.FMT_STATIC_HLS_STREAM_URL,camera.getId()))
          .navigation();
    }
  }

}
