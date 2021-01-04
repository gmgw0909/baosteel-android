package com.meetutech.baosteel.receiver;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.receiver
// Author: culm at 2017-08-16
//*********************************************************

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.android.pushservice.PushMessageReceiver;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.BindDevicesInfoBody;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.result.CommonObjectResult;
import com.meetutech.baosteel.model.MTDataObject;
import com.meetutech.baosteel.utils.DBManager;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BDPushMessageReceiver extends PushMessageReceiver {

  /**
   * TAG to Log
   */
  public static final String TAG = BDPushMessageReceiver.class.getSimpleName();

  public static boolean isPush = true;

  /**
   * 调用PushManager.startWork后，sdk将对push
   * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel
   * id和user id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
   *
   * @param context BroadcastReceiver的执行Context
   * @param errorCode 绑定接口返回值，0 - 成功
   * @param appid 应用id。errorCode非0时为null
   * @param userId 应用user id。errorCode非0时为null
   * @param channelId 应用channel id。errorCode非0时为null
   * @param requestId 向服务端发起的请求id。在追查问题时有用；
   * @return none
   */
  @Override public void onBind(Context context, int errorCode, String appid, String userId,
      final String channelId, String requestId) {
    String responseString =
        "onBind errorCode=" + errorCode + " appid=" + appid + " userId=" + userId + " channelId="
            + channelId + " requestId=" + requestId;

    new RestClient().getApiService()
        .postBindDevices(BSApplication.mainUserId, new InfoBody(true).getHttpParams(),
            new BindDevicesInfoBody(channelId), new Callback<CommonObjectResult<Object>>() {
              @Override public void success(CommonObjectResult<Object> res, Response response) {
                if (res.isSuccess()) {
                  DBManager.insertCache(CacheKeyConstant.NOTIFICATION_DEVICE_TOKEN,new MTDataObject(channelId));
                  Log.d(TAG, "绑定推送设备成功");
                } else {
                  Log.e(TAG, "绑定推送设备失败");
                }
              }

              @Override public void failure(RetrofitError error) {
                error.printStackTrace();
              }
            });

    Log.d(TAG, responseString);
  }

  /**
   * 接收通知到达的函数。
   *
   * @param context 上下文
   * @param title 推送的通知的标题
   * @param description 推送的通知的描述
   * @param customContentString 自定义内容，为空或者json字符串
   */

  @Override public void onNotificationArrived(Context context, String title, String description,
      String customContentString) {
  }

  /**
   * 接收通知点击的函数。
   *
   * @param context 上下文
   * @param title 推送的通知的标题
   * @param description 推送的通知的描述
   * @param customContentString 自定义内容，为空或者json字符串
   */
  @Override public void onNotificationClicked(Context context, String title, String description,
      String customContentString) {
    String notifyString =
        "通知点击 onNotificationClicked title=\"" + title + "\" description=\"" + description
            + "\" customContent=" + customContentString;
    Log.d(TAG, notifyString);

    // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
    if (!TextUtils.isEmpty(customContentString)) {
      JSONObject customJson = null;
      try {
        customJson = new JSONObject(customContentString);
        String myvalue = null;
        if (!customJson.isNull("mykey")) {
          myvalue = customJson.getString("mykey");
        }
      } catch (JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
    //updateContent(context, notifyString);
  }

  /**
   * setTags() 的回调函数。
   *
   * @param context 上下文
   * @param errorCode 错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
   * @param successTags 设置成功的tag
   * @param failTags 设置失败的tag
   * @param requestId 分配给对云推送的请求的id
   */
  @Override public void onSetTags(Context context, int errorCode, List<String> successTags,
      List<String> failTags, String requestId) {
    String responseString =
        "onSetTags errorCode=" + errorCode + " successTags=" + successTags + " failTags=" + failTags
            + " requestId=" + requestId;
    Log.d(TAG, responseString);

    // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
    //updateContent(context, responseString);
  }

  /**
   * delTags() 的回调函数。
   *
   * @param context 上下文
   * @param errorCode 错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
   * @param successTags 成功删除的tag
   * @param failTags 删除失败的tag
   * @param requestId 分配给对云推送的请求的id
   */
  @Override public void onDelTags(Context context, int errorCode, List<String> successTags,
      List<String> failTags, String requestId) {
    String responseString =
        "onDelTags errorCode=" + errorCode + " successTags=" + successTags + " failTags=" + failTags
            + " requestId=" + requestId;
    Log.d(TAG, responseString);

    // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
    //updateContent(context, responseString);
  }

  /**
   * listTags() 的回调函数。
   *
   * @param context 上下文
   * @param errorCode 错误码。0表示列举tag成功；非0表示失败。
   * @param tags 当前应用设置的所有tag。
   * @param requestId 分配给对云推送的请求的id
   */
  @Override public void onListTags(Context context, int errorCode, List<String> tags,
      String requestId) {
    String responseString = "onListTags errorCode=" + errorCode + " tags=" + tags;
    Log.d(TAG, responseString);

    // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
    //updateContent(context, responseString);
  }

  @Override public void onMessage(Context context, String s, String s1) {
  }

  /**
   * PushManager.stopWork() 的回调函数。
   *
   * @param context 上下文
   * @param errorCode 错误码。0表示从云推送解绑定成功；非0表示失败。
   * @param requestId 分配给对云推送的请求的id
   */
  @Override public void onUnbind(Context context, int errorCode, String requestId) {
    if (errorCode == 0) {
      // 解绑定成功
      Log.d(TAG, "解绑成功");
    }
  }
}
