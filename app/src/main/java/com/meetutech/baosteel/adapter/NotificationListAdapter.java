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
import android.widget.TextView;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.model.http.PushNotification;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.layout.AbsPercentRelativeLayout;
import java.util.List;

public class NotificationListAdapter extends BaseAdapter {

  private Context ctx;
  private List<PushNotification> data;

  public NotificationListAdapter(Context ctx,List<PushNotification> data){
    this.ctx=ctx;
    this.data=data;
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
    PushNotification notification=data.get(i);
    if (convertView == null) {
      view = View.inflate(ctx, R.layout.item_list_notification, null);
      holder = new ViewHolder();
      holder.tv_duration = (TextView) view.findViewById(R.id.tv_duration);
      holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
      holder.tv_state = (TextView) view.findViewById(R.id.tv_state);
      holder.btn_details = (Button) view.findViewById(R.id.btn_details);

      ViewUtils.adjustViewTextSize(holder.tv_name,30);
      ViewUtils.adjustViewTextSize(holder.tv_state,29);
      ViewUtils.adjustViewTextSize(holder.tv_duration,29);
      AbsPercentRelativeLayout.LayoutParams params=
          (AbsPercentRelativeLayout.LayoutParams) holder.tv_state.getLayoutParams();
      params.rightMargin=ViewUtils.getDimenByRateWidth(14.4f);
      holder.tv_state.setLayoutParams(params);

      AbsListView.LayoutParams absLayoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, ViewUtils.getDimenByRateWidth(130));
      view.setLayoutParams(absLayoutParams);
      view.setTag(holder);

    } else {
      holder = (ViewHolder) view.getTag();
    }

    try {
      loadData(holder, notification);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return view;
  }

  //private class OnInfoClickListener implements View.OnClickListener{
  //
  //  private Context context;
  //  private ProjectExp exp;
  //
  //  public OnInfoClickListener(Context context, ProjectExp exp) {
  //    this.context = context;
  //    this.exp = exp;
  //  }
  //
  //  @Override public void onClick(View view) {
  //    ARouter.getInstance().build("/experiment/info")
  //        .withString("title",exp.getName())
  //        .navigation(ctx);
  //  }
  //}

  private void loadData(ViewHolder holder, PushNotification notification) throws Exception{
      holder.tv_name.setText(notification.getTitle());
      holder.tv_state.setText(notification.getText());
  }

  public static class ViewHolder {
    public TextView tv_name;
    public TextView tv_duration;
    public TextView tv_state;
    public Button btn_details;
  }

}
