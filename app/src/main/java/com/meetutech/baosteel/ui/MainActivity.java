package com.meetutech.baosteel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.adapter.ProjectInfoViewPageAdapter;
import com.meetutech.baosteel.common.AppConstant;
import com.meetutech.baosteel.common.HTTPConstant;
import com.meetutech.baosteel.model.http.Infos;
import com.meetutech.baosteel.model.http.ProjectMenu;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hugeterry.updatefun.UpdateFunGO;
import cn.hugeterry.updatefun.config.UpdateKey;

@Route(path = "/main/start")
public class MainActivity extends BaseActivity
        implements View.OnClickListener {

    public static MainActivity instance;

/*  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.vp_content) RollPagerView vp_content;
  //public @BindView(R.id.indicator) CircleIndicator indicator;
  public @BindView(R.id.list_exp) ListView list_exp;*/

    protected View menuView;
    protected ProjectInfoViewPageAdapter vp_adapter;

    public List<String> listDescription;
    public List<View> pageViews;
    private SlidingMenu slidingMenu;
    private List<Infos<Infos.Html>> infos;
    private List<ProjectMenu> menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFirimUpdater();
        instance = this;
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
/*    initViews();
    loadViews();
    initViewPager();*/
//    BProgressDialog.showProgressDialog(this);
    }

    private void initFirimUpdater() {
        UpdateKey.API_TOKEN = AppConstant.FIR_IM_API_TOKEN;
        UpdateKey.APP_ID = AppConstant.FIR_IM_APP_ID;
        UpdateKey.DialogOrNotification = UpdateKey.WITH_DIALOG;
        UpdateFunGO.init(this);
    }

/*  private void loadViews() {
    tv_header_title.setText(R.string.txt_project_title);
  }*/

/*  private void initViews() {
    menus = new ArrayList<>();
    updateSlidingMenus(menus);
    vp_content.setHintView(new ColorPointHintView(this, Color.WHITE, Color.LTGRAY));
    initEvent();
    loadNetworkData();
  }*/

/*  private void loadNetworkData() {
    BProgressDialog.showProgressDialog(this);
    //init Project Info and IntroBanner
    new RestClient().getApiService()
        .getProjectList(new ListFilterBody(true).getHttpParams(),
            new Callback<CommonListResult<Project>>() {
              @Override public void success(CommonListResult<Project> res, Response response) {
                BProgressDialog.dismissProgressDialog();
                if (res.isSuccess()) {
                  initListAdapter(res.getData());
                } else {
                  Toasty.warning(MainActivity.this, getString(R.string.load_project_list_error),
                      Toast.LENGTH_SHORT).show();
                  UserUtils.logoutTimeout(MainActivity.this);
                }
                AppDBUtils.updateProjectList(res.getData());
              }

              @Override public void failure(RetrofitError error) {
                BProgressDialog.dismissProgressDialog();
                //if (error.getResponse().getStatus() == 401) {
                Toasty.warning(MainActivity.this, getString(R.string.login_token_expire_error),
                    Toast.LENGTH_SHORT, true).show();
                UserUtils.logoutTimeout(MainActivity.this);
                //} else {
                //  Toasty.warning(MainActivity.this, getString(R.string.load_project_list_error),
                //      Toast.LENGTH_SHORT).show();
                //  UserUtils.logoutTimeout(MainActivity.this);
                //}
              }
            });
    new RestClient().getApiService()
        .getIntroBanner(new InfoBody(true).getHttpParams(),
            new Callback<CommonObjectResult<IntroBannerData>>() {
              @Override public void success(CommonObjectResult<IntroBannerData> introBannerResult,
                  Response response) {

                if (!introBannerResult.isSuccess()) {
                  Toasty.error(MainActivity.instance, getString(R.string.logout_timeout),
                      Toast.LENGTH_SHORT).show();
                  UserUtils.logoutTimeout(MainActivity.this);
                  return;
                }

                if (introBannerResult.getData() == null
                    || introBannerResult.getData().getContent() == null
                    || introBannerResult.getData().getContent().getBannerkeys() == null) {
                  return;
                }

                final List<String> bannerKeys =
                    introBannerResult.getData().getContent().getBannerkeys();
                infos.clear();
                menus.clear();
                for (String bk : bannerKeys) {
                  new RestClient().getApiService()
                      .getInfoByKey(bk, new InfoBody(true).getHttpParams(),
                          new Callback<CommonObjectResult<Infos<Infos.Html>>>() {
                            @Override public void success(CommonObjectResult<Infos<Infos.Html>> res,
                                Response response) {
                              if(res==null||res.getData()==null){
                                return;
                              }
                              infos.add(res.getData());
                              ProjectMenu menu = new ProjectMenu(res.getData().getName(),
                                  HTTPConstant.SUB_INFO_URL, res.getData().getKey());
                              menus.add(menu);
                              if (infos.size() == bannerKeys.size()) {
                                updateViewPager(infos);
                                updateSlidingMenus(menus);
                                return;
                              }
                            }

                            @Override public void failure(RetrofitError error) {

                            }
                          });
                }
              }

              @Override public void failure(RetrofitError error) {

              }
            });
    //update variable list
    new RestClient().getApiService().getFurnaceVariables(new InfoBody(true).getHttpParams(),
        new Callback<CommonListResult<FurnaceVariable>>() {
          @Override
          public void success(CommonListResult<FurnaceVariable> res,
              Response response) {
            if(!res.isSuccess()||!TextUtils.isEmpty(res.getError())||res.getData()==null){
              Toasty.warning(MainActivity.this,
                  TextUtils.isEmpty(res.getError())?getString(R.string.get_var_list_error):res.getError(),Toast.LENGTH_SHORT).show();
              return;
            }
            AppDBUtils.updateFurnaceVariables(res.getData());
          }

          @Override public void failure(RetrofitError error) {
            Toasty.warning(MainActivity.this,getString(R.string.get_var_list_error),Toast.LENGTH_SHORT).show();
          }
        });
  }*/

