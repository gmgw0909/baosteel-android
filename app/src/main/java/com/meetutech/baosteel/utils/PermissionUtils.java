package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-08-11
//*********************************************************

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;
import com.kishan.askpermission.PermissionInterface;
import com.meetutech.baosteel.R;
import es.dmoral.toasty.Toasty;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PermissionUtils {
  public static final int RECORD_AUDIO = 0;

  @Retention(RetentionPolicy.SOURCE)
  @IntDef({GRANTED, DENIED, BLOCKED_OR_NEVER_ASKED })
  public @interface PermissionStatus {}

  public static final int GRANTED = 0;
  public static final int DENIED = 1;
  public static final int BLOCKED_OR_NEVER_ASKED = 2;

  @PermissionStatus
  public static int getPermissionStatus(Activity activity, String androidPermissionName) {
    if(ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
      if(!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)){
        return BLOCKED_OR_NEVER_ASKED;
      }
      return DENIED;
    }
    return GRANTED;
  }
  public static void checkWriteDiskPermissionStatus(final Activity activity){
    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      new AskPermission.Builder(activity).setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
          .setCallback(new PermissionCallback() {
            @Override public void onPermissionsGranted(int i) {

            }

            @Override public void onPermissionsDenied(int i) {
              Toasty.error(activity,activity.getString(R.string.error_access_storage_permission), Toast.LENGTH_SHORT).show();;
            }
          })
          .setErrorCallback(new ErrorCallback() {
            @Override
            public void onShowRationalDialog(PermissionInterface permissionInterface, int i) {
              permissionInterface.onDialogShown();
            }

            @Override public void onShowSettings(PermissionInterface permissionInterface, int i) {
              permissionInterface.onSettingsShown();
            }
          })
          .request(0x12);
    }
  }
  public static void checkAudioPermissionStatus(final Activity activity){
    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED) {
      new AskPermission.Builder(activity).setPermissions(Manifest.permission.RECORD_AUDIO)
          .setCallback(new PermissionCallback() {
            @Override public void onPermissionsGranted(int i) {

            }

            @Override public void onPermissionsDenied(int i) {
              Toasty.error(activity,activity.getString(R.string.error_access_audio_permission), Toast.LENGTH_SHORT).show();;
            }
          })
          .setErrorCallback(new ErrorCallback() {
            @Override
            public void onShowRationalDialog(PermissionInterface permissionInterface, int i) {
              permissionInterface.onDialogShown();
            }

            @Override public void onShowSettings(PermissionInterface permissionInterface, int i) {
              permissionInterface.onSettingsShown();
            }
          })
          .request(0x12);
    }
  }
}
