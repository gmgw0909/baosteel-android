package com.meetutech.baosteel.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.HTTPConstant;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.MessageFilterBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.model.http.MessageObject;
import com.meetutech.baosteel.ui.chat.ChatGroupActivity;
import es.dmoral.toasty.Toasty;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.service
// Author: culm at 2017-05-25
//*********************************************************

public class ChatGroupService extends Service {

  private Thread chatThread;
  private long loopDelay = 1000;

  private String experimentId;
  private String eTag = "";

  private boolean isLoopFetch=false;

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    // Let it continue running until it is stopped.
    experimentId=ChatGroupActivity.expId;
    startLoopMessage();
    return START_STICKY;
  }

  private void startLoopMessage() {
    isLoopFetch=true;
    chatThread = new Thread() {
      @Override public void run() {
        super.run();

        while (isLoopFetch) {
          try {
            Thread.sleep(loopDelay);
            fetchMessage(ChatGroupActivity.expId);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    };
    chatThread.start();
  }

  private void fetchMessage(String experimentId) {
    new RestClient().getApiService()
        .getChatMessageByExpId(experimentId, eTag,
            new MessageFilterBody(MessageFilterBody.SORT_ASC).getHttpParams(),
            new Callback<CommonListResult<MessageObject>>() {
              @Override
              public void success(CommonListResult<MessageObject> res, Response response) {
                if (response.getStatus() == 304) {
                   return;
                }

                if(res==null||res.getData()==null){
                  Toasty.error(getApplicationContext(),getString(R.string.load_chat_message_error)).show();
                  return;
                }

                updateETag(response);
                ChatGroupActivity.messageList.clear();
                ChatGroupActivity.messageList.addAll(res.getData());
                ChatGroupActivity.notifyDataChanged();
              }

              @Override public void failure(RetrofitError error) {

              }
            });
  }

  private void updateETag(Response response) {
    List<Header> headerList = response.getHeaders();
    if(headerList==null){
      return;
    }
    for (Header h : headerList) {
      if (h!=null&&!TextUtils.isEmpty(h.getName())&&h.getName().equals(HTTPConstant.HEADER_E_TAG)) {
        eTag = h.getValue();
        return;
      }
    }
  }

  private void stopLoopMessage() {
    if (chatThread != null) {
      chatThread.interrupt();
    }
    isLoopFetch=false;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    eTag="";
    stopLoopMessage();
  }

  public String getExperimentId() {
    return experimentId;
  }

  public void setExperimentId(String experimentId) {
    this.experimentId = experimentId;
  }
}
