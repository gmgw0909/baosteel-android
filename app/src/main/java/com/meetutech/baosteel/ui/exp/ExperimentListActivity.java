package com.meetutech.baosteel.ui.exp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.adapter.ProjectExpListAdapter;
import com.meetutech.baosteel.adapter.ProjectInfoViewPageAdapter;
import com.meetutech.baosteel.common.HTTPConstant;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.ExpListInfoBody;
import com.meetutech.baosteel.http.body.ProjectInfoBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.model.DBUtils;
import com.meetutech.baosteel.model.data.Project;
import com.meetutech.baosteel.model.http.ExperimentInfo;
import com.meetutech.baosteel.model.http.Furnace;
import com.meetutech.baosteel.model.http.ProjectDetails;
import com.meetutech.baosteel.model.http.ProjectMenu;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.AppDBUtils;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.menu.ProjectMenuView;
import com.squareup.picasso.Picasso;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@Route(path = "/experiment/list") public class ExperimentListActivity extends BaseActivity
    implements View.OnClickListener {

  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.tv_header_back) TextView tv_header_back;
  public @BindView(R.id.vp_content) RollPagerView vp_content;
  public @BindView(R.id.rl_header_back) View rl_header_back;
  //public @BindView(R.id.indicator) CircleIndicator indicator;
  public @BindView(R.id.list_exp) ListView list_exp;

  protected View menuView;
  protected ProjectInfoViewPageAdapter vp_adapter;

  public List<ProjectMenu> menuData;
  public List<String> listDescription;
  public List<View> pageViews;
  private SlidingMenu slidingMenu;

  //View Params
  public @Autowired(name = "projectID") String projectID;
  public Project currProject;

  public @Autowired(name = "title") String title;
  public @Autowired(name = "longTerm") boolean longTerm;

  //View Data
  private List<ProjectDetails> projectDetailses;
  private List<ProjectDetails> projectMenus;
  private List<Furnace> furnaces;
  private List<ExperimentInfo> experimentInfos;
  private ProjectExpListAdapter listAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_experiment_list);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    initViews();
    loadViews();
    loadNetworkData();
  }

  private void loadNetworkData() {
    currProject= AppDBUtils.getProjectSimpleInfo(projectID);
    new RestClient().getApiService()
        .getProjectInfo(projectID, new ProjectInfoBody(true).getHttpParams(),
            new Callback<CommonListResult<ProjectDetails>>() {
              @Override
              public void success(CommonListResult<ProjectDetails> res, Response response) {
                if (!res.isSuccess()) {
                  Toasty.error(ExperimentListActivity.this,
                      getString(R.string.load_project_info_error), Toast.LENGTH_SHORT, true).show();
                  return;
                }
                projectDetailses.clear();
                projectDetailses.addAll(res.getData());
                updateViewPager(projectDetailses);
                updateSlidingMenus(projectDetailses);
              }

              @Override public void failure(RetrofitError error) {
              }
            });

    new RestClient().getApiService()
        .getProjectExperiment(projectID, new ExpListInfoBody(true).getHttpParams(),
            new Callback<CommonListResult<ExperimentInfo>>() {
              @Override
              public void success(CommonListResult<ExperimentInfo> res, Response response) {
                if (!res.isSuccess()) {
                  Toasty.error(ExperimentListActivity.this, getString(R.string.load_error),
                      Toast.LENGTH_SHORT).show();
                  return;
                }
                experimentInfos.clear();
                experimentInfos.addAll(res.getData());

                listAdapter.notifyDataSetChanged();
                //更新缓存
                DBUtils.updateExperimentList(res.getData());
              }

              @Override public void failure(RetrofitError error) {
                Toasty.error(ExperimentListActivity.this, getString(R.string.get_exp_list_error),
                    Toast.LENGTH_SHORT).show();
                error.printStackTrace();
              }
            });
  }

  private void updateSlidingMenus(List<ProjectDetails> menus) {
    menuView = new ProjectMenuView(this, generateMenus(menus)).build(this);
    initSlidingMenus(menuView);
  }

  private void loadViews() {
    tv_header_title.setText(title);
    tv_header_back.setText(getString(R.string.txt_project_title));
    projectDetailses = new ArrayList<>();
    updateViewPager(projectDetailses);
  }

  private void updateViewPager(List<ProjectDetails> infos) {
    List<ProjectDetails> currInfos=new ArrayList<>();
    for(ProjectDetails p:infos){
      if(p.getContent()==null){
        continue;
      }
      currInfos.add(p);
    }
    vp_content.setVisibility(currInfos == null || currInfos.size() == 0 ? View.GONE : View.VISIBLE);
    pageViews = generatePageViews(currInfos);
    vp_adapter = new ProjectInfoViewPageAdapter(this, pageViews, listDescription);
    vp_content.setAdapter(vp_adapter);
  }

  private void initViews() {
    vp_content.setHintView(new ColorPointHintView(this, Color.WHITE, Color.LTGRAY));
    initMenus();
    initEvent();
    initViewPager();
    initListAdapter();
  }

  private void initListAdapter() {
    experimentInfos = new ArrayList<>();
    furnaces = new ArrayList<>();
    listAdapter = new ProjectExpListAdapter(this, experimentInfos, title,projectID,longTerm);
    list_exp.setAdapter(listAdapter);
  }

  private void initViewPager() {
    projectDetailses = new ArrayList<>();
    updateViewPager(projectDetailses);
    //indicator.setViewPager(vp_content);
  }

  private List<View> generatePageViews(final List<ProjectDetails> data) {
    List<View> views = new ArrayList<>();
    if (data == null) {
      return views;
    }
    for (int i = 0; i < data.size(); i++) {
      View item = View.inflate(this, R.layout.item_exp_view_pager, null);
      ViewUtils.adjustViewTextSize((TextView) item.findViewById(R.id.tv_description), 25);
      Picasso.with(this)
          .load(data.get(i).getImageFullUrl())
          .fit()
          .into((ImageView) item.findViewById(R.id.iv_content));
      ((TextView) item.findViewById(R.id.tv_description)).setText(data.get(i).getName());
      final int finalI = i;
      item.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          ARouter.getInstance()
              .build(HTTPConstant.SUB_INFO_URL)
              .withString("key", data.get(finalI).getKey())
              .withString("title", data.get(finalI).getName())
              .withBoolean("isAuth", true)
              .withString("content", data.get(finalI).getContent().getHtml())
              .navigation(ExperimentListActivity.this);
        }
      });
      views.add(item);
    }
    return views;
  }

  private void initEvent() {
    findViewById(R.id.btn_header_right).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        slidingMenu.showMenu(true);
      }
    });
    rl_header_back.setOnClickListener(this);
  }

  private void initMenus() {
    projectMenus = new ArrayList<>();
    ViewUtils.adjustViewTextSize(tv_header_title, 38);
    updateSlidingMenus(projectMenus);
  }

  private List<ProjectMenu> generateMenus(List<ProjectDetails> data) {
    menuData = new ArrayList<>();
    if (data == null) {
      return menuData;
    }
    for (ProjectDetails item : data) {
      if(item.getContent()==null){
        continue;
      }
      menuData.add(new ProjectMenu(item.getName(), HTTPConstant.SUB_INFO_URL, item.getKey(),
          item.getContent().getHtml(),currProject));
    }

    if (!longTerm) {
      menuData.add(new ProjectMenu("报告下载", RouterConstant.REPORT_DOWNLOAD_VIEW,currProject));
      menuData.add(new ProjectMenu("专家意见", "/expert/advice"));
    }
    return menuData;
  }

  private void initSlidingMenus(View mainView) {
    slidingMenu = new SlidingMenu(this);
    slidingMenu.setMode(SlidingMenu.RIGHT);
    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
    slidingMenu.setFadeDegree(0.35f);
    slidingMenu.setBehindWidth(ViewUtils.getDimenByRateWidth(288));
    //menu.setMinimumWidth(ViewUtils.getDimenByRateWidth(288));
    slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
    slidingMenu.setMenu(mainView);
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_message:
        ARouter.getInstance()
            .build("/system/notification")
            .withString("projectId", projectID)
            .navigation(this);
        break;
      case R.id.btn_setting:
        ARouter.getInstance().build("/system/setting").navigation(this);
        break;
      case R.id.rl_header_back:
        finish();
        break;
      default:
        handleInfoPages(view);
        break;
    }
  }

  private void handleInfoPages(View v) {
    ARouter.getInstance()
        .build((String) v.getTag())
        .withString("projectId", projectID)
        .navigation(this);
  }
}
