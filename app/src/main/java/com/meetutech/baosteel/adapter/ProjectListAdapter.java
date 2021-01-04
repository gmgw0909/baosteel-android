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
import com.meetutech.baosteel.model.data.Project;
import com.meetutech.baosteel.utils.ViewUtils;
import java.util.List;

public class ProjectListAdapter extends BaseAdapter {

  private Context ctx;
  private List<Project> data;

  public ProjectListAdapter(Context ctx, List<Project> data) {
    this.ctx = ctx;
    this.data = data;
  }

  @Override public int getCount() {
    return data == null ? 0 : data.size();
  }

  @Override public Object getItem(int i) {
    return i;
  }

  @Override public long getItemId(int i) {
    return i;
  }

  @Override public View getView(int i, View convertView, ViewGroup viewGroup) {
    View view = convertView;
    final ViewHolder holder;
    Project project = data.get(i);

    if (convertView == null) {
      view = View.inflate(ctx, R.layout.item_list_project, null);
      holder = new ViewHolder();
      holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
      holder.tv_description = (TextView) view.findViewById(R.id.tv_description);

      ViewUtils.adjustViewTextSize(holder.tv_name, 30);
      ViewUtils.adjustViewTextSize(holder.tv_description, 29);

      AbsListView.LayoutParams absLayoutParams =
          new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
              ViewUtils.getDimenByRateWidth(106));
      view.setLayoutParams(absLayoutParams);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }

    try {
      loadData(view, holder, project);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return view;
  }

  private class OnInfoClickListener implements View.OnClickListener {

    private Context context;
    private Project exp;

    public OnInfoClickListener(Context context, Project exp) {
      this.context = context;
      this.exp = exp;
    }

    @Override public void onClick(View view) {
      ARouter.getInstance()
          .build("/experiment/list")
          .withString("title", exp.getName())
          .withString("projectID", exp.getId())
          .withSerializable("project",exp)
          .withBoolean("longTerm",exp.isLongTerm())
          .navigation(ctx);
    }
  }

  private void loadData(View view, ViewHolder holder, Project project) throws Exception {
    holder.tv_name.setText(project.getName());
    holder.tv_description.setText(project.getDescription());
    view.setOnClickListener(new OnInfoClickListener(ctx, project));
  }

  public static class ViewHolder {
    public TextView tv_name;
    public TextView tv_description;
  }
}
