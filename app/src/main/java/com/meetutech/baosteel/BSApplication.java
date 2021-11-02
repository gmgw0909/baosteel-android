package com.meetutech.baosteel;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel
// Author: culm at 2017-04-25
//*********************************************************

import android.app.Application;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.alibaba.android.arouter.launcher.ARouter;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;
import com.meetutech.baosteel.common.AppConstant;
import com.meetutech.baosteel.utils.ImageLoaderUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import com.ximalaya.ting.android.miyataopensdk.XmUISdk;
import com.yixia.camera.VCamera;

import java.io.File;

public class BSApplication extends Application {

    public static final String TAG = "BaoSteel Application";

    public static BSApplication instance;

    private static DisplayMetrics displayMetrics;

    public static String mainUserId = "";

    /**
     * UI Resource
     */
    public static BProgressDialog pb_dialog;
    public static boolean managersInitialized;
    public static String currAuthToken;
    public static String VIDEO_PATH;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initModules();
        String mAppSecret, mAppKey, mPackId;
        mAppSecret = "3b8bd8ff0278fff9e174f1ac5ce8010d";
        mAppKey = "2c2cfad6a0cc6def2dca4b8f0f8e5416";
        mPackId = "12345";
        XmUISdk.getInstance().init(this, mAppSecret, mAppKey, mPackId);
    }

    public static BSApplication getInstance() {
        return instance;
    }

    /**
     * Init Module
     */
    private void initModules() {
        if (!managersInitialized) {
            if (AppConstant.DEBUG) {
                ARouter.openDebug();
                ARouter.openLog();
            }
            ARouter.init(this);
            Iconify
                    .with(new FontAwesomeModule())
                    .with(new EntypoModule())
                    .with(new TypiconsModule())
                    .with(new MaterialModule())
                    .with(new MaterialCommunityModule())
                    .with(new MeteoconsModule())
                    .with(new WeathericonsModule())
                    .with(new SimpleLineIconsModule())
                    .with(new IoniconsModule());
            initMediaCache();
            //managersInitialized=true;
        }
    }

    private void initMediaCache() {
        VIDEO_PATH = String.format("%s/%s/%s", Environment.getExternalStorageDirectory()
                , AppConstant.CACHE_DIRECTORY_PATH, AppConstant.CACHE_TYPE_VIDEO);
        File file = new File(VIDEO_PATH);
        if (!file.exists()) file.mkdirs();
        if (!AppConstant.DEBUG) {
            VCamera.setVideoCachePath(VIDEO_PATH);
            VCamera.setDebugMode(true);
        }
    }

    public static DisplayMetrics getDisplayMetrics() {
        return displayMetrics;
    }

    public static void setDisplayMetrics(DisplayMetrics displayMetrics) {
        BSApplication.displayMetrics = displayMetrics;
    }
}
