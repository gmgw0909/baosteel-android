package com.meetutech.baosteel.ui.exp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.adapter.CommonInfoViewPageAdapter;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.controller.ExperimentAnalysisController;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.AnalysisDataBody;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.body.SnapshotDataBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.http.result.VariableData;
import com.meetutech.baosteel.model.http.Curve;
import com.meetutech.baosteel.model.http.ExperimentInfo;
import com.meetutech.baosteel.model.http.FurnaceVariable;
import com.meetutech.baosteel.model.http.Power;
import com.meetutech.baosteel.model.http.PowerGroup;
import com.meetutech.baosteel.model.http.PowerType;
import com.meetutech.baosteel.model.http.Snapshot;
import com.meetutech.baosteel.model.http.Target;
import com.meetutech.baosteel.model.http.TargetContent;
import com.meetutech.baosteel.model.http.WorkCondition;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.AppDBUtils;
import com.meetutech.baosteel.utils.DBManager;
import com.meetutech.baosteel.utils.FurnaceVariableUtils;
import com.meetutech.baosteel.utils.NetworkUtils;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import me.relex.circleindicator.CircleIndicator;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.info
// Author: culm at 2017-04-27
//*********************************************************
@Route(path = RouterConstant.EXPERIMENT_ANALYSIS_DATA) public class ExperimentAnalysisDataActivity
    extends BaseActivity {

  private final static String VIEWTAG_LINECHART = "lineChart";
  private final static String VIEWTAG_BARCHART = "barChart";
  private final static String VIEWTAG_PIECHART = "pieChart";

  private final static String VIEWTAG_STYLEBAR = "styleBar";
  private final static String VIEWTAG_STYLECURVE = "styleCurve";
  private final static String VIEWTAG_STYLEPIE = "stylePie";
  private static final String VIEWTAG_PIESCROLLVIEW = "pieScrollview";

  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.tv_header_back) TextView tv_header_back;
  public @BindView(R.id.vp_content) ViewPager vp_content;
  public @BindView(R.id.indicator) CircleIndicator indicator;

  public @Autowired(name = "title") String header_title;
  public @Autowired(name = "experimentId") String experimentId;

  private ExperimentInfo currExp;
  private List<Target> currTargets;
  private List<View> pageViews;
  private CommonInfoViewPageAdapter vp_adapter;
  private HashMap<String, CommonChartGroup> commonChartGroups;
  private ExperimentAnalysisController _controller;
  private List<TableVariable> tableSubViews;

  private List<CommonChartLineView> commonChartLineViews;
  private ArrayList<ILineDataSet> uilines;
  private PowerGroup currPowerGroup;
  private List<FilterGroup> filterGroupsUI;
  private HashMap<String, FilterButtons> filterButtonsHashMap;

  private List<Snapshot> snapshotList;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_analysis_data);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    _controller = new ExperimentAnalysisController(this, experimentId);
    filterButtonsHashMap = new HashMap<>();
    initViews();
    loadData();
    loadNetworkData();
  }

  @OnClick(R.id.btn_chat) public void onChat(View v) {
    ARouter.getInstance()
        .build(RouterConstant.CHAT_URL)
        .withString("title", header_title)
        .withString("experimentId", experimentId)
        .navigation(this);
  }

  private void loadNetworkData() {

    if (!NetworkUtils.isOnline(this)) {
      Toasty.error(this, getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
      return;
    }
    _controller.refreshExpSnapshots();
  }

  private void generateViewPager(List<Target> currTargets) {
    vp_content.setVisibility(
        currTargets == null || currTargets.size() == 0 ? View.GONE : View.VISIBLE);
    pageViews = generatePageViews(currTargets);
    vp_adapter = new CommonInfoViewPageAdapter(this, pageViews);
    vp_content.setAdapter(vp_adapter);
    indicator.setViewPager(vp_content);
    vp_adapter.registerDataSetObserver(indicator.getDataSetObserver());
  }

  private List<View> generatePageViews(List<Target> currTargets) {

    filterGroupsUI = new ArrayList<>();
    List<View> contentViews = new ArrayList<>(currTargets.size());
    commonChartGroups = new HashMap<>();
    commonChartLineViews = new ArrayList<>();
    for (Target target : currTargets) {

      if(!target.isShowAnalyse()){
        continue;
      }

      TargetContent targetContent = target.getContent();

      if (targetContent == null || !target.isShowAnalyse()) {
        continue;
      }
      View chart = null;
      if (targetContent.getTable() != null) {//表格模板
        chart = generateAnalysisView(target);
      } else if (targetContent.getSurface() != null) {//曲面模板
      } else if (targetContent.getCurve() != null) {//曲线模板
        chart = generateAnalysisView(target);
      } else {

      }
      if (chart != null) {
        contentViews.add(chart);
        CommonChartGroup ccg = new CommonChartGroup(experimentId, target, chart);
        commonChartGroups.put(target.getId(), ccg);
      }
    }
    return contentViews;
  }

  private View generateAnalysisView(Target target) {

    FilterGroup filterGroup = new FilterGroup(target, currPowerGroup);

    RelativeLayout mainVew = new RelativeLayout(this);

    ScrollView scrollView = new ScrollView(this);
    RelativeLayout.LayoutParams params =
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    params.topMargin = ViewUtils.getDimenByRateWidth(20);
    scrollView.setTag(ExperimentAnalysisDataActivity.VIEWTAG_PIESCROLLVIEW);
    mainVew.addView(scrollView, params);

    LinearLayout pieLayout = new LinearLayout(this);
    ScrollView.LayoutParams sv_params =
        new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    params.topMargin = ViewUtils.getDimenByRateWidth(20);
    pieLayout.setOrientation(LinearLayout.VERTICAL);
    pieLayout.setTag(ExperimentAnalysisDataActivity.VIEWTAG_PIECHART);
    scrollView.addView(pieLayout, sv_params);
    pieLayout.setVisibility(View.GONE);

    LineChartView lineChart = new LineChartView(this);
    params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    params.topMargin = ViewUtils.getDimenByRateWidth(20);
    mainVew.addView(lineChart, params);
    lineChart.setInteractive(true);
    lineChart.setZoomEnabled(false);
    lineChart.setContainerScrollEnabled(false, null);
    lineChart.setTag(ExperimentAnalysisDataActivity.VIEWTAG_LINECHART);

    lineChart = initLineChartXAxis(lineChart);
    lineChart.setLineChartData(null);

    ColumnChartView columnChartView = new ColumnChartView(this);
    params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    params.topMargin = ViewUtils.getDimenByRateWidth(20);
    mainVew.addView(columnChartView, params);
    columnChartView.setInteractive(true);
    columnChartView.setZoomEnabled(false);
    columnChartView.setContainerScrollEnabled(false, null);
    columnChartView.setTag(ExperimentAnalysisDataActivity.VIEWTAG_BARCHART);

    columnChartView = initBarChartXAxis(columnChartView);
    columnChartView.setColumnChartData(null);
    columnChartView.setVisibility(View.GONE);

    //添加Button
    final Button btn_filter = new Button(this);
    btn_filter.setTextColor(Color.WHITE);
    btn_filter.setBackgroundColor(getResources().getColor(R.color.common_grey));
    btn_filter.setText("筛选");
    params = new RelativeLayout.LayoutParams(ViewUtils.getDimenByRateWidth(55),
        ViewUtils.getDimenByRateWidth(75));
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    params.rightMargin = ViewUtils.getDimenByRateWidth(150);
    mainVew.addView(btn_filter, params);

    final Button btn_style_pie = new Button(this);
    btn_style_pie.setBackgroundResource(R.drawable.pie_chart_icon);
    params = new RelativeLayout.LayoutParams(ViewUtils.getDimenByRateWidth(50),
        ViewUtils.getDimenByRateWidth(50));
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    params.rightMargin = ViewUtils.getDimenByRateWidth(155);
    params.bottomMargin = ViewUtils.getDimenByRateWidth(80);

    btn_style_pie.setTag(VIEWTAG_STYLEPIE);
    btn_style_pie.setOnClickListener(new StyleSwitchClickListener(mainVew,target));

    mainVew.addView(btn_style_pie, params);

    final Button btn_style_bar = new Button(this);
    btn_style_bar.setBackgroundResource(R.drawable.bar_chart_icon);
    params = new RelativeLayout.LayoutParams(ViewUtils.getDimenByRateWidth(50),
        ViewUtils.getDimenByRateWidth(50));
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    params.rightMargin = ViewUtils.getDimenByRateWidth(155);
    params.bottomMargin = ViewUtils.getDimenByRateWidth(137);

    btn_style_bar.setTag(VIEWTAG_STYLEBAR);
    btn_style_bar.setOnClickListener(new StyleSwitchClickListener(mainVew,target));

    mainVew.addView(btn_style_bar, params);

    final Button btn_style_curve = new Button(this);
    btn_style_curve.setBackgroundResource(R.drawable.curve_chart_icon);
    params = new RelativeLayout.LayoutParams(ViewUtils.getDimenByRateWidth(50),
        ViewUtils.getDimenByRateWidth(50));
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    params.rightMargin = ViewUtils.getDimenByRateWidth(155);
    params.bottomMargin = ViewUtils.getDimenByRateWidth(192);

    btn_style_curve.setTag(VIEWTAG_STYLECURVE);
    btn_style_curve.setOnClickListener(new StyleSwitchClickListener(mainVew,target));

    mainVew.addView(btn_style_curve, params);

    final ScrollView sv_powerTypes = new ScrollView(this);
    params = new RelativeLayout.LayoutParams(ViewUtils.getDimenByRateWidth(150),
        ViewGroup.LayoutParams.MATCH_PARENT);
    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    sv_powerTypes.setBackgroundResource(R.drawable.bg_slidingbar);

    mainVew.addView(sv_powerTypes, params);

    LinearLayout linearLayout = generateTagView(target, currPowerGroup, filterGroup);
    ScrollView.LayoutParams layoutParams =
        new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.bottomMargin = ViewUtils.getDimenByRateWidth(50);
    sv_powerTypes.addView(linearLayout, layoutParams);

    btn_filter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        RelativeLayout.LayoutParams ll_params =
            (RelativeLayout.LayoutParams) btn_filter.getLayoutParams();
        if (sv_powerTypes.getVisibility() == View.GONE) {
          sv_powerTypes.setVisibility(View.VISIBLE);
          ll_params.rightMargin = ViewUtils.getDimenByRateWidth(150);
          fixStyleButtonLayout(btn_style_bar, false);
          fixStyleButtonLayout(btn_style_pie, false);
          fixStyleButtonLayout(btn_style_curve, false);
        } else {
          sv_powerTypes.setVisibility(View.GONE);
          ll_params.rightMargin = 0;
          fixStyleButtonLayout(btn_style_bar, true);
          fixStyleButtonLayout(btn_style_pie, true);
          fixStyleButtonLayout(btn_style_curve, true);
        }
        btn_filter.setLayoutParams(ll_params);
      }
    });

    filterGroupsUI.add(filterGroup);

    return mainVew;
  }

  private void fixStyleButtonLayout(Button btn, boolean isShow) {
    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btn.getLayoutParams();
    params.rightMargin = isShow ? 0 : ViewUtils.getDimenByRateWidth(150);
    btn.setLayoutParams(params);
  }

  private ColumnChartView initBarChartXAxis(ColumnChartView barChart) {

    ArrayList<AxisValue> axisValuesX = new ArrayList<AxisValue>();

    for (int i = 0; i < 10; i++) {
      axisValuesX.add(new AxisValue(i).setLabel(String.format("%.1f", i + 0.0f)));
    }

    Axis axisX = new Axis();
    axisX.setName("主管路煤气总流量");
    axisX.setValues(axisValuesX);
    axisX.setTextSize(ViewUtils.getDimenByRateWidth(14));
    axisX.setTypeface(Typeface.DEFAULT);
    axisX.setHasTiltedLabels(false);
    axisX.setHasLines(false);
    axisX.setHasSeparationLine(true);
    axisX.setInside(false);

    ColumnChartData chartData = new ColumnChartData();//将线的集合设置为折线图的数据
    chartData.setAxisXBottom(axisX);// 将X轴属性设置到底部
    chartData.setBaseValue(20);// 设置反向覆盖区域颜色
    chartData.setValueLabelBackgroundAuto(false);// 设置数据背景是否跟随节点颜色
    chartData.setValueLabelBackgroundColor(Color.BLUE);// 设置数据背景颜色
    chartData.setValueLabelBackgroundEnabled(false);// 设置是否有数据背景
    chartData.setValueLabelsTextColor(Color.BLACK);// 设置数据文字颜色
    chartData.setValueLabelTextSize(15);// 设置数据文字大小
    chartData.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式
    barChart.setColumnChartData(chartData);
    return barChart;
  }


  private LineChartView initLineChartXAxis(LineChartView lineChart) {

    ArrayList<AxisValue> axisValuesX = new ArrayList<AxisValue>();

    for (int i = 0; i < 10; i++) {
      axisValuesX.add(new AxisValue(i).setLabel(String.format("%.1f", i + 0.0f)));
    }

    Axis axisX = new Axis();
    axisX.setName("主管路煤气总流量");
    axisX.setValues(axisValuesX);
    axisX.setTextSize(ViewUtils.getDimenByRateWidth(14));
    axisX.setTypeface(Typeface.DEFAULT);
    axisX.setHasTiltedLabels(false);
    axisX.setHasLines(false);
    axisX.setHasSeparationLine(true);
    axisX.setInside(false);

    LineChartData chartData = new LineChartData();//将线的集合设置为折线图的数据
    chartData.setAxisXBottom(axisX);// 将X轴属性设置到底部
    chartData.setBaseValue(20);// 设置反向覆盖区域颜色
    chartData.setValueLabelBackgroundAuto(false);// 设置数据背景是否跟随节点颜色
    chartData.setValueLabelBackgroundColor(Color.BLUE);// 设置数据背景颜色
    chartData.setValueLabelBackgroundEnabled(false);// 设置是否有数据背景
    chartData.setValueLabelsTextColor(Color.BLACK);// 设置数据文字颜色
    chartData.setValueLabelTextSize(15);// 设置数据文字大小
    chartData.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式
    lineChart.setLineChartData(chartData);
    return lineChart;
  }

  private LinearLayout generateTagView(Target target, PowerGroup currPowerGroup,
      FilterGroup filterGroup) {

    LinearLayout mainView = new LinearLayout(this);
    mainView.setOrientation(LinearLayout.VERTICAL);
    FilterButtons filterButtons = new FilterButtons();
    List<Button> btn_var = new ArrayList<>();

    if (target.getContent() != null && target.getContent().getTable() != null) {

      TextView tv_title = new TextView(this);
      tv_title.setTextColor(Color.BLACK);
      tv_title.setText("变量");
      ViewUtils.adjustViewTextSizeLandscape(tv_title, 45);
      tv_title.setGravity(Gravity.CENTER_VERTICAL);
      tv_title.setPadding(ViewUtils.getDimenByRateWidth(5), 0, 0, 0);
      tv_title.setBackgroundColor(Color.GRAY);

      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewUtils.getDimenByRateWidth(40));
      mainView.addView(tv_title, params);

      TargetContent.TargetTable targetTable = target.getContent().getTable();
      for (TargetContent.TargetTable.TableData tableData : targetTable.getData()) {
        Button btn_variable = new Button(this);
        btn_variable.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        btn_variable.setPadding(ViewUtils.getDimenByRateWidth(6), 0, 0, 0);
        btn_variable.setTextColor(getResources().getColor(R.color.black));
        btn_variable.setText(tableData.getTitle());
        btn_variable.setOnClickListener(new AnalysisDataClickListener(filterGroup, target,
            AnalysisDataClickListener.TYPE_VARIABLE, tableData.getVariableId()));
        btn_variable.setBackgroundResource(R.drawable.bg_selector_label);
        ViewUtils.adjustViewTextSizeLandscape(btn_variable, 38);
        params = new LinearLayout.LayoutParams(ViewUtils.getDimenByRateWidth(120),
            ViewUtils.getDimenByRateHeight(60));
        params.leftMargin = params.rightMargin = ViewUtils.getDimenByRateWidth(15);
        params.topMargin = params.bottomMargin = ViewUtils.getDimenByRateWidth(5);
        mainView.addView(btn_variable, params);
        btn_var.add(btn_variable);
      }
    } else if (target.getContent().getCurve() != null) {
      TextView tv_title = new TextView(this);
      tv_title.setTextColor(Color.BLACK);
      tv_title.setText("变量");
      ViewUtils.adjustViewTextSizeLandscape(tv_title, 45);
      tv_title.setGravity(Gravity.CENTER_VERTICAL);
      tv_title.setPadding(ViewUtils.getDimenByRateWidth(5), 0, 0, 0);
      tv_title.setBackgroundColor(Color.GRAY);

      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewUtils.getDimenByRateWidth(40));
      mainView.addView(tv_title, params);

      List<Curve> curves = target.getContent().getCurve();
      for (Curve curve : curves) {
        Button btn_variable = new Button(this);
        btn_variable.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        btn_variable.setPadding(ViewUtils.getDimenByRateWidth(6), 0, 0, 0);
        btn_variable.setTextColor(getResources().getColor(R.color.black));
        btn_variable.setText(FurnaceVariableUtils.getFurnaceVariableName(curve.getRealtime_y()));
        btn_variable.setOnClickListener(new AnalysisDataClickListener(filterGroup, target,
            AnalysisDataClickListener.TYPE_VARIABLE, curve.getRealtime_y()));
        btn_variable.setBackgroundResource(R.drawable.bg_selector_label);
        ViewUtils.adjustViewTextSizeLandscape(btn_variable, 38);
        params = new LinearLayout.LayoutParams(ViewUtils.getDimenByRateWidth(120),
            ViewUtils.getDimenByRateHeight(60));
        params.leftMargin = params.rightMargin = ViewUtils.getDimenByRateWidth(15);
        params.topMargin = params.bottomMargin = ViewUtils.getDimenByRateWidth(5);
        mainView.addView(btn_variable, params);
        btn_var.add(btn_variable);
      }
    }
    List<List<Button>> btn_types = new ArrayList<>();
    for (PowerType type : currPowerGroup.getTypes()) {
      List<Button> buttonsType = new ArrayList<>();
      TextView tv_title = new TextView(this);
      tv_title.setTextColor(Color.BLACK);
      tv_title.setText(type.getName());
      tv_title.setGravity(Gravity.CENTER_VERTICAL);
      tv_title.setPadding(ViewUtils.getDimenByRateWidth(5), 0, 0, 0);
      tv_title.setBackgroundColor(Color.GRAY);
      ViewUtils.adjustViewTextSizeLandscape(tv_title, 40);

      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewUtils.getDimenByRateWidth(40));
      mainView.addView(tv_title, params);

      for (Power power : type.getPowers()) {
        Button btn_powerType = new Button(this);
        btn_powerType.setText(power.getName());
        btn_powerType.setBackgroundResource(R.drawable.bg_selector_label);
        btn_powerType.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        btn_powerType.setPadding(ViewUtils.getDimenByRateWidth(6), 0, 0, 0);
        btn_powerType.setTextColor(Color.BLACK);
        btn_powerType.setOnClickListener(
            new AnalysisDataClickListener(filterGroup, target, AnalysisDataClickListener.TYPE_POWER,
                power.getId(), type.getId()));
        ViewUtils.adjustViewTextSizeLandscape(btn_powerType, 38);
        params = new LinearLayout.LayoutParams(ViewUtils.getDimenByRateWidth(120),
            ViewUtils.getDimenByRateHeight(50));
        params.topMargin =
            params.leftMargin = params.rightMargin = ViewUtils.getDimenByRateWidth(15);
        mainView.addView(btn_powerType, params);
        buttonsType.add(btn_powerType);
      }
      btn_types.add(buttonsType);
    }

    filterButtons.setBtn_powers(btn_types);
    filterButtons.setBtn_var(btn_var);
    filterButtonsHashMap.put(target.getId(), filterButtons);

    return mainView;
  }

  public class AnalysisDataClickListener implements View.OnClickListener {

    public static final String TYPE_POWER = "type_power";
    public static final String TYPE_VARIABLE = "type_variable";

    public static final int SELECTED_DRAWABLE = R.drawable.bg_selector_label_ed;
    public static final int UNSELECTED_DRAWABLE = R.drawable.bg_selector_label;

    private String type;
    private String id;
    private String parentId;
    private boolean selected;
    private String targetId;
    private Target target;
    private FilterGroup filterGroup;

    public AnalysisDataClickListener(FilterGroup filterGroup, Target target, String type, String id,
        String... parentIds) {
      this.type = type;
      this.id = id;
      this.filterGroup = filterGroup;
      this.targetId = target.getId();
      this.target = target;
      if (parentIds != null && parentIds.length > 0) {
        this.parentId = parentIds[0];
      }
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    @Override public void onClick(View view) {
      //if (!AppConstant.DEBUG) {
      //  Toasty.error(ExperimentAnalysisDataActivity.this, "系统兼容问题，无法执行操作！", Toast.LENGTH_SHORT)
      //      .show();
      //  return;
      //}
      view.setBackgroundResource(selected ? UNSELECTED_DRAWABLE : SELECTED_DRAWABLE);
      view.setTag(selected ? null : id);
      ((Button) view).setTextColor(selected ? Color.BLACK : Color.WHITE);
      selected = !selected;

      if (getType().equals(TYPE_POWER)) {
        filterGroup.updateTypeState(parentId, id, selected);
      } else {
        filterGroup.updateTargetState(selected);
      }

      FilterButtons filterButtons = filterButtonsHashMap.get(targetId);
      CommonChartGroup ccg = commonChartGroups.get(targetId);
      if (filterButtons == null) {
        return;
      }

      final LineChartView chartView = (LineChartView) ccg.getChartView()
          .findViewWithTag(ExperimentAnalysisDataActivity.VIEWTAG_LINECHART);

      final ColumnChartView barView = (ColumnChartView) ccg.getChartView()
          .findViewWithTag(ExperimentAnalysisDataActivity.VIEWTAG_BARCHART);

      if (chartView == null || barView == null) {
        return;
      }

      int countAnalysis =
          _controller.countAnalysisLines(filterButtons.getBtn_var(), filterButtons.getBtn_powers());

      if (countAnalysis == 0) {
        chartView.setLineChartData(null);
      }

      final AnalysisDataBody body =
          _controller.generateQueryData(filterButtons.getBtn_var(), filterButtons.getBtn_powers());

      if (body != null) {

        final List<SnapshotDataBody> bodyList = new ArrayList<>();

        for (AnalysisDataBody.Data d : body.getData()) {
          bodyList.add(new SnapshotDataBody(d.getSnapshotids(), d.getVarableId()));
        }

        requestCount = 0;
        lineData = new ArrayList<>();

        for (final SnapshotDataBody snapshotDataBody : bodyList) {
          new RestClient().getApiService()
              .querySnapshotData(new InfoBody(true).getHttpParams(), snapshotDataBody,
                  new Callback<CommonListResult<HashMap<String, String>>>() {
                    @Override public void success(CommonListResult<HashMap<String, String>> res,
                        Response response) {
                      if (!res.isSuccess() || res.getData() == null || res.getData().size() == 0) {
                        return;
                      }
                      lineData.add(res.getData().get(0));

                      requestCount++;
                      if (requestCount == bodyList.size()) {
                        refreshCurveChart(chartView);
                        refreshBarChart(barView);
                      }
                    }

                    @Override public void failure(RetrofitError error) {

                    }
                  });
        }
      } else {
        //Toasty.warning(ExperimentAnalysisDataActivity.this, "数据分析为空！", Toast.LENGTH_SHORT).show();
      }
    }

    private void refreshBarChart(ColumnChartView chartView) {
      String analysisXid = target.getAnalysisAliasX();
      //start render data

      //set X Y axis
      Axis axisY = new Axis().setHasLines(true);// Y轴属性
      Axis axisX = new Axis();// X轴属性
      axisY.setName("kPa");//设置Y轴显示名称

      FurnaceVariable furnaceVariable=AppDBUtils.getFurnaceVariablesById(analysisXid);

      if(furnaceVariable!=null) {
        axisX.setName(furnaceVariable.getName());//设置X轴显示名称
      }
      ArrayList<AxisValue> axisValuesX = new ArrayList<AxisValue>();//定义X轴刻度值的数据集合
      ArrayList<AxisValue> axisValuesY = new ArrayList<AxisValue>();//定义Y轴刻度值的数据集合
      axisX.setValues(axisValuesX);//为X轴显示的刻度值设置数据集合
      axisX.setLineColor(Color.BLACK);// 设置X轴轴线颜色
      axisY.setLineColor(Color.BLACK);// 设置Y轴轴线颜色
      axisX.setTextColor(Color.BLACK);// 设置X轴文字颜色
      axisY.setTextColor(Color.BLACK);// 设置Y轴文字颜色
      axisY.setHasLines(true);
      axisY.setHasSeparationLine(false);
      axisY.setAutoGenerated(false);
      axisX.setHasLines(true);
      axisX.setHasSeparationLine(false);
      axisX.setAutoGenerated(false);
      axisX.setTextSize(14);// 设置X轴文字大小
      axisX.setTypeface(Typeface.DEFAULT);// 设置文字样式，此处为默认
      axisX.setHasTiltedLabels(false);// 设置X轴文字向左旋转45度
      axisX.setHasLines(false);// 是否显示X轴网格线
      axisY.setHasLines(false);// 是否显示Y轴网格线
      axisX.setHasSeparationLine(true);// 设置是否有分割线
      axisX.setInside(false);// 设置X轴文字是否在X轴内部

      List<Column> lines = new ArrayList<Column>();

      for (int i = 0; i < requestCount; i++) {
        List<SubcolumnValue> values = new ArrayList<SubcolumnValue>();

        for (Map.Entry<String, String> entry : lineData.get(i).entrySet()) {
          if (entry.getKey().equals("varableId")) {
            continue;
          }

          Snapshot currSnapshot = _controller.getSnapshotById(entry.getKey());

          //search x

          if (currSnapshot.getWorkstat().getCondition() == null) {
            continue;
          }
          float xPoint = 0.0f;
          for (WorkCondition condition : currSnapshot.getWorkstat().getCondition()) {
            if (condition.getVid().equals(analysisXid)) {
              xPoint = Float.parseFloat(condition.getValue()
                  .get(condition.getValue().size() - 1 < 0 ? 0 : condition.getValue().size() - 1));
              break;
            }
          }
          axisValuesX.add(new AxisValue(xPoint));
          SubcolumnValue pointValue = new SubcolumnValue(Float.parseFloat(entry.getValue()),
              Color.parseColor(filterColors[i]));
          values.add(pointValue);
        }
        Collections.sort(values, new Comparator<SubcolumnValue>() {
          @Override public int compare(SubcolumnValue p1, SubcolumnValue p2) {
            return (int) (p1.getValue() - p2.getValue());
          }
        });

        for (int j = 0; j < 2000; j += 200) {//循环为节点、X、Y轴添加数据
          axisValuesY.add(
              new AxisValue(j + 0.0f).setLabel(String.format("%.1f", j + 0.0f)));// 添加Y轴显示的刻度值
        }

        Column line = new Column(values);
        lines.add(line);
      }

      List<Column> rightLines = new ArrayList<Column>();
      Column rightColunm = new Column();
      List<SubcolumnValue> rightValue = new ArrayList<SubcolumnValue>();


      int maxSize = lines.get(0).getValues().size();

      for (Column column : lines) {
        int currSize = column.getValues().size();
        maxSize = currSize < maxSize ? maxSize : currSize;
      }

      for (int subIndex = 0; subIndex < maxSize; subIndex++) {

        for (int index = 0; index < lines.size(); index++) {
          List<SubcolumnValue> subcolumnValues=lines.get(index).getValues();
          if(subcolumnValues.size()-1<subIndex){
            continue;
          }
          rightValue.add(subcolumnValues.get(subIndex));
        }
      }

      rightColunm.setValues(rightValue);

      rightLines.add(rightColunm);

      ColumnChartData data = new ColumnChartData(rightLines);
      data.setAxisYLeft(axisY);
      data.setAxisXBottom(axisX);

      chartView.setColumnChartData(data);
      chartView.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
        @Override public void onValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {
          Toasty.success(ExperimentAnalysisDataActivity.this,
              String.format("当前值：%.2f", subcolumnValue.getValue()));
        }

        @Override public void onValueDeselected() {

        }
      });
      chartView.setInteractive(true);
    }

    private void refreshCurveChart(LineChartView chartView) {
      String analysisXid = target.getAnalysisAliasX();
      //start render data

      //set X Y axis
      Axis axisY = new Axis().setHasLines(true);// Y轴属性
      Axis axisX = new Axis();// X轴属性
      axisY.setName("kPa");//设置Y轴显示名称
      FurnaceVariable furnaceVariable=AppDBUtils.getFurnaceVariablesById(analysisXid);
      if(furnaceVariable!=null) {
        axisX.setName(furnaceVariable.getName());//设置X轴显示名称
      }
      ArrayList<AxisValue> axisValuesX = new ArrayList<AxisValue>();//定义X轴刻度值的数据集合
      ArrayList<AxisValue> axisValuesY = new ArrayList<AxisValue>();//定义Y轴刻度值的数据集合
      axisX.setValues(axisValuesX);//为X轴显示的刻度值设置数据集合
      axisX.setLineColor(Color.BLACK);// 设置X轴轴线颜色
      axisY.setLineColor(Color.BLACK);// 设置Y轴轴线颜色
      axisX.setTextColor(Color.BLACK);// 设置X轴文字颜色
      axisY.setTextColor(Color.BLACK);// 设置Y轴文字颜色
      axisY.setHasLines(true);
      axisY.setHasSeparationLine(false);
      axisY.setAutoGenerated(false);
      axisX.setHasLines(true);
      axisX.setHasSeparationLine(false);
      axisX.setAutoGenerated(false);
      axisX.setTextSize(14);// 设置X轴文字大小
      axisX.setTypeface(Typeface.DEFAULT);// 设置文字样式，此处为默认
      axisX.setHasTiltedLabels(false);// 设置X轴文字向左旋转45度
      axisX.setHasLines(false);// 是否显示X轴网格线
      axisY.setHasLines(false);// 是否显示Y轴网格线
      axisX.setHasSeparationLine(true);// 设置是否有分割线
      axisX.setInside(false);// 设置X轴文字是否在X轴内部

      List<Line> lines = new ArrayList<Line>();
      for (int i = 0; i < requestCount; i++) {
        List<PointValue> values = new ArrayList<PointValue>();

        for (Map.Entry<String, String> entry : lineData.get(i).entrySet()) {
          if (entry.getKey().equals("varableId")) {
            continue;
          }

          Snapshot currSnapshot = _controller.getSnapshotById(entry.getKey());

          //search x

          if (currSnapshot.getWorkstat().getCondition() == null) {
            continue;
          }
          float xPoint = 0.0f;
          for (WorkCondition condition : currSnapshot.getWorkstat().getCondition()) {
            if (condition.getVid().equals(analysisXid)) {
              xPoint = Float.parseFloat(condition.getValue()
                  .get(condition.getValue().size() - 1 < 0 ? 0 : condition.getValue().size() - 1));
              break;
            }
          }

          //if(xPoint<=0&&values.size()>0){
          //  xPoint=values.get(values.size()-1).getX()+50;
          //}

          float yPoint = Float.parseFloat(entry.getValue());

          //
          //if(values.size()>0&&Math.abs(yPoint-values.get(values.size()-1).getY()-yPoint)>1000){
          //  yPoint=values.get(values.size()-1).getY()+50;
          //}

          PointValue pointValue = new PointValue(xPoint, yPoint);
          values.add(pointValue);
        }
        Collections.sort(values, new Comparator<PointValue>() {
          @Override public int compare(PointValue p1, PointValue p2) {
            return (int) (p1.getX() - p2.getX());
          }
        });
        //
        //for(int index=0;index<values.size();index++){
        //
        //  PointValue p=values.get(index);
        //
        //  if(index==0){
        //    continue;
        //  }
        //
        //  float xPoint=p.getX();
        //
        //  if(xPoint<=0){
        //    xPoint=values.get(index-1).getX()+10;
        //    p.set(xPoint,p.getY());
        //    values.set(index,p);
        //  }
        //
        //}

        for (int j = 0; j < 2000; j += 200) {//循环为节点、X、Y轴添加数据
          axisValuesY.add(
              new AxisValue(j + 0.0f).setLabel(String.format("%.1f", j + 0.0f)));// 添加Y轴显示的刻度值
        }

        int allCountX = 0;

        for (PointValue pointValue : values) {
          allCountX += pointValue.getX();
        }

        for (int k = 0; k < 10; k++) {
          axisValuesX.add(new AxisValue(0.0f + allCountX / 10 * k).setValue(allCountX / 10 * k)
              .setLabel(String.format("%.1f", allCountX * (k / 10.0f))));
        }

        Line line = new Line(values);
        line.setColor(Color.parseColor(filterColors[i]));
        line.setFilled(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);
      }

      LineChartData data = new LineChartData(lines);
      //data.setAxisYLeft(axisY);
      //data.setAxisXBottom(axisX);
      chartView.setLineChartData(data);
      chartView.setInteractive(true);
      chartView.setOnValueTouchListener(new LineChartOnValueSelectListener() {
        @Override public void onValueSelected(int i, int i1, PointValue pointValue) {
          Toasty.success(ExperimentAnalysisDataActivity.this,
              String.format("x轴：%.2f\ny轴：%.2f", pointValue.getX(), pointValue.getY())).show();
        }

        @Override public void onValueDeselected() {

        }
      });
    }
  }

  private int requestCount = 0;
  private List<HashMap<String, String>> lineData = new ArrayList<>();

  public static String[] filterColors = {
      "#F6A623", "#7ED321", "#50E3C2", "#B8E986", "#E80A59", "#4A4A4A", "#B3E2FF", "#0080FC",
      "#CADE4B", "#BD0FE1", "#F6A623", "#7ED321", "#50E3C2", "#B8E986", "#E80A59", "#4A4A4A",
      "#B3E2FF", "#0080FC", "#CADE4B", "#BD0FE1",
  };

  private class FilterGroup {
    public Target target;
    public PowerGroup group;

    public FilterGroup(Target target, PowerGroup group) {
      this.target = target;
      this.group = group;
    }

    public void updateTargetState(boolean value) {
      this.target.set_selected(value);
    }

    public void updateTypeState(String parent, String id, boolean value) {
      for (PowerType powerType : group.getTypes()) {
        if (parent.equals(powerType.getId())) {
          for (Power power : powerType.getPowers()) {
            if (power.getId().equals(id)) {
              power.set_selected(value);
            }
          }
        }
      }
    }
  }

  private CommonChartLineView updateChartCurveView(Target target, List<Curve> data,
      List<VariableData> realData) {
    LineChart mainView = null;
    CommonChartLineView lineView = null;
    for (CommonChartLineView line : commonChartLineViews) {
      if (line.targetId.equals(target.getId())) {
        mainView = line.mainView;
        lineView = line;
        break;
      }
    }

    if (mainView == null) {
      return null;
    }
    List<ILineDataSet> uilines =
        lineView.data != null ? lineView.data.getDataSets() : new ArrayList<ILineDataSet>();
    for (Curve curve : data) {

      for (VariableData rd : realData) {
        if (rd.getVarableId().equals(curve.getRealtime_y())) {

          int index = data.indexOf(curve);

          CommonChartLine line = new CommonChartLine();
          List<Entry> listData =
              index < uilines.size() ? ((LineDataSet) uilines.get(index)).getValues()
                  : new ArrayList<Entry>();
          listData.add(new Entry(rd.getDate(), rd.getValue()));
          line.setData(listData);
          line.setVariableId(curve.getRealtime_y());
          line.setLabel(curve.getLabel());
          line.setColor(curve.getColor());
          ILineDataSet lineDataSet = index < uilines.size() ? ((LineDataSet) uilines.get(index))
              : new LineDataSet(line.getData(), line.getLabel());
          lineDataSet.addEntry(new Entry(rd.getDate(), rd.getValue()));
          lineDataSet.setDrawFilled(true);
          lineDataSet.setVisible(true);
          lineDataSet.setDrawValues(true);
          ((LineDataSet) lineDataSet).setColor(Color.parseColor(curve.getColor()));
          if (uilines.size() == data.size()) {
            uilines.set(index, lineDataSet);
          } else {
            uilines.add(lineDataSet);
          }
        }
      }
    }

    if (lineView.data != null) {
      lineView.data.notifyDataChanged();
    }
    lineView.data = new LineData(uilines);
    mainView.setData(lineView.data);

    Description description = new Description();
    description.setText(target.getName());
    mainView.setDescription(description);

    mainView.notifyDataSetChanged();
    mainView.invalidate();

    return lineView;
  }

  //public void updateAllUIData(List<VariableData> data) {
  //  for (CommonChartGroup ccg : commonChartGroups) {
  //    if (ccg == null) {
  //      continue;
  //    }
  //
  //    TargetContent targetContent = ccg.getCurrTarget().getContent();
  //
  //    if (targetContent.getTable() != null) {//更新表格数据
  //      updateTableData(ccg, data);
  //    } else if (targetContent.getCurve() != null) {
  //      updateChartCurveView(ccg.getCurrTarget(), targetContent.getCurve(), data);
  //    }
  //  }
  //}

  //private void updateTableData(CommonChartGroup ccg, List<VariableData> tableData) {
  //
  //  ScrollView scrollView = ((ScrollView) ccg.getChartView());
  //  FlowLayout mainView = null;
  //  for (int i = 0; i < ((ViewGroup) scrollView).getChildCount(); i++) {
  //    mainView = (FlowLayout) ((ViewGroup) scrollView).getChildAt(i);
  //    continue;
  //  }
  //
  //  if (mainView == null) {
  //    return;
  //  }
  //  for (VariableData data : tableData) {
  //    for (TableVariable variable : tableSubViews) {
  //      if (data.getVarableId().equals(variable.getVariableId())) {
  //        variable.getTv_content()
  //            .setText(
  //                String.format("%.2f%s", Float.parseFloat(data.getValue()), variable.getUnit()));
  //      }
  //    }
  //  }
  //}
  //
  //private View generateTableView(Target target) {
  //  ScrollView scrollView = new ScrollView(this);
  //
  //  FlowLayout mainView = new FlowLayout(this);
  //  mainView.setLayoutParams(new ViewPager.LayoutParams());
  //  mainView.setGravity(Gravity.START | Gravity.TOP);
  //
  //  List<TargetContent.TargetTable.TableData> datas = target.getContent().getTable().getData();
  //
  //  for (TargetContent.TargetTable.TableData data : datas) {
  //
  //    String defaultValue = data.getSubTitle();
  //
  //    if (TextUtils.isEmpty(defaultValue)) {
  //      if (TextUtils.isEmpty(data.getUnit())) {
  //        defaultValue = data.getVariableId();
  //      } else {
  //        defaultValue = String.format("%.2f%s", 0.0f, data.getUnit());
  //      }
  //    }
  //    tableSubViews = new ArrayList<>();
  //    View subView =
  //        generateSubView(data.getVariableId(), data.getTitle(), defaultValue, data.getUnit());
  //
  //    FlowLayout.LayoutParams params =
  //        new FlowLayout.LayoutParams(ViewUtils.getDimenByRateWidth(320),
  //            ViewUtils.getDimenByRateWidth(45));
  //    params.leftMargin = params.rightMargin = ViewUtils.getDimenByRateWidth(15);
  //    mainView.addView(subView, params);
  //  }
  //
  //  scrollView.addView(mainView, new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
  //      ViewGroup.LayoutParams.MATCH_PARENT));
  //
  //  return scrollView;
  //}

  //public View generateSubView(String variableId, String title, String value, String unit) {
  //  RelativeLayout mainView = new RelativeLayout(this);
  //
  //  TextView tv_name = new TextView(this);
  //  RelativeLayout.LayoutParams params =
  //      new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
  //          ViewGroup.LayoutParams.WRAP_CONTENT);
  //  params.addRule(RelativeLayout.CENTER_VERTICAL);
  //
  //  ViewUtils.adjustViewTextSizeLandscape(tv_name, 50);
  //  tv_name.setTextColor(Color.BLACK);
  //  tv_name.setText(title);
  //  mainView.addView(tv_name, params);
  //
  //  TextView tv_content = new TextView(this);
  //  params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
  //      ViewGroup.LayoutParams.WRAP_CONTENT);
  //  params.addRule(RelativeLayout.CENTER_VERTICAL);
  //  params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
  //  params.rightMargin = ViewUtils.getDimenByRateWidth(25);
  //
  //  tv_content.setText(value);
  //  tv_content.setBackgroundResource(R.drawable.bg_workstat_item);
  //  tv_content.setTextColor(Color.WHITE);
  //  tv_content.setPadding(ViewUtils.getDimenByRateWidth(10), ViewUtils.getDimenByRateWidth(5),
  //      ViewUtils.getDimenByRateWidth(10), ViewUtils.getDimenByRateWidth(5));
  //  ViewUtils.adjustViewTextSizeLandscape(tv_content, 50);
  //
  //  tv_content.setTag(variableId);
  //
  //  tableSubViews.add(new TableVariable(tv_content, variableId, unit, value));
  //
  //  mainView.addView(tv_content, params);
  //
  //  params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
  //      ViewUtils.getDimenByRateWidth(1));
  //  params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
  //
  //  View divider = new View(this);
  //  divider.setBackgroundColor(getResources().getColor(R.color.divider_grey));
  //
  //  mainView.addView(divider, params);
  //
  //  return mainView;
  //}

  private void loadData() {
    tv_header_title.setText(String.format("%s-数据分析", header_title));
    currExp =
        (ExperimentInfo) DBManager.getCache(CacheKeyConstant.EXPERIMENT_CACHE_ID + experimentId);
    if (currExp == null) {
      Toasty.error(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
      return;
    }

    currPowerGroup = currExp.getPowerGroup();

    currTargets = currExp.getTargets();
    if (currTargets == null || currTargets.size() == 0) {
      Toasty.error(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
      return;
    }
    generateViewPager(currTargets);

    initPowerGroups(currPowerGroup);
  }

  private void initPowerGroups(PowerGroup currPowerGroup) {

  }

  private void initViews() {
    ViewUtils.adjustViewTextSizeLandscape(tv_header_back, 70);
    ViewUtils.adjustViewTextSizeLandscape(tv_header_title, 70);
  }

  @OnClick(R.id.rl_header_back) public void onBackClick() {
    finish();
  }

  private class TableVariable {
    private TextView tv_content;
    private String variableId;
    private String unit;
    private String value;

    public TableVariable(TextView tv_content, String variableId, String unit, String value) {
      this.tv_content = tv_content;
      this.variableId = variableId;
      this.unit = unit;
      this.value = value;
    }

    public TextView getTv_content() {
      return tv_content;
    }

    public void setTv_content(TextView tv_content) {
      this.tv_content = tv_content;
    }

    public String getVariableId() {
      return variableId;
    }

    public void setVariableId(String variableId) {
      this.variableId = variableId;
    }

    public String getUnit() {
      return unit;
    }

    public void setUnit(String unit) {
      this.unit = unit;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  private class CommonChartLineView {
    public List<CommonChartLine> chartLines;
    public String targetId;
    public LineChart mainView;
    public LineData data;
  }

  private class CommonChartLine {
    private String variableId;
    private List<Entry> data;
    private String label;
    private String color;

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public String getVariableId() {
      return variableId;
    }

    public void setVariableId(String variableId) {
      this.variableId = variableId;
    }

    public List<Entry> getData() {
      return data;
    }

    public void setData(List<Entry> data) {
      this.data = data;
    }

    public void setColor(String color) {
      this.color = color;
    }

    public String getColor() {
      return color;
    }
  }

  private class CommonChartGroup {
    private String experimentId;
    private Target currTarget;
    private View chartView;

    public CommonChartGroup(String experimentId, Target currTarget, View chartView) {
      this.experimentId = experimentId;
      this.currTarget = currTarget;
      this.chartView = chartView;
    }

    public String getExperimentId() {
      return experimentId;
    }

    public void setExperimentId(String experimentId) {
      this.experimentId = experimentId;
    }

    public Target getCurrTarget() {
      return currTarget;
    }

    public void setCurrTarget(Target currTarget) {
      this.currTarget = currTarget;
    }

    public View getChartView() {
      return chartView;
    }

    public void setChartView(View chartView) {
      this.chartView = chartView;
    }
  }

  private class FilterButtons {
    private List<Button> btn_var;
    private List<List<Button>> btn_powers;

    public List<Button> getBtn_var() {
      return btn_var;
    }

    public void setBtn_var(List<Button> btn_var) {
      this.btn_var = btn_var;
    }

    public List<List<Button>> getBtn_powers() {
      return btn_powers;
    }

    public void setBtn_powers(List<List<Button>> btn_powers) {
      this.btn_powers = btn_powers;
    }
  }

  private class StyleSwitchClickListener implements View.OnClickListener {

    private View mainView;

    private Target target;

    public StyleSwitchClickListener(RelativeLayout mainVew,Target target) {
      this.mainView = mainVew;
      this.target=target;
    }

    @Override public void onClick(View view) {
      String tag = (String) view.getTag();

      switch (tag) {
        case VIEWTAG_STYLEBAR:
          //show bar
          mainView.findViewWithTag(VIEWTAG_BARCHART).setVisibility(View.VISIBLE);
          mainView.findViewWithTag(VIEWTAG_PIECHART).setVisibility(View.GONE);
          mainView.findViewWithTag(VIEWTAG_LINECHART).setVisibility(View.GONE);
          break;
        case VIEWTAG_STYLECURVE:
          mainView.findViewWithTag(VIEWTAG_BARCHART).setVisibility(View.GONE);
          mainView.findViewWithTag(VIEWTAG_PIECHART).setVisibility(View.GONE);
          mainView.findViewWithTag(VIEWTAG_LINECHART).setVisibility(View.VISIBLE);
          break;
        case VIEWTAG_STYLEPIE:
          mainView.findViewWithTag(VIEWTAG_BARCHART).setVisibility(View.GONE);
          mainView.findViewWithTag(VIEWTAG_PIECHART).setVisibility(View.VISIBLE);
          mainView.findViewWithTag(VIEWTAG_LINECHART).setVisibility(View.GONE);
          refreshPieView(target,mainView);
          break;
      }
    }
  }

  private boolean canRequest=true;

  private void refreshPieView(Target target,View mainView) {
    BProgressDialog.showProgressDialog(this);
    if(_controller.snapshotList==null||_controller.snapshotList.size()==0){
      _controller.refreshExpSnapshots();
      Toasty.warning(this,"快照列表更新未完成，请稍后再试！");
      return;
    }

    initPieRequestCount(_controller.pieRequestCount,_controller.pieRequestValues,_controller.snapshotList);

    for(Snapshot snapshot:_controller.snapshotList){

      ScrollView scrollView=
          (ScrollView) mainView.findViewWithTag(ExperimentAnalysisDataActivity.VIEWTAG_PIESCROLLVIEW);

      LinearLayout linearLayout=
          (LinearLayout) scrollView.findViewWithTag(ExperimentAnalysisDataActivity.VIEWTAG_PIECHART);

      _controller.updatePieChartView(
          linearLayout,filterButtonsHashMap.get(target.getId()).getBtn_var(),snapshot);

    }
    mainView.invalidate();
    BProgressDialog.dismissProgressDialog();
  }

  private void initPieRequestCount(HashMap<String, Integer> hashMap,HashMap<String,List<SliceValue>> values,List<Snapshot> snapshots) {
    for(Snapshot snapshot:snapshots){
      hashMap.put(snapshot.getId(),0);
      values.put(snapshot.getId(),new ArrayList<SliceValue>());
    }
  }
}
