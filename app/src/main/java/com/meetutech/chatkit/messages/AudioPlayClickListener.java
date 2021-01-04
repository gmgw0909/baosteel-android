package com.meetutech.chatkit.messages;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.chatkit.messages
// Author: culm at 2017-08-09
//*********************************************************

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import com.meetutech.chatkit.commons.MediaManager;
import java.io.IOException;

public class AudioPlayClickListener implements View.OnClickListener, MediaPlayer.OnCompletionListener {

  private static Context context;
  private String audio_url;

  public AudioPlayClickListener(String audio_url) {
    this.audio_url=audio_url;
  }

  @Override public void onClick(View view) {
    try {
      MediaManager.init(audio_url, this);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if(MediaManager.isPlaying()){
      MediaManager.stop();
    } else {
      MediaManager.play();
    }
  }

  public static void setContext(Context context) {
    AudioPlayClickListener.context = context;
  }

  @Override public void onCompletion(MediaPlayer mediaPlayer) {
    mediaPlayer.release();
  }
}
