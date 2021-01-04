package com.meetutech.baosteel.ui.exp;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.exp
// Author: culm at 2017-05-02
//*********************************************************

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.lzy.ninegrid.NineGridView;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.controller.ExperimentListController;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.ExperimentApprovalBody;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.body.SnapshotDataBody;
import com.meetutech.baosteel.http.body.SnapshotListInfoBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.http.result.CommonObjectResult;
import com.meetutech.baosteel.http.result.ExperimentCamera;
import com.meetutech.baosteel.model.DBUtils;
import com.meetutech.baosteel.model.http.Approval;
import com.meetutech.baosteel.model.http.ExperimentInfo;
import com.meetutech.baosteel.model.http.KeyVariable;
import com.meetutech.baosteel.model.http.Snapshot;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.NetworkUtils;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import com.meetutech.baosteel.widget.NineGridViewClickAdapter;
import com.meetutech.baosteel.widget.layout.AbsPercentLinearLayout;
import com.meetutech.baosteel.widget.textview.TextViewLabel;
import com.squareup.picasso.Picasso;
import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@Route(path = RouterConstant.EXPERIMENT_INFO) public class ExperimentInfoActivity
    extends BaseActivity {

  @BindView(R.id.tv_header_title) public TextView tv_header_title;
  @BindView(R.id.tv_tips_serialNumber) public TextView tv_tips_serialNumber;
  @BindView(R.id.tv_serialNumber) public TextView tv_serialNumber;
  @BindView(R.id.tv_tips_exp_project) public TextView tv_tips_exp_project;
  @BindView(R.id.tv_exp_project) public TextView tv_exp_project;

  @BindView(R.id.tv_tips_exp_furnace) public TextView tv_tips_exp_furnace;
  @BindView(R.id.tv_exp_furnace) public TextView tv_exp_furnace;
  @BindView(R.id.tv_tips_exp_startTime) public TextView tv_tips_exp_startTime;
  @BindView(R.id.tv_exp_startTime) public TextView tv_exp_startTime;
  @BindView(R.id.tv_tips_exp_duration) public TextView tv_tips_exp_duration;
  @BindView(R.id.tv_exp_duration) public TextView tv_exp_duration;
  @BindView(R.id.tv_tips_exp_description) public TextView tv_tips_exp_description;
  @BindView(R.id.tv_exp_description) public TextView tv_exp_description;
  @BindView(R.id.tv_tips_exp_approval) public TextView tv_tips_exp_approval;
  @BindView(R.id.tv_exp_approval) public TextView tv_exp_approval;
  @BindView(R.id.tv_state) public TextViewLabel tv_state;
  @BindView(R.id.tv_furnace_info) public TextViewLabel tv_furnace_info;

  @BindView(R.id.btn_chat) public Button btn_chat;
  @BindView(R.id.btn_rating) public Button btn_rating;
  @BindView(R.id.btn_realtime) public Button btn_realtime;
  @BindView(R.id.btn_analysis) public Button btn_analysis;
  @BindView(R.id.btn_header_camera) public Button btn_camera;

  @BindView(R.id.rl_exp_approval) public View rl_exp_approval;
  @BindView(R.id.ll_workstat) public AbsPercentLinearLayout ll_workstat;
  @BindView(R.id.rl_exp_approver) public View rl_exp_approver;

  @Autowired public String title;
  @Autowired public String experimentId;
  @Autowired public String projectId;
  @Autowired public String projectName;
  @Autowired public boolean longTerm;

  public ExperimentInfo currExp;
  private List<Snapshot> currSnapshots;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_experiment_info);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    NineGridView.setImageLoader(new PicassoImageLoader());
    initViews();
    loadViews();
    loadNetworkData();
  }

  private void loadNetworkData() {

    if (!NetworkUtils.isOnline(this)) {
      Toasty.error(this, getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
      return;
    }

    BProgressDialog.showProgressDialog(this);

    new RestClient().getApiService()
        .getSnapshotList(experimentId, new SnapshotListInfoBody(true).getHttpParams(),
            new Callback<CommonListResult<Snapshot>>() {
              @Override public void success(CommonListResult<Snapshot> res, Response response) {
                BProgressDialog.dismissProgressDialog();
                if (!res.isSuccess()) {
                  Toasty.error(ExperimentInfoActivity.this,
                      TextUtils.isEmpty(res.getError()) ? getString(R.string.load_error)
                          : res.getError(), Toast.LENGTH_SHORT).show();
                  return;
                }
                currSnapshots = res.getData();
                if (currExp.getWorkstat() != null) {
                  generateWorkStatView(currSnapshots, currExp.getKeyVariables());
                }
              }

              @Override public void failure(RetrofitError error) {
                BProgressDialog.dismissProgressDialog();
                Toasty.error(ExperimentInfoActivity.this, getString(R.string.load_error),
                    Toast.LENGTH_SHORT).show();
              }
            });

    new RestClient().getApiService()
        .getExperimentCamera(experimentId, new InfoBody(true).getHttpParams(),
            new Callback<CommonListResult<ExperimentCamera>>() {
              @Override
              public void success(CommonListResult<ExperimentCamera> res,
                  Response response) {
                  if(res.isSuccess()&&res.getData()!=null&&res.getData().size()>0){
                    btn_camera.setVisibility(View.VISIBLE);
                  } else {
                    btn_camera.setVisibility(View.GONE);
                  }
              }

              @Override public void failure(RetrofitError error) {

              }
            });

  }

  private void loadViews() {
    tv_header_title.setText(title);
    currExp = DBUtils.getExperiment(experimentId);
    if (currExp == null) {
      Toasty.error(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
      return;
    }
    tv_serialNumber.setText(currExp.getSerialNumber());
    tv_exp_project.setText(projectName);
    tv_exp_furnace.setText(currExp.getFurnace().getName());
    tv_exp_startTime.setText(currExp.getDynamicStartTime());
    tv_exp_duration.setText(currExp.getDurationLabel());
    tv_exp_description.setText(currExp.getDescription());
    if (currExp.getStatus().equals("running")) {
      rl_exp_approval.setVisibility(View.VISIBLE);
    } else {
      rl_exp_approval.setVisibility(View.GONE);
    }
    tv_furnace_info.setVisibility(
        currExp.getNozzles() == null || currExp.getNozzles().size() == 0 ? View.GONE
            : View.VISIBLE);
    tv_exp_approval.setText(currExp.getStatus().equals("running") ? "同意" : "");
    tv_state.setText(currExp.getDynamicStatus());
    tv_state.setLabelColor(currExp.getDynamicStatusColor());
    rl_exp_approver.setVisibility(
        isShowApproval(currExp.getApprovals()) ? View.VISIBLE : View.GONE);
  }

  private boolean isShowApproval(List<Approval> approvals) {

    if(currExp==null||!currExp.getStatus().equals("approval")){
      return false;
    }

    if (approvals == null || approvals.size() == 0) {
      return false;
    }

    for (int i = 0; i < approvals.size(); i++) {
      Approval approval = approvals.get(i);
      if (approval == null || TextUtils.isEmpty(approval.getApproverId())) {
        continue;
      }
      if (!approval.getApproverId().equals(BSApplication.mainUserId)) {
        continue;
      }
      if (i == 0) {
        if (approval.getStatus().equals(ExperimentApprovalBody.STATUS_AGREE) || approval.getStatus()
            .equals(ExperimentApprovalBody.STATUS_DISAGREE)) {
          return false;
        } else {
          return true;
        }
      }
      //如果不是第一位用户
      Approval preApproval = approvals.get(i - 1);

      if (preApproval.getStatus().equals(ExperimentApprovalBody.STATUS_AGREE) && (
          !approval.getStatus().equals(ExperimentApprovalBody.STATUS_AGREE) || !approval.getStatus()
              .equals(ExperimentApprovalBody.STATUS_DISAGREE))) {
        return true;
      }
    }

    return false;
  }

  private void generateWorkStatView(List<Snapshot> snapshots,
      List<KeyVariable> keyVariables) {
    //Collections.reverse(workstats);
    for (Snapshot snapshot : snapshots) {
      RelativeLayout rl_header = new RelativeLayout(this);
      rl_header.setBackgroundResource(R.drawable.bg_scroll_listheader);
      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewUtils.getDimenByRateWidth(70));

      TextView tv_name = new TextView(this);

      RelativeLayout.LayoutParams params_tv_name = new RelativeLayout.LayoutParams(
          ViewUtils.getDimenByRateWidth(ViewGroup.LayoutParams.WRAP_CONTENT),
          ViewGroup.LayoutParams.MATCH_PARENT);

      tv_name.setTextColor(Color.WHITE);
      tv_name.setTypeface(null, Typeface.BOLD);
      tv_name.setBackgroundColor(getResources().getColor(R.color.header_light_blue));
      ViewUtils.adjustViewTextSize(tv_name, 28);
      tv_name.setGravity(Gravity.CENTER);

      tv_name.setText(snapshot.getWorkstat().getName());
      tv_name.setPadding(ViewUtils.getDimenByRateWidth(30), 0, ViewUtils.getDimenByRateWidth(30),
          0);
      rl_header.addView(tv_name, params_tv_name);

      ll_workstat.addView(rl_header, params);

      LinearLayout ll_content = new LinearLayout(this);

      ll_content.setOrientation(LinearLayout.VERTICAL);
      params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT);

      ll_content.setPadding(ViewUtils.getDimenByRateWidth(30), ViewUtils.getDimenByRateWidth(5),
          ViewUtils.getDimenByRateWidth(30), ViewUtils.getDimenByRateWidth(5));

      ll_workstat.addView(ll_content, params);

      for (KeyVariable keyVariable : keyVariables) {
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewUtils.getDimenByRateWidth(80));
        params.leftMargin = ViewUtils.getDimenByRateWidth(25);

        ll_content.addView(
            generateSubView(keyVariable.getName(), "0.00", snapshot.getId(),
                keyVariable.getId()), params);
      }

      generateMediaView(ll_content,snapshot.getMedia());

    }
  }

  private void generateMediaView(LinearLayout ll_content, Snapshot.SnapshotMedia media) {

    if(media==null||media.getImage()==null||media.getImage().size()==0){
      return;
    }

    LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    ll_params.leftMargin=ViewUtils.getDimenByRateWidth(10);
    RelativeLayout mainView=new RelativeLayout(this);
    ll_content.addView(mainView,ll_params);

    RelativeLayout.LayoutParams params ;
    NineGridView nv_content = new NineGridView(this);
    params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.CENTER_VERTICAL);
    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    params.rightMargin = ViewUtils.getDimenByRateWidth(25);
    nv_content.setPadding(ViewUtils.getDimenByRateWidth(20), ViewUtils.getDimenByRateWidth(10),
        ViewUtils.getDimenByRateWidth(0), ViewUtils.getDimenByRateWidth(10));

    nv_content.setAdapter(new NineGridViewClickAdapter(this,media.genereateImageInfos()));
    nv_content.setSingleImageSize(ViewUtils.getDimenByRateWidth(60));
    mainView.addView(nv_content, params);

  }

  private String getSnapshotId(String workstatId) {

    if (currSnapshots != null) {
      for (Snapshot snapshot : currSnapshots) {
        if (snapshot.getWorkstatId().equals(workstatId)) {
          return snapshot.getWorkstatId();
        }
      }
    }

    return null;
  }

  public View generateSubView(String title, String value, String snapshotId, String variableId) {

    RelativeLayout mainView = new RelativeLayout(this);

    TextView tv_name = new TextView(this);
    RelativeLayout.LayoutParams params =
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.CENTER_VERTICAL);

    ViewUtils.adjustViewTextSize(tv_name, 25);
    tv_name.setTextColor(Color.BLACK);
    tv_name.setText(title);
    mainView.addView(tv_name, params);

    TextView tv_content = new TextView(this);
    params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.CENTER_VERTICAL);
    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    params.rightMargin = ViewUtils.getDimenByRateWidth(25);

    tv_content.setText(value);
    tv_content.setBackgroundResource(R.drawable.bg_workstat_item);
    tv_content.setTextColor(Color.WHITE);
    tv_content.setPadding(ViewUtils.getDimenByRateWidth(20), ViewUtils.getDimenByRateWidth(10),
        ViewUtils.getDimenByRateWidth(20), ViewUtils.getDimenByRateWidth(10));

    mainView.addView(tv_content, params);

    params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewUtils.getDimenByRateWidth(1));
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

    View divider = new View(this);
    divider.setBackgroundColor(getResources().getColor(R.color.divider_grey));

    mainView.addView(divider, params);

    loadSnapshotData(tv_content,snapshotId,variableId);

    return mainView;
  }

  private void loadSnapshotData(final TextView tv_content, final String snapshotId, String variableId) {

    new RestClient().getApiService()
        .
            querySnapshotData(new InfoBody(true).getHttpParams(), new SnapshotDataBody(snapshotId, variableId),
            new Callback<CommonListResult<HashMap<String, String>>>() {
              @Override
              public void success(CommonListResult<HashMap<String, String>> res,
                  Response response) {
                if(!res.isSuccess()||res.getData()==null||res.getData().size()<=0){
                  return;
                }

                String findRes=res.getData().get(0).get(snapshotId);
                tv_content.setText(TextUtils.isEmpty(findRes)?"0.00":findRes);

              }

              @Override public void failure(RetrofitError error) {
                error.printStackTrace();
              }
            });

  }

  private void initViews() {
    ViewUtils.adjustViewTextSize(tv_header_title, 38);

    ViewUtils.adjustViewTextSize(tv_tips_serialNumber, 28);
    ViewUtils.adjustViewTextSize(tv_serialNumber, 28);
    ViewUtils.adjustViewTextSize(tv_tips_exp_project, 28);
    ViewUtils.adjustViewTextSize(tv_exp_project, 28);

    ViewUtils.adjustViewTextSize(tv_tips_exp_furnace, 28);
    ViewUtils.adjustViewTextSize(tv_exp_furnace, 28);
    ViewUtils.adjustViewTextSize(tv_tips_exp_startTime, 28);
    ViewUtils.adjustViewTextSize(tv_exp_startTime, 28);
    ViewUtils.adjustViewTextSize(tv_tips_exp_duration, 28);
    ViewUtils.adjustViewTextSize(tv_exp_duration, 28);
    ViewUtils.adjustViewTextSize(tv_tips_exp_description, 28);
    ViewUtils.adjustViewTextSize(tv_exp_description, 28);
    ViewUtils.adjustViewTextSize(tv_tips_exp_approval, 28);
    ViewUtils.adjustViewTextSize(tv_exp_approval, 25);
    ViewUtils.adjustViewTextSize(tv_state, 25);
    ViewUtils.adjustViewTextSize(tv_furnace_info, 25);

    initMenuButtonDrawable(btn_chat);
    initMenuButtonDrawable(btn_rating);
    initMenuButtonDrawable(btn_realtime);
    initMenuButtonDrawable(btn_analysis);
  }

  private void initMenuButtonDrawable(Button btn) {
    ViewUtils.adjustViewTextSize(btn, 20);
    Drawable[] drawables = btn.getCompoundDrawables();
    drawables[1].setBounds(0, ViewUtils.getDimenByRateWidth(20), ViewUtils.getDimenByRateWidth(60),
        ViewUtils.getDimenByRateWidth(80));
    btn.setCompoundDrawables(null, drawables[1], null, null);
  }

  @OnClick(R.id.btn_header_camera) public void onCamera(){

    if (currExp==null||!currExp.getStatus().equals("running")) {
      Toasty.error(this, getString(R.string.exp_not_running_camera), Toast.LENGTH_SHORT).show();
      return;
    }

    ARouter.getInstance()
        .build(RouterConstant.CAMERA_MONITOR_URL)
        .withString("title", title)
        .withString("projectId",projectId)
        .withString("experimentId", experimentId)
        .navigation(this);

  }

  @OnClick(R.id.btn_header_back) public void onClose() {
    finish();
  }

  @OnClick(R.id.btn_chat) public void onMessage() {
    ARouter.getInstance()
        .build(RouterConstant.CHAT_URL)
        .withString("title", title)
        .withString("experimentId", experimentId)
        .navigation(this);
  }

  @OnClick(R.id.btn_rating) public void onRating() {

    if (!currExp.getStatus().equals("running")
        &&!currExp.getStatus().equals("pending")
        &&!currExp.getStatus().equals("finished")) {
      Toasty.error(this, getString(R.string.exp_not_running), Toast.LENGTH_SHORT).show();
      return;
    }

    ARouter.getInstance()
        .build(RouterConstant.EXPERIMENT_TARGET_EVALUATION)
        .withString("title", title)
        .withString("experimentId", experimentId)
        .navigation(this);
  }

  @OnClick(R.id.btn_realtime) public void onRealtime() {
    if (!currExp.getStatus().equals("running")) {
      Toasty.error(this, getString(R.string.exp_not_running), Toast.LENGTH_SHORT).show();
      return;
    }
    ARouter.getInstance()
        .build(RouterConstant.EXPERIMENT_REALTIME_DATA)
        .withString("title", title)
        .withString("experimentId", experimentId)
        .withBoolean("longTerm", longTerm)
        .navigation(this);
  }

  @OnClick(R.id.btn_analysis) public void onAnalysis() {
    if (!currExp.getStatus().equals("running")&&!currExp.getStatus().equals("pending")&&!currExp.getStatus().equals("finished")) {
      Toasty.error(this, getString(R.string.exp_not_running), Toast.LENGTH_SHORT).show();
      return;
    }
    if(currExp==null){
      Toasty.error(this, getString(R.string.exp_analysis_error_empty_experiment), Toast.LENGTH_SHORT).show();
      return;
    }
    if(currExp.getTargets()==null||currExp.getTargets().size()==0){
      Toasty.error(this, getString(R.string.exp_analysis_error_not_target), Toast.LENGTH_SHORT).show();
      return;
    }
    if(currExp.getWorkstat()==null){
      Toasty.error(this, getString(R.string.exp_analysis_error_not_workstat), Toast.LENGTH_SHORT).show();
      return;
    }
    if(currSnapshots==null||currSnapshots.size()==0){
      Toasty.error(this, getString(R.string.exp_analysis_error_not_snapshot), Toast.LENGTH_SHORT).show();
      return;
    }


    ARouter.getInstance()
        .build(RouterConstant.EXPERIMENT_ANALYSIS_DATA)
        .withString("title", title)
        .withString("experimentId", experimentId)
        .navigation(this);
  }

  @OnClick(R.id.tv_furnace_info) public void onFurnaceInfo() {
    if (currExp == null || currExp.getNozzles() == null || currExp.getNozzles().size() == 0) {
      return;
    }
    ARouter.getInstance()
        .build(RouterConstant.NOZZLE_INFO)
        .withString("title", title)
        .withString("experimentId", experimentId)
        .navigation(this);
  }

  private String getApprovalId() {
    String aid = "";
    for (Approval approval : currExp.getApprovals()) {
      if (approval.getApproverId().equals(BSApplication.mainUserId)) {
        aid = approval.getId();
        break;
      }
    }
    return aid;
  }

  @OnClick(R.id.btn_agree) public void onAgree() {

    String aid = getApprovalId();
    if (TextUtils.isEmpty(aid)) {

      Toasty.error(this, "操作错误，无权进行操作", Toast.LENGTH_SHORT).show();

      return;
    }

    postApproval(ExperimentApprovalBody.STATUS_AGREE, aid);
  }

  @OnClick(R.id.btn_disagree) public void onDisagree() {
    String aid = getApprovalId();
    if (TextUtils.isEmpty(aid)) {

      Toasty.error(this, "操作错误，无权进行操作", Toast.LENGTH_SHORT).show();

      return;
    }
    postApproval(ExperimentApprovalBody.STATUS_DISAGREE, aid);
  }

  public void postApproval(String status, String aid) {
    if (!NetworkUtils.isOnline(this)) {
      Toasty.error(this, getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
      return;
    }
    BProgressDialog.showProgressDialog(this);
    new RestClient().getApiService()
        .putExperimentApprovals(experimentId, aid, new InfoBody(true).getHttpParams(),
            new ExperimentApprovalBody(status), new Callback<CommonObjectResult<Object>>() {
              @Override public void success(CommonObjectResult<Object> res, Response response) {
                BProgressDialog.dismissProgressDialog();
                if (!res.isSuccess()) {
                  Toasty.error(ExperimentInfoActivity.this,
                      TextUtils.isEmpty(res.getError()) ? getString(R.string.post_info_failed)
                          : res.getError(), Toast.LENGTH_SHORT).show();
                  return;
                }
                Toasty.success(ExperimentInfoActivity.this, getString(R.string.post_info_success),
                    Toast.LENGTH_SHORT).show();
                rl_exp_approver.setVisibility(View.GONE);
              }

              @Override public void failure(RetrofitError error) {
                BProgressDialog.dismissProgressDialog();
                Toasty.error(ExperimentInfoActivity.this, getString(R.string.post_info_failed),
                    Toast.LENGTH_SHORT).show();
              }
            });
  }

  @Override protected void onResume() {
    super.onResume();
    if (!TextUtils.isEmpty(projectId)) {
      ExperimentListController.updateExperimentList(this, projectId);
    }
  }

  @OnClick(R.id.rl_header_back) public void onBack(View v) {
    finish();
  }

  private class PicassoImageLoader implements NineGridView.ImageLoader {
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
      Picasso.with(context).load(url)
          .placeholder(R.drawable.ic_default_image)
          .error(R.drawable.ic_default_image)
          .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
      return null;
    }
  }
}
