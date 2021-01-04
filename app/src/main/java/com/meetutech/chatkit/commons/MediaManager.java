package com.meetutech.chatkit.commons;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.model.MTDataObject;
import com.meetutech.baosteel.utils.DBManager;
import java.io.IOException;

public class MediaManager {

  private static MediaPlayer mPlayer;

  private static boolean isPause;

  public static void init(final String filePathString,
      MediaPlayer.OnCompletionListener... onCompletionListener) throws IOException {
    mPlayer = new MediaPlayer();
    mPlayer.setDataSource(filePathString);

    mPlayer.setVolume(15f, 15f);
    if (onCompletionListener != null && onCompletionListener.length > 0) {
      mPlayer.setOnCompletionListener(onCompletionListener[0]);
    }
    mPlayer.prepare();
  }

  public static void play() {
    if (mPlayer == null) {
      return;
    }
    if (!isPlaying()) {
      mPlayer.start();
    } else {
      mPlayer.stop();
    }
  }

  public static String getDuration(Context ctx, String... path) {

    if (path != null && path.length > 0) {
      if(DBManager.getCache(CacheKeyConstant.AUDIO_DURATION+path[0])!=null) {
        String res = (String) DBManager.getCache(CacheKeyConstant.AUDIO_DURATION + path[0]);
        if(!TextUtils.isEmpty(res)){
          return res;
        }
      }

      try {
        init(path[0]);
      } catch (IOException e) {
        e.printStackTrace();
        return ctx.getString(R.string.default_audio_duration);
      }
    }

    if (mPlayer != null) {
      String res=String.format("%.1fs",mPlayer.getDuration()/1000.f);
      if (path != null && path.length > 0) {
        DBManager.insertCache(CacheKeyConstant.AUDIO_DURATION+path[0], new MTDataObject(res));
      }
      mPlayer.release();
      return res;

    }
    return ctx.getString(R.string.default_audio_duration);
  }

  public static boolean isPlaying() {
    if (mPlayer != null && mPlayer.isPlaying()) {
      return true;
    }
    return false;
  }

  //暂停函数
  public static void pause() {
    if (mPlayer != null && mPlayer.isPlaying()) {
      mPlayer.pause();
      isPause = true;
    }
  }

  //停止函数
  public static void stop() {
    if (mPlayer != null && mPlayer.isPlaying()) {
      mPlayer.reset();
    }
  }

  //继续
  public static void resume() {
    if (mPlayer != null && isPause) {
      mPlayer.start();
      isPause = false;
    }
  }

  public static void release() {
    if (mPlayer != null) {
      mPlayer.release();
      mPlayer = null;
    }
  }
}