package com.meetutech.baosteel.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.meetutech.baosteel.R;
import com.meetutech.baosteel.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.web)
    WebView mWebView;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_web_html);
        ButterKnife.bind(this);
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setLoadsImagesAutomatically(true);
        int type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            tvTitle.setText("公司介绍");
            mWebView.loadUrl("file:///android_asset/bao1.html");
        } else if (type == 2) {
            tvTitle.setText("实验室介绍");
            mWebView.loadUrl("file:///android_asset/bao2.html");
        } else if (type == 3) {
            tvTitle.setText("实验说明");
            mWebView.loadUrl("file:///android_asset/bao3.html");
        }
    }

    @OnClick({R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
