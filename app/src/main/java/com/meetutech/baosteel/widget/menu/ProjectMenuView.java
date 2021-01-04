package com.meetutech.baosteel.widget.menu;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.widget.menu
// Author: culm at 2017-04-28
//*********************************************************

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.model.data.Project;
import com.meetutech.baosteel.model.http.ProjectMenu;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.layout.AbsPercentRelativeLayout;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;

public class ProjectMenuView{
  private AbsPercentRelativeLayout menuView;
  private List<ProjectMenu> menus;
  private ProjectMenuView instance;
  private Context context;

  public ProjectMenuView(Context context,List<ProjectMenu> menus) {
    this.context=context;
    this.menus = menus;
  }

  public ProjectMenuView setMenus(Context context,List<ProjectMenu> menus){
    instance=new ProjectMenuView(context,menus);
    return instance;
  }

  public ProjectMenuView addMenu(Context context,ProjectMenu menu){
    if(instance==null||menus!=null){
      List<ProjectMenu> _menus=new ArrayList<>();
      _menus.add(menu);
      instance=new ProjectMenuView(context,_menus);
    } else {
      this.menus.add(menu);
    }
    return instance;
  }

  public ProjectMenuView addMenus(Context context,List<ProjectMenu> menus){
    if(instance==null||menus!=null){
      List<ProjectMenu> _menus=new ArrayList<>();
      _menus.addAll(menus);
      instance=new ProjectMenuView(context,_menus);
    } else {
      this.menus.addAll(menus);
    }
    return instance;
  }

  public View build(View.OnClickListener eventListener){
    menuView= (AbsPercentRelativeLayout) View.inflate(context, R.layout.menu_project,null);

    if(this.menus!=null&&this.menus.size()>0){
      LinearLayout ll_content= (LinearLayout) menuView.findViewById(R.id.ll_content);
      for(ProjectMenu menuData:this.menus) {
        View subMenus = View.inflate(context, R.layout.sliding_menu_item, null);
        LinearLayout.LayoutParams subLayoutParams=new LinearLayout.LayoutParams(ViewUtils.getDimenByRateWidth(265),
            ViewUtils.getDimenByRateWidth(89));
        subLayoutParams.setMargins(ViewUtils.getDimenByRateWidth(23),0,0,0);

        TextView tv_menuName= (TextView) subMenus.findViewById(R.id.tv_menuName);
        tv_menuName.setText(menuData.getName());
        ViewUtils.adjustViewTextSize(tv_menuName,35);
        subMenus.setTag(menuData.getUrl());
        subMenus.setOnClickListener(new OnMenuClickListener(context,menuData.getName(),menuData.getKey(),menuData.getContent(),menuData.getProject()));
        ll_content.addView(subMenus,subLayoutParams);
      }
    }

    menuView.findViewById(R.id.btn_message).setOnClickListener(eventListener);
    menuView.findViewById(R.id.btn_setting).setOnClickListener(eventListener);

    return menuView;
  }

  public static class OnMenuClickListener implements View.OnClickListener{

    private String name;
    private String key;
    private Context ctx;
    private String content;
    private Project project;

    public OnMenuClickListener(Context ctx, String name, String key, String content,
        Project project) {
      this.name = name;
      this.key = key;
      this.ctx = ctx;
      this.content=content;
      this.project=project;
    }

    @Override public void onClick(View view) {

      String url= (String) view.getTag();

      if(url.equals(RouterConstant.REPORT_DOWNLOAD_VIEW)&&(project==null||
          TextUtils.isEmpty(project.getReportFileUrl()))){
        Toasty.error(BSApplication.getInstance(),"暂无实验报告！", Toast.LENGTH_SHORT).show();
        return;
      }

      ARouter.getInstance().build((String) view.getTag())
          .withString("key",key)
          .withString("title",name)
          .withBoolean("isAuth",true)
          .withString("content", content)
          .withString("projectId",project==null?"":project.getId())
          .withSerializable("project",project)
          .navigation(ctx);
    }
  }

}
