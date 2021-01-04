package com.meetutech.baosteel.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.result.CommonResult;
import com.meetutech.baosteel.http.result.OnePartResult;
import com.meetutech.baosteel.ui.adapter.AllPartItemAdapter;
import com.meetutech.baosteel.ui.adapter.MyItemDecoration;
import com.meetutech.baosteel.ui.adapter.OnePartItemAdapter;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.NumberUtils;
import com.veken.chartview.bean.ChartBean;
import com.veken.chartview.drawtype.DrawBgType;
import com.veken.chartview.drawtype.DrawConnectLineType;
import com.veken.chartview.drawtype.DrawLineType;
import com.veken.chartview.view.LineChartView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OnePartActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.tv11)
    TextView tv11;
    @BindView(R.id.tv12)
    TextView tv12;
    @BindView(R.id.tv13)
    TextView tv13;
    @BindView(R.id.tv14)
    TextView tv14;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.sp1)
    TextView sp1;
    @BindView(R.id.sp2)
    TextView sp2;
    @BindView(R.id.sp3)
    TextView sp3;
    @BindView(R.id.sp4)
    TextView sp4;
    @BindView(R.id.sp5)
    TextView sp5;
    @BindView(R.id.sp6)
    TextView sp6;
    @BindView(R.id.inner_rv1)
    RecyclerView innerRv1;
    @BindView(R.id.inner_rv2)
    RecyclerView innerRv2;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.chart_view)
    LineChartView lineChartView;
    MyAdapter cAdapter;
    OnePartItemAdapter adapter1;
    OnePartItemAdapter adapter2;

    String title;
    List<LinkedTreeMap<String, Double>> list = new ArrayList<>();

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_one_part);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("TITLE");
        tvTitle.setText(title);

        lineChartView.setyLableText("温度℃");
        //设置点击背景（可以为图片，也可以为一个颜色背景，大小根据textAndClickBgMargin设置）
        lineChartView.setDrawBgType(DrawBgType.DrawBitmap);
        //设置图片资源
        lineChartView.setShowPicResource(R.mipmap.click_icon);
        //连接线为虚线（也可以为实现）
        lineChartView.setDrawConnectLineType(DrawConnectLineType.DrawDottedLine);
        lineChartView.setClickable(true);
        //是否需要画连接线
        lineChartView.setNeedDrawConnectYDataLine(true);
        //连接线的颜色
        lineChartView.setConnectLineColor(getResources().getColor(R.color.default_color));
        //是否需要背景
        lineChartView.setNeedBg(true);
        //画曲线图（也可以为折线图）
        lineChartView.setDrawLineType(DrawLineType.Draw_Curve);

        initViewData();
        refreshData();
    }

    private void initViewData() {
        List<ChartBean> lineChartBeanList = new ArrayList<>();
        int count = 20;
        switch (title) {
            case "大功率明火烧嘴":
                count = 12;
                break;
            case "W型辐射管烧嘴":
                count = 20;
                break;
            case "中功率明火烧嘴":
            case "点火枪":
                count = 11;
                break;
            case "小功率明火烧嘴":
                count = 6;
                break;
            case "U型辐射管烧嘴":
                count = 8;
                break;
            case "I型辐射管烧嘴":
                count = 5;
                break;
        }
        for (int i = 0; i < count; i++) {
            ChartBean lineChartBean = new ChartBean();
            lineChartBean.setValue(String.valueOf(new Random().nextInt(100)));//y
            lineChartBean.setDate(String.valueOf(i));//x
            lineChartBeanList.add(lineChartBean);
        }
        lineChartView.setData(lineChartBeanList);

        cAdapter = new MyAdapter(OnePartActivity.this, list);
        gridView.setAdapter(cAdapter);
        innerRv1.setLayoutManager(new GridLayoutManager(this, 2));
        innerRv1.addItemDecoration(new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        adapter1 = new OnePartItemAdapter(this, new ArrayList<>());
        innerRv1.setAdapter(adapter1);
        innerRv2.setLayoutManager(new GridLayoutManager(this, 2));
        innerRv2.addItemDecoration(new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        adapter2 = new OnePartItemAdapter(this, new ArrayList<>());
        innerRv2.setAdapter(adapter2);
    }

    private void refreshData() {
        Map<String, String> body = new HashMap<>();
        body.put("name", title);
        new RestClient().getApiService()
                .getVariable(body, new Callback<CommonResult<OnePartResult>>() {
                    @Override
                    public void success(CommonResult<OnePartResult> commonResult, Response response) {
                        if (commonResult != null && commonResult.getResult() != null) {
                            OnePartResult result = commonResult.getResult();
                            tv1.setText(setText("空气流量(Nm3/h)\n" + NumberUtils.getTwoN(result.getAirflow())));
                            tv2.setText(setText("煤气流量(Nm3/h)\n" + NumberUtils.getTwoN(result.getGasflow())));
                            tv3.setText(setText("孔板前压力(Pa)\n" + NumberUtils.getTwoN(result.getPressureinfrontoforifice())));
                            tv4.setText(setText("孔板后压力(Pa)\n" + NumberUtils.getTwoN(result.getPressurebehindorifice())));
                            tv5.setText(setText("排烟温度(℃)\n" + NumberUtils.getTwoN(result.getExhaustemperature())));
                            tv6.setText(setText("排烟压力(Pa)\n" + NumberUtils.getTwoN(result.getExhaustpressure())));
                            tv7.setText(setText("成分O2(Nm3/h)\n" + NumberUtils.getTwoN(result.getSmokeo2())));
                            tv8.setText(setText("成分NO(Nm3/h)\n" + NumberUtils.getTwoN(result.getSmokeno())));
                            tv9.setText(setText("成分CO2(Nm3/h)\n" + NumberUtils.getTwoN(result.getSmokeco2())));
                            tv10.setText(setText("成分NO2(Nm3/h)\n" + NumberUtils.getTwoN(result.getSmokeno2())));
                            tv11.setText(setText("成分CO(Nm3/h)\n" + NumberUtils.getTwoN(result.getSmokeco())));
                            tv12.setText(setText("成分NOx(Nm3/h)\n" + NumberUtils.getTwoN(result.getSmokenox())));
                            tv13.setText(setText("炉温(℃)\n" + NumberUtils.getTwoN(result.getFurnacetemperature())));
                            tv14.setText(setText("炉压(Pa)\n" + NumberUtils.getTwoN(result.getFurnaceressure())));
                            list.clear();
                            //DATA
                            if (result.getAxistemperaturegroup() != null && result.getAxistemperaturegroup().size() > 0) {
                                List<ChartBean> lineChartBeanList = new ArrayList<>();
                                for (int i = 0; i < result.getAxistemperaturegroup().size(); i++) {
                                    ChartBean lineChartBean = new ChartBean();
                                    lineChartBean.setValue(NumberUtils.getTwoN(getHead(result.getAxistemperaturegroup().get(i)).getValue()));//y
                                    lineChartBean.setDate(String.valueOf(i));//x
                                    lineChartBeanList.add(lineChartBean);
                                }
                                lineChartView.setData(lineChartBeanList);
                                switch (title) {
                                    case "大功率明火烧嘴":
                                    case "W型辐射管烧嘴":
                                        list.addAll(result.getAxistemperaturegroup());
                                        gridView.setNumColumns(4);
                                        break;
                                    case "中功率明火烧嘴":
                                    case "点火枪":
                                        list.add(getNullMap());
                                        for (int i = 0; i < result.getAxistemperaturegroup().size(); i++) {
                                            if (i == 1 || i == result.getAxistemperaturegroup().size() - 1) {
                                                list.add(getNullMap());
                                            }
                                            list.add(result.getAxistemperaturegroup().get(i));
                                        }
                                        list.add(getNullMap());
                                        gridView.setNumColumns(3);
                                        break;
                                    case "小功率明火烧嘴":
                                    case "U型辐射管烧嘴":
                                        list.addAll(result.getAxistemperaturegroup());
                                        gridView.setNumColumns(2);
                                        break;
                                    case "I型辐射管烧嘴":
                                        list.addAll(result.getAxistemperaturegroup());
                                        gridView.setNumColumns(1);
                                        break;
                                }
                                cAdapter.notifyDataSetChanged();
                            }
                            if (result.getPrimaryheatexchanger() != null && result.getPrimaryheatexchanger().size() > 0) {
                                ll1.setVisibility(View.VISIBLE);
                                adapter1.setNewData(result.getPrimaryheatexchanger());
                            } else {
                                ll1.setVisibility(View.GONE);
                            }
                            if (result.getSecondaryheatexchanger() != null && result.getSecondaryheatexchanger().size() > 0) {
                                ll2.setVisibility(View.VISIBLE);
                                adapter2.setNewData(result.getSecondaryheatexchanger());
                            } else {
                                ll2.setVisibility(View.GONE);
                            }
                        }

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                refreshData();
                            }
                        }, 2000);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                refreshData();
                            }
                        }, 2000);
                    }
                });
    }

    public <K, V> Map.Entry<K, V> getHead(LinkedTreeMap<K, V> map) {
        return map.entrySet().iterator().next();
    }

    public SpannableString setText(String s) {
        SpannableString sStr = new SpannableString(s);
        sStr.setSpan(new ForegroundColorSpan(ContextCompat.getColor(OnePartActivity.this, R.color.warm_grey_three)), 0, s.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new AbsoluteSizeSpan(14, true), 0, s.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sStr;
    }

    public LinkedTreeMap<String, Double> getNullMap() {
        LinkedTreeMap<String, Double> map = new LinkedTreeMap<>();
        map.put("null", 0.00);
        return map;
    }

    @OnClick({R.id.sp1, R.id.sp2, R.id.sp3, R.id.sp4, R.id.sp5, R.id.sp6, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sp1:
                content.setText("视频1");
                setDefaultTab();
                sp1.setTextColor(ContextCompat.getColor(this, R.color.black));
                break;
            case R.id.sp2:
                content.setText("视频2");
                setDefaultTab();
                sp2.setTextColor(ContextCompat.getColor(this, R.color.black));
                break;
            case R.id.sp3:
                content.setText("视频3");
                setDefaultTab();
                sp3.setTextColor(ContextCompat.getColor(this, R.color.black));
                break;
            case R.id.sp4:
                content.setText("视频4");
                setDefaultTab();
                sp4.setTextColor(ContextCompat.getColor(this, R.color.black));
                break;
            case R.id.sp5:
                content.setText("视频5");
                setDefaultTab();
                sp5.setTextColor(ContextCompat.getColor(this, R.color.black));
                break;
            case R.id.sp6:
                content.setText("视频6");
                setDefaultTab();
                sp6.setTextColor(ContextCompat.getColor(this, R.color.black));
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void setDefaultTab() {
        sp1.setTextColor(ContextCompat.getColor(this, R.color.common_grey));
        sp2.setTextColor(ContextCompat.getColor(this, R.color.common_grey));
        sp3.setTextColor(ContextCompat.getColor(this, R.color.common_grey));
        sp4.setTextColor(ContextCompat.getColor(this, R.color.common_grey));
        sp5.setTextColor(ContextCompat.getColor(this, R.color.common_grey));
        sp6.setTextColor(ContextCompat.getColor(this, R.color.common_grey));
    }
}