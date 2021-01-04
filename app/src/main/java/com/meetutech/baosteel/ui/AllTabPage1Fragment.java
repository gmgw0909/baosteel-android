package com.meetutech.baosteel.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meetutech.baosteel.R;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.result.AllPartResult2;
import com.meetutech.baosteel.http.result.CommonResult;
import com.meetutech.baosteel.ui.adapter.AllPartAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AllTabPage1Fragment extends Fragment {
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    int type;
    AllPartAdapter adapter;

    public static AllTabPage1Fragment newInstance(int type) {
        Bundle args = new Bundle();
        AllTabPage1Fragment fragment = new AllTabPage1Fragment();
        args.putSerializable("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.act_all_page, container, false);
        ButterKnife.bind(this, rootView);
        initViewData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new AllPartAdapter(getActivity()));
//        if (result != null) {
//            tv11.setText("高炉煤气流量:  " + result.getBlastFurnaceGasFlow());
//            tv12.setText("主空气流量:  " + result.getMainAirFlow());
//            tv21.setText("高炉煤气压力:  " + result.getBlastFurnaceGasPressure());
//            tv22.setText("空气小流量:  " + result.getSmallAirFlow());
//            tv31.setText("焦炉煤气流量:  " + result.getCokeOvenGasFlow());
//            tv32.setText("空气微流量:  " + result.getAirFlow());
//            tv41.setText("焦炉煤气压力:  " + result.getCokeOvenGasPressure());
//            tv42.setText("空气压力1:  " + result.getAirPressure1());
//            tv51.setText("转炉煤气流量:  " + result.getConverterGasFlow());
//            tv52.setText("空气压力2:  " + result.getAirPressur2());
//            tv61.setText("转炉煤气压力:  " + result.getConverterGasPressure());
//            tv62.setText("空气压力3:  " + result.getAirPressur3());
//            tv71.setText("天然气流量:  " + result.getNaturalGasFlow());
//            tv72.setText("空气压力4:  " + result.getAirPressure4());
//            tv81.setText("天然气压力:  " + result.getNaturalGasPressure());
//            tv82.setText("空气压力5:  " + result.getAirPressure5());
//            tv91.setText("氮气流量:  " + result.getNitrogenFlow());
//            tv92.setText("空气压力6:  " + result.getAirPressure6());
//            tv101.setText("氮气压力:  " + result.getNitrogenPressure());
//            tv102.setText("煤气压力1:  " + result.getGasPressure1());
//            tv111.setText("软水流量:  " + result.getSoftWaterFlow());
//            tv112.setText("煤气压力2:  " + result.getGasPressure2());
//            tv121.setText("软水压力:  " + result.getSoftWaterPressure());
//            tv122.setText("煤气压力3:  " + result.getGasPressure3());
//            tv131.setText("烟道温度:  " + result.getFlueTemperature());
//            tv132.setText("煤气压力4:  " + result.getGasPressure4());
//            tv141.setText("烟道压力:  " + result.getFluePressure());
//            tv142.setText("煤气压力5:  " + result.getGasPressure5());
//            tv151.setText("天然气小流量计:  " + result.getNaturalGasSmallFlowMeter());
//            tv152.setText("煤气压力6:  " + result.getGasPressure6());
//            tv161.setText("天然气微流量计:  " + result.getNaturalGasMicroFlowMeter());
//            tv162.setText("负压1:  " + result.getNegativePressure1());
//            tv171.setText("焦炉煤气小流量:  " + result.getsmallFlowOfCokeOvenGas());
//            tv172.setText("负压2:  " + result.getNegativePressure2());
//            tv181.setText("焦炉煤气微流量:  " + result.getCokeOvenGasMicroFlow());
//            tv182.setText("负压3:  " + result.getNegativePressure3());
//            tv191.setText("负压5:  " + result.getNegativePressure5());
//            tv192.setText("负压4:  " + result.getNegativePressure4());
//            tv201.setText("负压6:  " + result.getNegativePressure6());
//            tv202.setText("");
//        }
        return rootView;
    }


    private void initViewData() {
        Map<String, Object> body = new HashMap<>();
        body.put("type", type);
        new RestClient().getApiService()
                .comprehensiveMenu(body, new Callback<CommonResult<List<AllPartResult2>>>() {
                    @Override
                    public void success(CommonResult<List<AllPartResult2>> commonResult, Response response) {
                        if (commonResult != null && commonResult.getResult() != null) {
                            List<AllPartResult2> result = commonResult.getResult();
                            adapter.setNewData(result);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }
}
