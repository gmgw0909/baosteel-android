package com.meetutech.baosteel.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meetutech.baosteel.R;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.result.AllPartResult2;
import com.meetutech.baosteel.http.result.CommonResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AllTabPage2Fragment extends Fragment {
    int type;

    public static AllTabPage2Fragment newInstance(int type) {
        Bundle args = new Bundle();
        AllTabPage2Fragment fragment = new AllTabPage2Fragment();
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
        View rootView = inflater.inflate(R.layout.act_all_page2, container, false);
        ButterKnife.bind(this, rootView);
        initViewData();
//        if (result != null) {
//            tv_11.setText("排烟温度:  " + result.getExhaustTemperature());
//            tv_12.setText("助燃风温度:  " + result.getCombustionAirTemperature());
//            tv_21.setText("排烟压力:  " + result.getExhaustPressure());
//            tv_22.setText("助燃风压力:  " + result.getCombustionAirPressure());
//            tv_31.setText("烟气成分O2:  " + result.getSmokeCompositionO2());
//            tv_32.setText("火盘温度:  " + result.getFirePlateTemperature());
//            tv_41.setText("烟气成分CO2:  " + result.getSmokeCompositionCO2());
//            tv_42.setText("火盘压力:  " + result.getFirePlatePressure());
//            tv_51.setText("烟气成分CO:  " + result.getSmokeCompositionCO());
//            tv_52.setText("火焰温度:  " + result.getFlameTemperature());
//            tv_61.setText("烟气成分NO:  " + result.getSmokeCompositionNO());
//            tv_62.setText("火焰压力:  " + result.getFlamePressure());
//            tv_71.setText("烟气成分NO2:  " + result.getSmokeCompositionNO2());
//            tv_72.setText("入口烟气温度:  " + result.getInletFlueGasTemperature());
//            tv_81.setText("烟气成分NOx:  " + result.getSmokeCompositionNOx());
//            tv_82.setText("入口烟气压力:  " + result.getInletFlueGasPressure());
//            tv_91.setText("出口烟气温度:  " + result.getOutletFlueGasTemperature());
//            tv_92.setText("入口空气温度:  " + result.getInletAirTemperature());
//            tv_101.setText("出口烟气压力:  " + result.getOutletFlueGasPressure());
//            tv_102.setText("入口空气压力:  " + result.getInletAirPressure());
//            tv_111.setText("出口空气温度:  " + result.getOutletAirTemperature());
//            tv_112.setText("出口空气压力:  " + result.getOutletAirPressure());
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
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }
}
