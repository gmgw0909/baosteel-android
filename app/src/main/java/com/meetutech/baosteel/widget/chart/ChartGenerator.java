package com.meetutech.baosteel.widget.chart;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.widget.chart
// Author: culm at 2017-08-25
//*********************************************************

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.model.http.Target;
import com.meetutech.baosteel.model.http.TargetContent;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.layout.FlowLayout;
import es.dmoral.toasty.Toasty;
import java.util.List;

public class ChartGenerator {
  private Activity activity;

  public ChartGenerator(Activity activity) {
    this.activity = activity;
  }

  public List<View> generate312DotPlotView(String experimentId, Target target,
      List<View> contentViews) {

    if (target == null || target.getContent() == null) {
      return contentViews;
    }

    TargetContent targetContent = target.getContent();

    for (int i = 0; i < targetContent.getPage().size(); i++) {

      String pageSize = targetContent.getPage().get(i);

      String[] gridSize = splitCellSize(pageSize, "x");

      if (gridSize == null) {
        continue;
      }

      View pageView = generateDotPlotView(targetContent, contentViews, i);

      if (pageView == null) {
        continue;
      }

      contentViews.add(pageView);
    }

    return contentViews;
  }

  private View generateDotPlotView(TargetContent targetContent, List<View> contentViews,
      int pageIndex) {

    ScrollView scrollView = new ScrollView(activity);
    scrollView.setPadding(ViewUtils.getDimenByRateWidth(20), ViewUtils.getDimenByRateWidth(0),
        ViewUtils.getDimenByRateWidth(10), ViewUtils.getDimenByRateWidth(20));

    FlowLayout mainView = new FlowLayout(activity);
    mainView.setLayoutParams(new ViewPager.LayoutParams());
    mainView.setGravity(Gravity.START | Gravity.TOP);
    scrollView.addView(mainView);

    TargetContent.Grid grid = targetContent.getGrid();
    List<List<String>> names = grid.getName().get(pageIndex);

    FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(ViewUtils.getDimenByRateWidth(335f),
        ViewUtils.getDimenByRateWidth(152));
    params.leftMargin=params.rightMargin=params.topMargin=params.bottomMargin=ViewUtils.getDimenByRateWidth(3);

    for(int i=0;i<names.size();i++){
      for (int j=0;j<names.get(i).size();j++){

        RelativeLayout subView=new RelativeLayout(activity);
        subView.setBackgroundColor(activity.getResources().getColor(R.color.common_section_grey));
        mainView.addView(subView,params);

        subView.setOnClickListener(new SubSectionClickListener(pageIndex,i,j));

        TextView tv_title=new TextView(activity);
        tv_title.setTextColor(Color.BLACK);
        tv_title.setTypeface(null, Typeface.BOLD);
        ViewUtils.adjustViewTextSizeLandscape(tv_title,60);
        tv_title.setText(names.get(i).get(j));
        RelativeLayout.LayoutParams rl_params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        rl_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rl_params.topMargin=ViewUtils.getDimenByRateWidth(15);
        subView.addView(tv_title,rl_params);

        TextView tv_gatherNums=new TextView(activity);
        tv_gatherNums.setTextColor(Color.GRAY);
        ViewUtils.adjustViewTextSizeLandscape(tv_gatherNums,45);
        tv_gatherNums.setText(String.format(activity.getString(R.string.txt_gather_num),getGatherCounts(targetContent.getCell().get(pageIndex).get(i).get(j))));
        rl_params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        rl_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rl_params.topMargin=ViewUtils.getDimenByRateWidth(50);
        subView.addView(tv_gatherNums,rl_params);

        TextView tv_max=new TextView(activity);
        tv_max.setTextColor(activity.getResources().getColor(R.color.light_blue));
        tv_max.setTypeface(null, Typeface.BOLD);
        tv_max.setText("Max：0.00");
        ViewUtils.adjustViewTextSizeLandscape(tv_max,55);
        rl_params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        rl_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rl_params.topMargin=ViewUtils.getDimenByRateWidth(80);
        subView.addView(tv_max,rl_params);


        TextView tv_min=new TextView(activity);
        tv_min.setTextColor(activity.getResources().getColor(R.color.light_blue));
        tv_min.setTypeface(null, Typeface.BOLD);
        tv_min.setText("Min：0.00");
        ViewUtils.adjustViewTextSizeLandscape(tv_min,55);
        rl_params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        rl_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rl_params.topMargin=ViewUtils.getDimenByRateWidth(100);
        subView.addView(tv_min,rl_params);


        TextView tv_avg=new TextView(activity);
        tv_avg.setTextColor(activity.getResources().getColor(R.color.light_blue));
        tv_avg.setTypeface(null, Typeface.BOLD);
        tv_avg.setText("Avg：0.00");
        ViewUtils.adjustViewTextSizeLandscape(tv_avg,55);
        rl_params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        rl_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rl_params.topMargin=ViewUtils.getDimenByRateWidth(120);
        subView.addView(tv_avg,rl_params);

      }
    }


    return scrollView;
  }

  private int getGatherCounts(List<List<String>> lists) {

    int count=0;

    for(List<String> row:lists){
      for(String cell:row){
        if(!TextUtils.isEmpty(cell)){
          count++;
        }
      }
    }

    return count;
  }

  public static String[] splitCellSize(String content, String reg) {
    return content.split("reg");
  }

  public class SubSectionClickListener implements View.OnClickListener {

    private int row;
    private int col;
    private int pageIndex;

    public SubSectionClickListener(int pageIndex,int row, int col) {
      this.row=row;
      this.col=col;
      this.pageIndex=pageIndex;
    }

    @Override public void onClick(View view) {
      Toasty.warning(activity,"操作失败！").show();
    }
  }
}
