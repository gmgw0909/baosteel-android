package com.meetutech.baosteel.ui.info;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.ConfigBody;
import com.meetutech.baosteel.http.result.HtmlConfigResult;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import es.dmoral.toasty.Toasty;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.info
// Author: culm at 2017-04-27
//*********************************************************
@Route(path="/info/html")
public class CompanyViewActivity extends BaseActivity {

  public  @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.tv_header_back) TextView tv_header_back;
  public @BindView(R.id.wv_content) WebView wv_content;

  public @Autowired(name="title") String header_title;
  public @Autowired(name="key") String key;
  public @Autowired(name="isAuth") boolean isAuth;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_common_html);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    initViews();
    loadData();
  }

  private void loadData() {
    tv_header_title.setText(header_title);
    loadServerHtml();
    //wv_content.loadUrl(load_url);
  }

  private void loadServerHtml() {
    BProgressDialog.showProgressDialog(this);
    ConfigBody config=new ConfigBody(key,isAuth);
    new RestClient().getApiService().getHtmlConfigs(config.getHttpParams(), new Callback<HtmlConfigResult>() {
      @Override public void success(HtmlConfigResult htmlConfigResult, Response response) {
        BProgressDialog.dismissProgressDialog();
        wv_content.loadDataWithBaseURL("",htmlConfigResult.getData()[0].toHtml(),null,"utf-8",null);
      }

      @Override public void failure(RetrofitError error) {
        Toasty.error(CompanyViewActivity.this,getString(R.string.load_error), Toast.LENGTH_SHORT,true).show();
        BProgressDialog.dismissProgressDialog();
      }
    });
  }

  private void initViews() {
    ViewUtils.adjustViewTextSize(tv_header_back,38);
    ViewUtils.adjustViewTextSize(tv_header_title,35);
    wv_content.getSettings().setJavaScriptEnabled(true);
    wv_content.setWebViewClient(new WebViewClient() {
      @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        BProgressDialog.showProgressDialog(CompanyViewActivity.this);
      }

      @Override public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        BProgressDialog.dismissProgressDialog();
      }

      public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Toasty.error(CompanyViewActivity.this,getString(R.string.load_error), Toast.LENGTH_SHORT,true).show();
        BProgressDialog.dismissProgressDialog();
      }
    });
  }

  @OnClick(R.id.rl_header_back)
  public void onBackClick(){
    finish();
  }
}
