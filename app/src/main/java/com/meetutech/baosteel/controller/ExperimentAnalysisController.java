package com.meetutech.baosteel.controller;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.controller
// Author: culm at 2017-08-20
//*********************************************************

import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.AnalysisDataBody;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.body.SnapshotDataBody;
import com.meetutech.baosteel.http.body.SnapshotListInfoBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.model.http.FurnaceVariable;
import com.meetutech.baosteel.model.http.Power;
import com.meetutech.baosteel.model.http.PowerGroup;
import com.meetutech.baosteel.model.http.PowerType;
import com.meetutech.baosteel.model.http.Snapshot;
import com.meetutech.baosteel.ui.exp.ExperimentAnalysisDataActivity;
import com.meetutech.baosteel.utils.AppDBUtils;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ExperimentAnalysisController {

  public static final int SELECTED_DRAWABLE = R.drawable.bg_selector_label_ed;
  public static final int UNSELECTED_DRAWABLE = R.drawable.bg_selector_label;

  /**
   * 变量，快照，功况关系
   * 快照里包含对应功况和特性类型
   * 变量和快照ID对应查询数据
   * 功况列表作为X轴
   * Y轴数据从何而来
   * 线条数量如何确定
   *
   * Y轴应由变量和快照ID对应数据查询得出
   * 而快照ID应由特性类型ID组合而成
   * 线条数量由变量(n）*多种特性
   */

  private ExperimentAnalysisDataActivity activity;
  public List<Snapshot> snapshotList;
  private String experimentId;

  public ExperimentAnalysisController(ExperimentAnalysisDataActivity activity,
      String experimentId) {
    this.activity = activity;
    this.experimentId = experimentId;
  }

  public ExperimentAnalysisController(ExperimentAnalysisDataActivity activity,
      List<Snapshot> snapshotList) {
    this.activity = activity;
    this.snapshotList = snapshotList;
  }

  public Snapshot getSnapshotById(String id) {

    if (snapshotList == null | snapshotList.size() == 0) {
      return null;
    }

    for (Snapshot snapshot : snapshotList) {
      if (snapshot.getId().equals(id)) {
        return snapshot;
      }
    }

    return null;
  }

  public void refreshExpSnapshots() {
    BProgressDialog.showProgressDialog(activity);
    new RestClient().getApiService()
        .getSnapshotList(experimentId, new SnapshotListInfoBody(true).getHttpParams(),
            new Callback<CommonListResult<Snapshot>>() {
              @Override public void success(CommonListResult<Snapshot> res, Response response) {
                BProgressDialog.dismissProgressDialog();
                if (!TextUtils.isEmpty(res.getError()) || !res.isSuccess()) {
                  Toasty.warning(activity, activity.getString(R.string.load_snapshot_list_error),
                      Toast.LENGTH_SHORT).show();
                  return;
                }

                if (res.getData() == null || res.getData().size() == 0) {
                  Toasty.error(activity, activity.getString(R.string.load_snapshot_list_empty),
                      Toast.LENGTH_SHORT).show();
                  return;
                }
                snapshotList = res.getData();
              }

              @Override public void failure(RetrofitError error) {
                Toasty.warning(activity, activity.getString(R.string.load_snapshot_list_error),
                    Toast.LENGTH_SHORT).show();
                BProgressDialog.dismissProgressDialog();
              }
            });
  }

  public HashMap<String, Integer> pieRequestCount = new HashMap<>();
  public HashMap<String, List<SliceValue>> pieRequestValues = new HashMap<>();

  public void updatePieChartView(final LinearLayout mainView, List<Button> btn_vars,
      final Snapshot snapshots) {

    final int maxRequestCount = btn_vars.size();

    for (Button btn_var : btn_vars) {
      if (btn_var.getTag() == null) {
        continue;
      }
      String varId = (String) btn_var.getTag();
      new RestClient().getApiService()
          .querySnapshotData(new InfoBody(true).getHttpParams(),
              new SnapshotDataBody(snapshots.getId(), varId),
              new Callback<CommonListResult<HashMap<String, String>>>() {
                @Override public void success(CommonListResult<HashMap<String, String>> res,
                    Response response) {

                  int requestCount = pieRequestCount.get(snapshots.getId());
                  requestCount++;
                  pieRequestCount.put(snapshots.getId(), requestCount);
                  List<SliceValue> values = pieRequestValues.get(snapshots.getId());
                  if (!res.isSuccess() || res.getData() == null || res.getData().size() == 0) {
                    return;
                  }

                  SliceValue value = new SliceValue(getSnapshotValueByHashMap(res.getData().get(0)),
                      Color.parseColor(activity.filterColors[requestCount]));

                  FurnaceVariable variable = AppDBUtils.getFurnaceVariablesById(
                      getSnapshotVidByHashMap(res.getData().get(0)));

                  if (variable != null) {
                    value.setLabel(String.format("%s的值：%.2f", variable.getName(), value.getValue()));
                  }
                  values.add(value);

                  pieRequestValues.put(snapshots.getId(), values);

                  if (requestCount == maxRequestCount) {//开始组成数据
                    if (values.size() == 0) {
                      return;
                    }

                    TextView tv_name = new TextView(activity);
                    LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewUtils.getDimenByRateWidth(30));
                    tv_name.setTextColor(Color.BLACK);
                    params.gravity = Gravity.CENTER;
                    tv_name.setGravity(Gravity.CENTER);
                    ViewUtils.adjustViewTextSize(tv_name, ViewUtils.getDimenByRateWidth(10));
                    tv_name.setText(snapshots.getName());
                    tv_name.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                    tv_name.setSingleLine();
                    mainView.addView(tv_name, params);

                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewUtils.getDimenByRateWidth(300));
                    params.gravity = Gravity.CENTER;
                    mainView.addView(generatePieView(values), params);
                  }
                }

                @Override public void failure(RetrofitError error) {
                }
              });
    }
  }

  public String getSnapshotVidByHashMap(HashMap<String, String> hashMap) {
    String value = "";

    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
      if (entry.getKey().equals("varableId")) {
        value = entry.getValue();
        continue;
      }
    }

    return value;
  }

  public float getSnapshotValueByHashMap(HashMap<String, String> hashMap) {
    float value = 0.0f;

    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
      if (entry.getKey().equals("varableId")) {
        continue;
      }

      value = Float.parseFloat(entry.getValue());
      break;
    }

    return value;
  }

  public PieChartView generatePieView(List<SliceValue> values) {
    PieChartView pieChartView = new PieChartView(activity);

    PieChartData data = new PieChartData(values);
    data.setHasLabels(true);
    data.setHasLabelsOnlyForSelected(true);
    data.setHasLabelsOutside(true);
    data.setValueLabelsTextColor(Color.WHITE);

    pieChartView.setPieChartData(data);
    pieChartView.setCircleFillRatio(0.9f);

    return pieChartView;
  }

  public AnalysisDataBody generateQueryData(List<Button> btn_vars, List<List<Button>> btn_powers) {

    if (snapshotList == null) {
      refreshExpSnapshots();
    }

    AnalysisDataBody body = new AnalysisDataBody();
    List<AnalysisDataBody.Data> datas = new ArrayList<>();

    if (countAnalysisLines(btn_vars, btn_powers) <= 0) {
      return null;
    }

    if (btn_powers.size() <= 0) {
      return null;
    }
    //combo all snapshot filter
    List<VarPower> varPowerList = new ArrayList<>();

    List<Button> btn_subPowers = btn_powers.get(0);

    for (Button btn_var : btn_vars) {
      if (btn_var.getTag() == null) {
        continue;
      }
      String varId = (String) btn_var.getTag();
      List<String> powers = new ArrayList<>();

      for (Button btn_power : btn_subPowers) {
        if (btn_power.getTag() == null) {
          continue;
        }
        String powerId = (String) btn_power.getTag();
        powers.add(powerId);
      }

      if (powers.size() > 0) {
        varPowerList.add(new VarPower(varId, powers));
      }
    }
    //Toasty.success(activity, "开始数据分析", Toast.LENGTH_SHORT).show();

    //filter snapshot id

    for (VarPower varPower : varPowerList) {
      String varId = varPower.getVar();

      for (String power : varPower.getPowers()) {
        List<String> snapshotIds = new ArrayList<>();

        for (Snapshot snapshot : snapshotList) {
          if (!snapshot.isRelease() || snapshot.getWorkstat() == null) {
            continue;
          }
          //query workstat condition
          //boolean isVid = false;
          //for(HashMap<String,String> temp:snapshot.getTypes()){
          //  if(temp.containsKey(power)||temp.containsValue(power)){
          //    isVid=true;
          //    continue;
          //  }
          //}
          //
          //
          //if (!isVid) {
          //  continue;
          //}

          //query type
          boolean isPower = false;

          for (HashMap<String, String> sType : snapshot.getTypes()) {
            isPower = sType.containsValue(power) || sType.containsKey(power);
            if (isPower) {
              break;
            }
          }

          if (isPower) {
            snapshotIds.add(snapshot.getId());
          }
        }
        if (snapshotIds.size() == 0) {
          continue;
        }
        AnalysisDataBody.Data data = new AnalysisDataBody.Data();
        data.setVarableId(varId);
        data.setSnapshotids(snapshotIds);
        datas.add(data);
      }
    }

    if (datas.size() > 0) {
      body.setData(datas);
      return body;
    }

    return null;
  }

  private List<List<String>> generatePowerComboList(List<List<String>> currPowerTypes) {

    List<List<String>> data = new ArrayList<>();

    return data;
  }

  private String getTypeIdByPowerId(String powerId, PowerGroup powerGroup) {

    if (powerGroup == null || powerGroup.getTypes() == null || powerGroup.getTypes().size() == 0) {
      return null;
    }

    for (PowerType powerType : powerGroup.getTypes()) {

      for (Power power : powerType.getPowers()) {
        if (power.getId().equals(powerId)) {
          return powerType.getId();
        }
      }
    }

    return null;
  }

  public String getSnapshotIdByPowerData(List<String> powerId, String typeId,
      List<Snapshot> snapshotList) {
    for (Snapshot snapshot : snapshotList) {
      for (HashMap<String, String> types : snapshot.getTypes()) {
        if (types.get(powerId) != null) {

        }
      }
    }
    return null;
  }

  /**
   * 计算数据分析线条数
   */
  public int countAnalysisLines(List<Button> btn_variable, List<List<Button>> btn_powers) {

    int count = 0;
    int var_count = 0;
    for (Button btn : btn_variable) {
      if (btn.getTag() != null) {
        var_count++;
      }
    }

    if (var_count == 0) {
      return count;
    }

    int type_counts = 1;

    for (List<Button> btns : btn_powers) {
      int curr_power_count = 0;
      for (Button subBtn : btns) {
        if (subBtn.getTag() != null) {
          curr_power_count++;
        }
      }
      if (curr_power_count == 0) {
        return count;
      }
      type_counts = type_counts * curr_power_count;
    }

    count = var_count * type_counts;

    return count;
  }

  public class VarPower {
    private String var;
    private List<String> powers;

    public VarPower(String variableId, List<String> querySubPower) {
      this.var = variableId;
      this.powers = querySubPower;
    }

    public String getVar() {
      return var;
    }

    public void setVar(String var) {
      this.var = var;
    }

    public List<String> getPowers() {
      return powers;
    }

    public void setPowers(List<String> powers) {
      this.powers = powers;
    }
  }
}
