package com.meetutech.baosteel.ui.report;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui
// Author: culm at 2017-05-01
//*********************************************************

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.HTTPConstant;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.model.data.Project;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.AppDBUtils;
import com.meetutech.baosteel.utils.ViewUtils;
import es.dmoral.toasty.Toasty;
import java.text.SimpleDateFormat;

@Route(path = RouterConstant.REPORT_DOWNLOAD_VIEW)
public class ReportDownloadActivity extends BaseActivity{

  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.tv_report_name) TextView tv_report_name;
  public @BindView(R.id.tv_report_size) TextView tv_report_size;
  public @BindView(R.id.tv_report_validtime) TextView tv_report_validtime;

  public @Autowired String projectId;
  public Project currProject;
  private String currDownloadLink;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_report_download);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    currProject= AppDBUtils.getProjectSimpleInfo(projectId);
    if(currProject==null){
      Toasty.error(this,"获取本地数据失败，请稍候重试！");
      finish();
    }
    initViews();
    loadViews();
  }

  private void initViews() {
    ViewUtils.adjustViewTextSize(tv_header_title,38);
    ViewUtils.adjustViewTextSize(tv_report_name,32);
    ViewUtils.adjustViewTextSize(tv_report_validtime,26);
    ViewUtils.adjustViewTextSize(tv_report_size,32);
    ViewUtils.adjustViewTextSize((TextView) findViewById(R.id.btn_download),34);
  }

  private void loadViews() {

    //tv_header_title.setText(getString(R.string.txt_report_download_title));
    //tv_report_name.setText(currProject.getReportFileName());
    //tv_report_size.setText(String.format("%.2fMB",Float.parseFloat(currProject.getReportFileSize())/1024));
    //tv_report_validtime.setText("有效期至"+new SimpleDateFormat("yyyy-MM-dd").format(currProject.getReportValidPeriod()));

    if(currProject!=null){
      loadLocalData(currProject);
    }

  }

  private void loadLocalData(Project currProject) {
    tv_report_name.setText(currProject.getReportFileName());

    if(currProject.getReportValidPeriod()==null){
      tv_report_validtime.setText("有效期：永久");

    } else {
      tv_report_validtime.setText("有效期至"+new SimpleDateFormat("yyyy-MM-dd").format(currProject.getReportValidPeriod()));
    }
    tv_report_size.setText(
        Float.parseFloat(currProject.getReportFileSize())/(1024.0f*1024.0f)<1.0?
        String.format("%.2f KB",Float.parseFloat(currProject.getReportFileSize())/1024.0f):
        String.format("%.2f MB",Float.parseFloat(currProject.getReportFileSize())/(1024.0f*1024.0f)));
    currDownloadLink= HTTPConstant.getStaticFileFullPath(currProject.getReportFileUrl());
  }

  @OnClick(R.id.btn_header_close)
  public void onClose(){
    finish();
  }

  @OnClick(R.id.btn_download)
  public void onDownload(){
    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(currDownloadLink));
    startActivity(intent);
  }
}
