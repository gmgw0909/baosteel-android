package com.meetutech.baosteel.ui.exp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.controller.TargetEvaluationController;
import com.meetutech.baosteel.model.http.ExperimentInfo;
import com.meetutech.baosteel.model.http.Target;
import com.meetutech.baosteel.model.http.TargetEvaluations;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.DBManager;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.layout.AbsPercentLinearLayout;
import com.meetutech.baosteel.widget.textview.TextViewLabel;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.info
// Author: culm at 2017-04-27
//*********************************************************
@Route(path = RouterConstant.EXPERIMENT_TARGET_EVALUATION) public class TargetEvaluationActivity
    extends BaseActivity {

  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.tv_header_back) TextView tv_header_back;
  public @BindView(R.id.ll_content) AbsPercentLinearLayout ll_content;

  public @Autowired(name = "title") String header_title;
  public @Autowired(name = "experimentId") String experimentId;

  public ExperimentInfo currExp;
  public String currUserId;
  private List<Target> currTargets;
  private List<CommentUIGroup> commentUIGroups;

  private TargetEvaluationController controller;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_target_evaluation);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    initViews();
    loadData();
  }

  private void loadData() {
    tv_header_title.setText(header_title);

    currUserId = (String) DBManager.getCache(CacheKeyConstant.AUTH_CURR_USER_ID);
    currExp =
        (ExperimentInfo) DBManager.getCache(CacheKeyConstant.EXPERIMENT_CACHE_ID + experimentId);
    if (currExp == null) {
      Toasty.error(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
      return;
    }
    currTargets = currExp.getTargets();
    commentUIGroups = new ArrayList<>(currTargets.size());
    generateEvalutionViews(currTargets);
  }

  private void generateEvalutionViews(List<Target> currTargets) {

    ll_content.removeAllViews();

    for (Target target : currTargets) {
      LinearLayout ll_item = new LinearLayout(this);
      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewUtils.getDimenByRateWidth(350));
      ll_item.setBackgroundColor(getResources().getColor(R.color.common_section_bg));
      ll_item.setOrientation(LinearLayout.VERTICAL);
      params.bottomMargin = ViewUtils.getDimenByRateWidth(30);
      ll_content.addView(ll_item, params);

      //针对指标生成对应评价View
      LinearLayout ll_top = new LinearLayout(this);
      params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT);

      ll_top.setOrientation(LinearLayout.HORIZONTAL);

      TextViewLabel tv_target_name = new TextViewLabel(this);
      tv_target_name.setTextColor(getResources().getColor(R.color.white));
      tv_target_name.setLabelColor(getResources().getColor(R.color.black));
      tv_target_name.setCornerRadius(ViewUtils.getDimenByRateHeight(5));
      tv_target_name.setPadding(ViewUtils.getDimenByRateWidth(10),
          ViewUtils.getDimenByRateWidth(10), ViewUtils.getDimenByRateWidth(10),
          ViewUtils.getDimenByRateWidth(10));
      tv_target_name.setTypeface(null, Typeface.BOLD);
      tv_target_name.setText(target.getName());
      LinearLayout.LayoutParams params_item =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      params_item.setMargins(ViewUtils.getDimenByRateWidth(15), 0,
          ViewUtils.getDimenByRateWidth(15), ViewUtils.getDimenByRateWidth(0));

      ll_item.addView(ll_top, params);

      ll_top.addView(tv_target_name, params_item);

      //Button Post
      Button btn_post = new Button(this);
      params_item = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewUtils.getDimenByRateWidth(50));
      params_item.leftMargin = ViewUtils.getDimenByRateWidth(50);
      btn_post.setVisibility(View.GONE);
      btn_post.setTextColor(Color.WHITE);
      btn_post.setBackgroundResource(R.drawable.bg_simple_btn_blue);
      btn_post.setPadding(ViewUtils.getDimenByRateWidth(10), 0, ViewUtils.getDimenByRateWidth(10),
          0);
      btn_post.setText(getString(R.string.btn_post));
      ViewUtils.adjustViewTextSize(btn_post, ViewUtils.getDimenByRateWidth(25));
      ll_top.addView(btn_post, params_item);

      SimpleRatingBar ratingBar = new SimpleRatingBar(this);
      ratingBar.setNumberOfStars(5);
      ratingBar.setStarSize(ViewUtils.getDimenByRateWidth(55));
      ratingBar.setStepSize(1);
      ratingBar.setDrawBorderEnabled(true);
      ratingBar.setStarsSeparation(ViewUtils.getDimenByRateWidth(15));
      ratingBar.setGravity(SimpleRatingBar.Gravity.Left);
      ratingBar.setBorderColor(getResources().getColor(R.color.rating_border_color));
      ratingBar.setFillColor(getResources().getColor(R.color.rating_fill_color));
      ratingBar.setOnRatingBarChangeListener(new OnRatingChangeListener(target.getId()));
      params_item = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.WRAP_CONTENT);
      params_item.gravity = Gravity.CENTER_HORIZONTAL;
      params_item.bottomMargin = ViewUtils.getDimenByRateWidth(20);
      params_item.topMargin = ViewUtils.getDimenByRateWidth(20);

      //评论框
      EditText editText = new EditText(this);
      editText.setBackgroundResource(R.drawable.bg_evalution_edittext);
      editText.setMaxLines(3);
      editText.setPadding(ViewUtils.getDimenByRateWidth(5), 0, ViewUtils.getDimenByRateWidth(5), 0);
      editText.setTextColor(getResources().getColor(R.color.black));
      editText.setCursorVisible(true);
      editText.setGravity(Gravity.LEFT | Gravity.TOP);
      ViewUtils.adjustViewTextSize(editText, 28);
      LinearLayout.LayoutParams params_item_et =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewUtils.getDimenByRateWidth(150));
      params_item_et.rightMargin = params_item_et.leftMargin = ViewUtils.getDimenByRateWidth(15);

      editText.setOnKeyListener(new OnCommonTextChangeListener(target.getId()));

      CommentUIGroup c =
          new CommentUIGroup(controller, target.getId(), tv_target_name, btn_post, editText,
              ratingBar);
      c.setCurrUserId(currUserId);
      for (TargetEvaluations evaluation : target.getEvaluations()) {
        if (evaluation.getSenderId().equals(currUserId)) {
          c.setEvaluationId(evaluation.getId());
          ratingBar.setRating(evaluation.getRating());
          editText.setText(evaluation.getDescription());
          break;
        }
      }
      ll_item.addView(ratingBar, params_item);
      ll_item.addView(editText, params_item_et);

      commentUIGroups.add(c);
    }
  }

  public CommentUIGroup getCurrUIGroup(String targetId) {
    CommentUIGroup commentUIGroup = null;

    for (CommentUIGroup c : commentUIGroups) {
      if (c.getTargetId().equals(targetId)) {
        commentUIGroup = c;
        break;
      }
    }

    return commentUIGroup;
  }

  private void initViews() {
    ViewUtils.adjustViewTextSize(tv_header_back, 38);
    ViewUtils.adjustViewTextSize(tv_header_title, 35);
    controller = new TargetEvaluationController(this);
  }

  private class OnRatingChangeListener implements SimpleRatingBar.OnRatingBarChangeListener {

    private String currTargetId;

    public OnRatingChangeListener(String currTargetId) {
      this.currTargetId = currTargetId;
    }

    @Override public void onRatingChanged(SimpleRatingBar simpleRatingBar, float v, boolean b) {
      showPostButton(currTargetId);
    }
  }

  private class OnCommonTextChangeListener implements EditText.OnKeyListener {

    private String currTargetId;

    public OnCommonTextChangeListener(String currTargetId) {
      this.currTargetId = currTargetId;
    }

    public String getCurrTargetId() {
      return currTargetId;
    }

    @Override public boolean onKey(View view, int i, KeyEvent keyEvent) {

      showPostButton(currTargetId);
      return false;
    }
  }

  private void showPostButton(String currTargetId) {

    CommentUIGroup c = getCurrUIGroup(currTargetId);
    if (c == null) {
      return;
    }
    c.getBtn_post().setVisibility(View.VISIBLE);
  }

  public static class CommentUIGroup {
    private TextViewLabel tv_target_name;
    private Button btn_post;
    private EditText et_comment;
    private SimpleRatingBar ratingBar;

    private String targetId;
    private String evaluationId;
    private String currUserId;
    private TargetEvaluationController controller;

    public CommentUIGroup(TargetEvaluationController controller, String targetId,
        TextViewLabel tv_target_name, Button btn_post, EditText et_comment,
        SimpleRatingBar ratingBar) {
      this.controller = controller;
      this.targetId = targetId;
      this.tv_target_name = tv_target_name;
      this.btn_post = btn_post;
      this.et_comment = et_comment;
      this.ratingBar = ratingBar;
      initPostEvent();
    }

    public String getCurrUserId() {
      return currUserId;
    }

    public void setCurrUserId(String currUserId) {
      this.currUserId = currUserId;
    }

    public void setEvaluationId(String evaluationId) {
      this.evaluationId = evaluationId;
    }

    private void initPostEvent() {
      btn_post.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (TextUtils.isEmpty(evaluationId)) {
            controller.postComment(CommentUIGroup.this, currUserId, targetId, ratingBar.getRating(),
                et_comment.getText().toString(), btn_post);
          } else {
            controller.putComment(evaluationId, currUserId, targetId, ratingBar.getRating(),
                et_comment.getText().toString(), btn_post);
          }
        }
      });
    }

    public TextViewLabel getTv_target_name() {
      return tv_target_name;
    }

    public Button getBtn_post() {
      return btn_post;
    }

    public EditText getEt_comment() {
      return et_comment;
    }

    public SimpleRatingBar getRatingBar() {
      return ratingBar;
    }

    public String getTargetId() {
      return targetId;
    }
  }

  @OnClick(R.id.rl_header_back) public void onBackClick() {
    finish();
  }
}
