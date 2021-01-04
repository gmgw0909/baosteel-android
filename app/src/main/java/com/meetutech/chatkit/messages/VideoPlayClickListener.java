package com.meetutech.chatkit.messages;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.chatkit.messages
// Author: culm at 2017-09-03
//*********************************************************

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.ui.chat.ChatGroupActivity;
import es.dmoral.toasty.Toasty;

class VideoPlayClickListener implements View.OnClickListener {
  private String videoUrl;
  public VideoPlayClickListener(String videoUrl) {
    this.videoUrl=videoUrl;
  }

  @Override public void onClick(View view) {
    try {
      Intent openVideo = new Intent(Intent.ACTION_VIEW);
      openVideo.setDataAndType(Uri.parse(videoUrl), "video/*");
      ChatGroupActivity.instance.startActivity(openVideo);
    }catch (Exception e){
      e.printStackTrace();
      Toasty.error(BSApplication.instance,"启动视频播放器失败！", Toast.LENGTH_SHORT).show();
    }
  }
}