/*  private void initListAdapter(List<Project> data) {
    list_exp.setAdapter(new ProjectListAdapter(this, data));
  }*/

/*  private void initViewPager() {
    infos = new ArrayList<>();
    updateViewPager(infos);
    //indicator.setViewPager(vp_content);
  }*/

/*  private void updateViewPager(List<Infos<Infos.Html>> infos) {
    pageViews = generatePageViews(infos);
    vp_adapter = new ProjectInfoViewPageAdapter(this, pageViews, listDescription);
    vp_content.setAdapter(vp_adapter);
  }*/

    private List<View> generatePageViews(final List<Infos<Infos.Html>> data) {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            View item = View.inflate(this, R.layout.item_project_view_pager, null);
            ViewUtils.adjustViewTextSize((TextView) item.findViewById(R.id.tv_description), 25);
            Picasso.with(this)
                    .load(data.get(i).getImageFullUrl())
                    .fit()
                    .into((ImageView) item.findViewById(R.id.iv_content));
            ((TextView) item.findViewById(R.id.tv_description)).setText(data.get(i).getName());
            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance()
                            .build(HTTPConstant.SUB_INFO_URL)
                            .withString("key", data.get(finalI).getKey())
                            .withString("title", data.get(finalI).getName())
                            .withBoolean("isAuth", true)
                            .navigation(MainActivity.this);
                }
            });
            views.add(item);
        }
        return views;
    }

/*  private void initEvent() {
    findViewById(R.id.btn_header_right).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        slidingMenu.showMenu(true);
      }
    });
  }*/

/*  private void updateSlidingMenus(List<ProjectMenu> menus) {
    ViewUtils.adjustViewTextSize(tv_header_title, 38);
    menuView = new ProjectMenuView(this, menus).build(this);
    initSlidingMenus(menuView);
  }*/

    private void initSlidingMenus(View mainView) {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setBehindWidth(ViewUtils.getDimenByRateWidth(288));
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(mainView);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_message:
                ARouter.getInstance().build("/system/notification").navigation(this);
                break;
            case R.id.btn_setting:
                ARouter.getInstance().build("/system/setting").navigation(this);
                break;
            default:
                handleInfoPages(view);
                break;
        }
    }

    private void handleInfoPages(View v) {
        ARouter.getInstance().build((String) v.getTag()).navigation(this);
    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateFunGO.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UpdateFunGO.onStop(this);
    }

    @OnClick({R.id.btJS, R.id.btSM, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btJS:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class)
                        .putExtra("type", 2));
                break;
            case R.id.btSM:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class)
                        .putExtra("type", 3));
                break;
            case R.id.b1:
                startActivity(new Intent(MainActivity.this, AllPartActivity.class));
                break;
            case R.id.b2:
                startActivity(new Intent(MainActivity.this, OnePartActivity.class)
                        .putExtra("TITLE", "大功率明火烧嘴"));
                break;
            case R.id.b3:
                startActivity(new Intent(MainActivity.this, OnePartActivity.class)
                        .putExtra("TITLE", "中功率明火烧嘴"));
                break;
            case R.id.b4:
                startActivity(new Intent(MainActivity.this, OnePartActivity.class)
                        .putExtra("TITLE", "小功率明火烧嘴"));
                break;
            case R.id.b5:
                startActivity(new Intent(MainActivity.this, OnePartActivity.class)
                        .putExtra("TITLE", "U型辐射管烧嘴"));
                break;
            case R.id.b6:
                startActivity(new Intent(MainActivity.this, OnePartActivity.class)
                        .putExtra("TITLE", "W型辐射管烧嘴"));
                break;
            case R.id.b7:
                startActivity(new Intent(MainActivity.this, OnePartActivity.class)
                        .putExtra("TITLE", "I型辐射管烧嘴"));
                break;
            case R.id.b8:
                startActivity(new Intent(MainActivity.this, OnePartActivity.class)
                        .putExtra("TITLE", "点火枪"));
                break;
        }
    }
}
