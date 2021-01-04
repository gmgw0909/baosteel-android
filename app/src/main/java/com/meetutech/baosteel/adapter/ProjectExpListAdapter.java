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
import android.widget.TextView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.model.http.ExperimentInfo;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.textview.TextViewLabel;
import java.util.List;

public class ProjectExpListAdapter extends BaseAdapter {

  private Context ctx;
  private List<ExperimentInfo> data;
  private String projectName;
  private boolean longTerm;
  private String projectId;

  public ProjectExpListAdapter(Context ctx, List<ExperimentInfo> data, String projectName,String projectId,
      boolean longTerm) {
    this.ctx = ctx;
    this.data = data;
    this.projectName = projectName;
    this.longTerm=longTerm;
    this.projectId=projectId;
  }

  @Override public int getCount() {
    return data == null ? 0 : data.size();
  }

  @Override public Object getItem(int i) {
    return data.get(i);
  }

  @Override public long getItemId(int i) {
    return i;
  }

  @Override public View getView(int i, View convertView, ViewGroup viewGroup) {
    View view = convertView;
    final ViewHolder holder;
    ExperimentInfo exp = data.get(i);
    if (convertView == null) {
      view = View.inflate(ctx, R.layout.item_list_exp, null);
      holder = new ViewHolder();
      holder.tv_desc = (TextView) view.findViewById(R.id.tv_desc);
      holder.tv_duration = (TextView) view.findViewById(R.id.tv_duration);
      holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
      holder.tv_state = (TextViewLabel) view.findViewById(R.id.tv_state);

      ViewUtils.adjustViewTextSize(holder.tv_desc, 29);
      ViewUtils.adjustViewTextSize(holder.tv_name, 34);
      ViewUtils.adjustViewTextSize(holder.tv_state, 27);
      ViewUtils.adjustViewTextSize(holder.tv_duration, 27);

      AbsListView.LayoutParams absLayoutParams =
          new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
              ViewUtils.getDimenByRateWidth(180));
      view.setLayoutParams(absLayoutParams);
      view.setTag(holder);
      view.setOnClickListener(new OnExpClickListener(exp, ctx, projectName));
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

  private class OnExpClickListener implements View.OnClickListener {

    private ExperimentInfo exp;
    private Context ctx;
    private String projectName;

    public OnExpClickListener(ExperimentInfo exp, Context ctx, String projectName) {
      this.exp = exp;
      this.ctx = ctx;
      this.projectName = projectName;
    }

    @Override public void onClick(View view) {

      ARouter.getInstance()
          .build(longTerm?RouterConstant.EXPERIMENT_REALTIME_DATA:RouterConstant.EXPERIMENT_INFO)
          .withString("title", exp.getName())
          .withString("experimentId", exp.getId())
          .withString("projectName", projectName)
          .withString("projectId",projectId)
          .withBoolean("longTerm",longTerm)
          .navigation(ctx);
    }
  }

  private void loadData(ViewHolder holder, ExperimentInfo exp) throws Exception {
    holder.tv_name.setText(exp.getName());
    holder.tv_duration.setText(exp.getDynamicDuration(ctx));
    holder.tv_desc.setText(exp.getDescription());

    holder.tv_state.setText(exp.getDynamicStatus());
    holder.tv_state.setLabelColor(exp.getDynamicStatusColor());

    //
    //if(exp.getDynamicStatus().equals("已完成")){
    //  holder.tv_state.setTextColor(ctx.getResources().getColor(R.color.text_color_finished));
    //} else if(exp.getDynamicStatus().equals("进行中")){
    //  holder.tv_state.setTextColor(ctx.getResources().getColor(R.color.text_color_ing));
    //} else {
    //  holder.tv_state.setTextColor(ctx.getResources().getColor(R.color.text_color_ready));
    //}

  }

  public static class ViewHolder {
    public TextView tv_name;
    public TextView tv_desc;
    public TextView tv_duration;
    public TextViewLabel tv_state;
  }
}
