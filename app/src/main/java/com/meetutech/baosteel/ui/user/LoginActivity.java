package com.meetutech.baosteel.ui.user;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.user
// Author: culm at 2017-04-25
//*********************************************************

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.controller.LoginController;
import com.meetutech.baosteel.exception.MTValidException;
import com.meetutech.baosteel.model.form.LoginForm;
import com.meetutech.baosteel.ui.MainActivity;
import com.meetutech.baosteel.ui.WebViewActivity;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.BDPshUtils;
import com.meetutech.baosteel.utils.PermissionUtils;
import com.meetutech.baosteel.utils.TypeFaceUtils;
import com.meetutech.baosteel.utils.UserUtils;
import com.meetutech.baosteel.utils.ViewUtils;

import es.dmoral.toasty.Toasty;

@Route(path = "/user/login")
public class LoginActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_about)
    Button btn_about;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;


    //private String SPLASH_BG_URL="";
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//    checkLogin();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        PermissionUtils.checkWriteDiskPermissionStatus(this);
        loginController = new LoginController(this);
//        initViews();
        loadData();
        et_phone.setText("18818208063");
        et_password.setText("18818208063");
    }

    private void checkLogin() {
        if (UserUtils.checkAccountLogin()) {
            UserUtils.applyUserConfig();
            ARouter.getInstance().build("/main/start").navigation(this);
            finish();
        } else {
            BDPshUtils.unBindForApp(this);
        }
    }

    private void loadData() {

    }

    private void initViews() {
        ViewUtils.adjustViewTextSize(tv_title, 39);
        ViewUtils.adjustViewTextSize(btn_about, 35);
        ViewUtils.adjustViewTextSize(btn_login, 35);
        ViewUtils.adjustViewTextSize(et_phone, 35);
        ViewUtils.adjustViewTextSize(et_password, 35);
        ViewUtils.setTypeFace(this, tv_title, TypeFaceUtils.SF_UI_TEXT_SEMIBOLD);
        ViewUtils.setTypeFace(this, btn_about, TypeFaceUtils.SF_UI_TEXT_SEMIBOLD);
        ViewUtils.setTypeFace(this, btn_login, TypeFaceUtils.SF_UI_TEXT_SEMIBOLD);
        ViewUtils.setTypeFace(this, et_phone, TypeFaceUtils.SF_UI_TEXT_SEMIBOLD);
        ViewUtils.setTypeFace(this, et_password, TypeFaceUtils.SF_UI_TEXT_SEMIBOLD);
    }

    @OnClick(R.id.btn_about)
    public void onAbout() {
//    ARouter.getInstance().build("/info/html")
//        .withString("key","company_intro")
//        .withString("title","公司简介")
//        .withBoolean("isAuth",false)
//        .navigation(this);
        startActivity(new Intent(LoginActivity.this, WebViewActivity.class)
                .putExtra("type", 1));
    }

    @OnClick(R.id.btn_login)
    public void onLogin(Button btn) {
//    showLoginForm();
        ARouter.getInstance().build("/main/start").navigation(this);
        finish();
    }

    private boolean isPwd = true;// 输入框密码是否是隐藏的，默认为true

    @OnClick(R.id.bt_eyes)
    public void password(View btn) {
        if (isPwd) {
            et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isPwd = false;
        } else {
            et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isPwd = true;
        }
    }

    private void showLoginForm() {
        btn_login.setEnabled(false);
        if (et_password.getVisibility() == View.VISIBLE) {
            submitLogin();
            btn_login.setEnabled(true);
            return;
        }
    }

    private void submitLogin() {
        LoginForm loginForm = new LoginForm(et_phone.getText().toString().trim(),
                et_password.getText().toString().trim());
        try {
            if (!loginController.checkLoginForm(loginForm)) {
                return;
            }
            loginController.submitLogin(loginForm);
        } catch (MTValidException e) {
            e.printStackTrace();
            Toasty.error(this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

/*  @OnClick(R.id.iv_bg) public void onIntroduce(View v) {
    showIntroduce();
  }*/


/*
  private void showIntroduce() {
    if (et_password.getVisibility() != View.VISIBLE) {
      return;
    }
    YoYo.with(Techniques.SlideOutUp).duration(500).playOn(et_phone);
    YoYo.with(Techniques.FadeOut).duration(300).playOn(et_phone);
    YoYo.with(Techniques.SlideOutUp).duration(500).playOn(et_password);
    YoYo.with(Techniques.FadeOut)
        .duration(300)
        .withListener(animatorListenerIntro)
        .playOn(et_password);
  }
*/

    Animator.AnimatorListener animatorListenerIntro = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            YoYo.with(Techniques.FadeIn).duration(300).playOn(btn_about);
            et_password.setVisibility(View.INVISIBLE);
            et_phone.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };
    Animator.AnimatorListener animatorListenerLogin = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            et_password.setVisibility(View.VISIBLE);
            et_phone.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeIn).duration(300).playOn(et_phone);
            YoYo.with(Techniques.FadeIn).duration(300).playOn(et_password);
            btn_login.setEnabled(true);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };
}
