package com.meetutech.baosteel.ui.exp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.meetutech.baosteel.common.AppConstant;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.common.HTTPConstant;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.controller.ExperimentRealtimeController;
import com.meetutech.baosteel.http.result.VariableData;
import com.meetutech.baosteel.model.http.Curve;
import com.meetutech.baosteel.model.http.ExperimentInfo;
import com.meetutech.baosteel.model.http.FurnaceVariable;
import com.meetutech.baosteel.model.http.Target;
import com.meetutech.baosteel.model.http.TargetContent;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.AppDBUtils;
import com.meetutech.baosteel.utils.DBManager;
import com.meetutech.baosteel.utils.FurnaceVariableUtils;
import com.meetutech.baosteel.utils.NetworkUtils;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.chart.ChartGenerator;
import com.meetutech.baosteel.widget.chart.GLSurfaceChart;
import com.meetutech.baosteel.widget.layout.FlowLayout;
import com.squareup.picasso.Picasso;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import me.relex.circleindicator.CircleIndicator;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.info
// Author: culm at 2017-04-27
//*********************************************************
@Route(path = RouterConstant.EXPERIMENT_REALTIME_DATA) public class ExperimentRealtimeDataActivity
    extends BaseActivity {

  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.tv_header_back) TextView tv_header_back;
  public @BindView(R.id.vp_content) ViewPager vp_content;
  public @BindView(R.id.indicator) CircleIndicator indicator;
  public @BindView(R.id.btn_chat) Button btn_chat;

  public @Autowired(name = "title") String header_title;
  public @Autowired(name = "experimentId") String experimentId;
  public @Autowired boolean longTerm;

  private ExperimentInfo currExp;
  private List<Target> currTargets;
  private int pageSize;
  private List<View> pageViews;
  private CommonInfoViewPageAdapter vp_adapter;
  private List<CommonChartGroup> commonChartGroups;
  private ExperimentRealtimeController _controller;
  private List<TableVariable> tableSubViews;

  private List<CommonChartLineView> commonChartLineViews;
  private ArrayList<ILineDataSet> uilines;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_realtime_data);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    _controller = new ExperimentRealtimeController(this);
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

  @Override protected void onDestroy() {
    ExperimentRealtimeController.isLoopFetch = false;
    super.onDestroy();
  }

  private void loadNetworkData() {

    if (!NetworkUtils.isOnline(this)) {
      Toasty.error(this, getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
      return;
    }

    _controller.startGetAllTopData(experimentId, false);
    _controller.loopGetTopData(experimentId);
  }

  private void generateViewPager(List<Target> currTargets) {
    pageSize = countViewPagers(currTargets);
    currTargets = mergeTargets(currTargets);
    vp_content.setVisibility(
        currTargets == null || currTargets.size() == 0 ? View.GONE : View.VISIBLE);
    pageViews = generatePageViews(currTargets);
    vp_adapter = new CommonInfoViewPageAdapter(this, pageViews);
    vp_content.setAdapter(vp_adapter);
    indicator.setViewPager(vp_content);
    vp_adapter.registerDataSetObserver(indicator.getDataSetObserver());
  }

  private List<Target> mergeTargets(List<Target> currTargets) {
    List<Target> tempTargets = new ArrayList<>();

    Target mergeTarget = null;

    for (Target target : currTargets) {

      if (target.getContent() == null || target.getContent().getTable() == null) {
        tempTargets.add(target);
        continue;
      }

      if (target.getContent().getTable().getIsMerge() == 1) {
        if (mergeTarget == null) {
          mergeTarget = target;
        } else {//合并到mergeTarget
          mergeTarget.getContent()
              .getTable()
              .getData()
              .addAll(target.getContent().getTable().getData());
        }
      } else {
        tempTargets.add(target);
      }
    }

    if (mergeTarget != null) {
      tempTargets.add(mergeTarget);
    }

    return tempTargets;
  }

  private List<View> generatePageViews(List<Target> currTargets) {
    List<View> contentViews = new ArrayList<>(currTargets.size());
    commonChartGroups = new ArrayList<>(currTargets.size());
    commonChartLineViews = new ArrayList<>();
    for (Target target : currTargets) {

      TargetContent targetContent = target.getContent();

      if (targetContent == null) {
        continue;
      }

      View chart = null;
      if(!target.isShowRealtime()){
        continue;
      }
      if (targetContent.getTable() != null) {//表格模板
        chart = generateTableView(target);
      } else if (targetContent.getCommon_dot_plot() != null) {//点阵图
        chart = generateDotplotView(target);
      } else if (targetContent.getSurface() != null) {//曲面模板
        if (AppConstant.DEBUG) {
          TargetContent.TargetSurface surface = targetContent.getSurface();
          chart = generateChartSurfaceView(surface);
        } else {
          chart = new TextView(this);
          ((TextView) chart).setText("由于机型问题，暂时关闭该功能！");
          ((TextView) chart).setGravity(Gravity.CENTER);
          ViewUtils.adjustViewTextSizeLandscape((TextView) chart, 55);
        }
      } else if (targetContent.getCurve() != null) {//曲线模板
        chart = new LineChart(this);
        chart.setLayoutParams(new ViewPager.LayoutParams());
        List<Curve> curves = targetContent.getCurve();
        CommonChartLineView chartLineView = new CommonChartLineView();
        List<CommonChartLine> lines = new ArrayList<>();
        for (Curve curve : curves) {

          CommonChartLine line = new CommonChartLine();

          line.setVariableId(curve.getRealtime_y());
          line.setLabel(curve.getLabel());
          line.setColor(curve.getColor());
          lines.add(line);
        }
        chartLineView.chartLines = lines;
        chartLineView.targetId = target.getId();
        chartLineView.mainView = (LineChart) chart;
        commonChartLineViews.add(chartLineView);
      } else if (targetContent.getGrid() != null) {
        contentViews =
            new ChartGenerator(this).generate312DotPlotView(experimentId, target, contentViews);
      } else if(targetContent.getCurveRefresh()!=null){
        chart=generateReCurveView(experimentId,target);
        //chart.setTag(target.getId());
      }
      if (chart != null) {
        contentViews.add(chart);
        CommonChartGroup ccg = new CommonChartGroup(experimentId, target, chart);
        commonChartGroups.add(ccg);
      }
    }
    //Collections.reverse(contentViews);

    return contentViews;
  }

  private View generateReCurveView(String experimentId, Target target) {
    RelativeLayout mainView=new RelativeLayout(this);
    mainView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));

    LineChartView lineChart = new LineChartView(this);
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    params.topMargin = ViewUtils.getDimenByRateWidth(20);
    mainView.addView(lineChart, params);
    lineChart.setInteractive(true);
    lineChart.setZoomEnabled(false);
    lineChart.setContainerScrollEnabled(false, null);
    lineChart.setTag(target.getId());
    return mainView;
  }

  private View generateDotplotView(Target target) {

    RelativeLayout mainView = new RelativeLayout(this);

    TargetContent.TargetDotplot content = target.getContent().getCommon_dot_plot();

    String backgroundurl = HTTPConstant.getStaticFileFullPath(content.getBackground());

    ImageView iv_background = new ImageView(this);

    RelativeLayout.LayoutParams rl_params =
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    mainView.addView(iv_background, rl_params);

    //load background
    Picasso.with(this).load(backgroundurl).fit().into(iv_background);

    String[] size = content.getSize().split("x");

    if (size.length < 2 || Integer.parseInt(size[0]) <= 0 || Integer.parseInt(size[1]) <= 0) {
      Toasty.error(this, "点阵图大小格式错误，无法绘制！").show();
      return mainView;
    }

    int rowSize = Integer.parseInt(size[0]);
    int colSize = Integer.parseInt(size[1]);

    DisplayMetrics displayMetrics = ViewUtils.getScreenResolution(this);

    float cell_width = displayMetrics.widthPixels / colSize;
    float cell_height = displayMetrics.heightPixels * 0.75f / rowSize;

    LinearLayout ll_dotplot = new LinearLayout(this);

    rl_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    ll_dotplot.setOrientation(LinearLayout.VERTICAL);
    mainView.addView(ll_dotplot, rl_params);

    for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
      LinearLayout ll_row = new LinearLayout(this);
      ll_row.setOrientation(LinearLayout.HORIZONTAL);
      for (int colIndex = 0; colIndex < colSize; colIndex++) {
        TextView tv = new TextView(this);
        ViewUtils.adjustViewTextSize(tv,7);
        tv.setTextColor(Color.BLACK);
        tv.setTag(content.getData().get(rowIndex).get(colIndex));
        ll_row.addView(tv, (int) cell_width, LinearLayout.LayoutParams.MATCH_PARENT);
      }
      ll_dotplot.addView(ll_row,ViewGroup.LayoutParams.MATCH_PARENT, (int) cell_height);
    }

    return mainView;
  }

  private View generateChartSurfaceView(TargetContent.TargetSurface surface) {
    mainView = getLayoutInflater().inflate(R.layout.chart_common_surface, null);
    GLSurfaceChart chart = (GLSurfaceChart) mainView.findViewById(R.id.chart);
    chart.initScene(surface);
    return mainView;
  }

  private CommonChartLineView updateChartCurveView(LineChart chartView, Target target,
      List<Curve> data, List<VariableData> realData) {
    LineChart mainView = chartView;
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

  public void updateAllUIData(List<VariableData> data) {
    for (CommonChartGroup ccg : commonChartGroups) {
      if (ccg == null) {
        continue;
      }

      TargetContent targetContent = ccg.getCurrTarget().getContent();

      if (targetContent.getTable() != null) {//更新表格数据
        updateTableData(ccg, data);
      } else if (targetContent.getCurve() != null) {
        updateChartCurveView((LineChart) ccg.getChartView(), ccg.getCurrTarget(),
            targetContent.getCurve(), data);
      } else if (targetContent.getCommon_dot_plot() != null) {
        updateDotplotData((RelativeLayout) ccg.getChartView(), ccg.getCurrTarget(),
            targetContent.getCommon_dot_plot(), data);
      } else if(targetContent.getCurveRefresh()!=null){
        updateCurveRefreshData((LineChartView)ccg.getChartView().findViewWithTag(ccg.getCurrTarget().getId()),
            ccg.getCurrTarget(),data);
      }
    }
  }

  private void updateCurveRefreshData(LineChartView mainChartView, Target target,
      List<VariableData> data) {


    TargetContent.CurveRefresh curveRefresh=target.getContent().getCurveRefresh();

    curveRefresh.initAllLines();

    if(curveRefresh==null||Integer.parseInt(target.getContent().getCurve_refresh_line_quantity())<=0){
      return;
    }

    List<List<Float>> filterData=new ArrayList<>();
    for(int i=0;i<Integer.parseInt(target.getContent().getCurve_refresh_line_quantity());i++) {
      List<Float> subData=new ArrayList<>();
      for (String currVid : curveRefresh.getLine().get(i).getDot()) {
        for (VariableData d : data) {
          if (currVid.equals(d.getVarableId())) {
            subData.add(d.getValue());
            break;
          }
        }
      }
      filterData.add(subData);
    }
    //start render data

    //set X Y axis
    Axis axisY = new Axis().setHasLines(true);// Y轴属性
    Axis axisX = new Axis();// X轴属性
    //axisY.setName("");//设置Y轴显示名称
    //FurnaceVariable furnaceVariable=AppDBUtils.getFurnaceVariablesById();
    //if(furnaceVariable!=null) {
    //  axisX.setName(furnaceVariable.getName());//设置X轴显示名称
    //}
    ArrayList<AxisValue> axisValuesX = new ArrayList<AxisValue>();//定义X轴刻度值的数据集合
    ArrayList<AxisValue> axisValuesY = new ArrayList<AxisValue>();//定义Y轴刻度值的数据集合
    axisX.setValues(axisValuesX);//为X轴显示的刻度值设置数据集合
    axisX.setLineColor(Color.BLACK);// 设置X轴轴线颜色
    axisY.setLineColor(Color.BLACK);// 设置Y轴轴线颜色
    axisX.setTextColor(Color.BLACK);// 设置X轴文字颜色
    axisY.setTextColor(Color.BLACK);// 设置Y轴文字颜色
    axisY.setHasLines(false);
    axisY.setValues(axisValuesY);
    axisY.setHasSeparationLine(false);
    axisY.setAutoGenerated(true);
    axisX.setHasLines(true);
    axisX.setHasSeparationLine(false);
    axisX.setAutoGenerated(false);
    axisX.setTextSize(14);// 设置X轴文字大小
    axisX.setTypeface(Typeface.DEFAULT);// 设置文字样式，此处为默认
    axisX.setHasSeparationLine(true);// 设置是否有分割线
    axisX.setInside(false);// 设置X轴文字是否在X轴内部

    for(int y=0;y<200;y++){
      axisValuesY.add(new AxisValue(y*25));
    }

    List<Line> lines = new ArrayList<Line>();
    for (int i = 0; i < Integer.parseInt(target.getContent().getCurve_refresh_line_quantity()); i++) {
      List<PointValue> values = new ArrayList<PointValue>();

      for(int j=0;j<filterData.get(i).size();j++){
        PointValue value=new PointValue(j*10,filterData.get(i).get(j));
        value.setLabel(String.format("%s-%d(%.2f)",
            FurnaceVariableUtils.getFurnaceVariableName(
                curveRefresh.getLine().get(i).getDot().get(j)),
            (int)value.getX(),value.getY()));
        values.add(value);
      }

      int allCountX = 0;

      for (PointValue pointValue : values) {
        allCountX += pointValue.getX();
      }

      for (int k = 0; k < curveRefresh.getDot_label().size(); k++) {
        axisValuesX.add(new AxisValue(0.0f + allCountX / 10 * k).setValue(allCountX / 10 * k)
            .setLabel(String.format("%.1f", allCountX * (k / 10.0f))));
      }

      int currColor=parseCssColor(curveRefresh.getAllLines()[i].getColor());

      Line line = new Line(values);

      line.setColor(currColor==-1?Color.parseColor(filterColors[i]):currColor);
      line.setFilled(false);
      line.setHasLines(curveRefresh.getNoLine()==1?true:false);
      line.setHasPoints(true);
      line.setHasLabelsOnlyForSelected(true);
      line.setFormatter(new SimpleLineChartValueFormatter(2));
      lines.add(line);
    }

    LineChartData lineChartData = new LineChartData(lines);
    mainChartView.setInteractive(true);
    lineChartData.setAxisYLeft(axisY);
    lineChartData.setAxisXBottom(axisX);
    mainChartView.setLineChartData(lineChartData);

  }

  public int parseCssColor(String input)
  {
    Pattern c = Pattern.compile("rgba*\\( *([0-9]+),*([0-9]+),*([0-9]+),*([0-9]+)*\\)");
    Matcher m = c.matcher(input);

    if (m.matches())
    {
      return Color.argb(Integer.valueOf(m.group(4)),
          Integer.valueOf(m.group(1)),Integer.valueOf(m.group(2)),Integer.valueOf(m.group(3)));
    }

    return -1;
  }

    public static String[] filterColors = {
        "#EB0E2D", "#7ED321", "#50E3C2", "#B8E986", "#E80A59", "#4A4A4A", "#B3E2FF", "#0080FC",
        "#CADE4B", "#BD0FE1", "#F6A623", "#7ED321", "#50E3C2", "#B8E986", "#E80A59", "#4A4A4A",
        "#B3E2FF", "#0080FC", "#CADE4B", "#BD0FE1",
    };


  private void updateDotplotData(RelativeLayout chartView, Target currTarget,
      TargetContent.TargetDotplot common_dot_plot, List<VariableData> data) {

    for (VariableData variableData : data){
      String vid=variableData.getVarableId();
      String unit="";

      FurnaceVariable furnaceVariable= AppDBUtils.getFurnaceVariablesById(vid);
      if(furnaceVariable!=null){
        unit=FurnaceVariable.TYPE2UNIT.get(furnaceVariable.getType());
      }

      TextView tv= (TextView) chartView.findViewWithTag(vid);
      if(tv==null){
        continue;
      }
      tv.setText(String.format("%.2f%s",variableData.getValue(),unit));
    }
  }

  private void updateTableData(CommonChartGroup ccg, List<VariableData> tableData) {

    ScrollView scrollView = ((ScrollView) ccg.getChartView());
    FlowLayout mainView = null;
    for (int i = 0; i < ((ViewGroup) scrollView).getChildCount(); i++) {
      mainView = (FlowLayout) ((ViewGroup) scrollView).getChildAt(i);
      continue;
    }

    if (mainView == null) {
      return;
    }
    for (VariableData data : tableData) {
      for (TableVariable variable : tableSubViews) {
        if (data.getVarableId().equals(variable.getVariableId())) {
          variable.getTv_content()
              .setText(
                  String.format("%.2f%s", data.getValue(), variable.getUnit()));
        }
      }
    }
  }

  private View generateTableView(Target target) {
    ScrollView scrollView = new ScrollView(this);

    FlowLayout mainView = new FlowLayout(this);
    mainView.setLayoutParams(new ViewPager.LayoutParams());
    mainView.setGravity(Gravity.START | Gravity.TOP);

    List<TargetContent.TargetTable.TableData> datas = target.getContent().getTable().getData();

    for (TargetContent.TargetTable.TableData data : datas) {

      String defaultValue = data.getSubTitle();

      if (TextUtils.isEmpty(defaultValue)) {
        if (TextUtils.isEmpty(data.getUnit())) {
          defaultValue = data.getVariableId();
        } else {
          defaultValue = String.format("%.2f%s", 0.0f, data.getUnit());
        }
      }
      tableSubViews = new ArrayList<>();
      View subView =
          generateSubView(data.getVariableId(), data.getTitle(), defaultValue, data.getUnit());

      FlowLayout.LayoutParams params =
          new FlowLayout.LayoutParams(ViewUtils.getDimenByRateWidth(320),
              ViewUtils.getDimenByRateWidth(45));
      params.leftMargin = params.rightMargin = ViewUtils.getDimenByRateWidth(15);
      mainView.addView(subView, params);
    }

    scrollView.addView(mainView, new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));

    return scrollView;
  }

  public View generateSubView(String variableId, String title, String value, String unit) {
    RelativeLayout mainView = new RelativeLayout(this);

    TextView tv_name = new TextView(this);
    RelativeLayout.LayoutParams params =
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.CENTER_VERTICAL);

    ViewUtils.adjustViewTextSizeLandscape(tv_name, 50);
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
    tv_content.setPadding(ViewUtils.getDimenByRateWidth(10), ViewUtils.getDimenByRateWidth(5),
        ViewUtils.getDimenByRateWidth(10), ViewUtils.getDimenByRateWidth(5));
    ViewUtils.adjustViewTextSizeLandscape(tv_content, 50);

    tv_content.setTag(variableId);

    tableSubViews.add(new TableVariable(tv_content, variableId, unit, value));

    mainView.addView(tv_content, params);

    params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewUtils.getDimenByRateWidth(1));
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

    View divider = new View(this);
    divider.setBackgroundColor(getResources().getColor(R.color.divider_grey));

    mainView.addView(divider, params);

    return mainView;
  }

  private int countViewPagers(List<Target> currTargets) {

    int size = 0;

    boolean hasMerge = false;

    for (Target target : currTargets) {

      if (target.getContent() != null && target.isShowRealtime()) {
        if (target.getContent().getTable() != null) {//table
          if (target.getContent().getTable().getIsMerge() == 1) {
            if (!hasMerge) {
              size++;
              hasMerge = true;
            }
          } else {
            size++;
          }
        } else {
          size++;
        }
      }
    }

    return size;
  }

  private void loadData() {
    btn_chat.setVisibility(longTerm ? View.VISIBLE : View.GONE);
    tv_header_title.setText(String.format("%s-实时数据", header_title));
    currExp =
        (ExperimentInfo) DBManager.getCache(CacheKeyConstant.EXPERIMENT_CACHE_ID + experimentId);
    if (currExp == null) {
      Toasty.error(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
      return;
    }

    currTargets = currExp.getTargets();
    if (currTargets == null || currTargets.size() == 0) {
      Toasty.error(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
      return;
    }
    generateViewPager(currTargets);
  }

  private void initViews() {
    ViewUtils.adjustViewTextSizeLandscape(tv_header_back, 70);
    ViewUtils.adjustViewTextSizeLandscape(tv_header_title, 70);
  }

  @OnClick(R.id.rl_header_back) public void onBackClick() {
    _controller.isLoopFetch = false;
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

  public static class CommonChartGroup {
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
}
