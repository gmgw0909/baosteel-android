package com.meetutech.baosteel.ui.media;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui
// Author: culm at 2017-11-29
//*********************************************************

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.result.CommonObjectResult;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.widget.BProgressDialog;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@Route(path = RouterConstant.HLS_PLAYER_URL)
public class HLSPlayerActivity extends BaseActivity{
  @Autowired public String title;
  @Autowired public String uri;
  @Autowired public String experimentId;
  @Autowired public String projectId;
  @Autowired public String cameraId;

  @BindView(R.id.video_view) SurfaceView mVideoSurfaceView;

  public static final String TAG=HLSPlayerActivity.class.getName();

  private boolean isLoop=false;

  // 播放器的对象
  private KSYMediaPlayer ksyMediaPlayer;
  // 播放SDK提供的监听器
  // 播放器在准备完成，可以开播时会发出onPrepared回调
  private IMediaPlayer.OnPreparedListener mOnPreparedListener;
  private IMediaPlayer.OnCompletionListener mOnCompletionListener;

  private IMediaPlayer.OnErrorListener mOnErrorListener;
  private IMediaPlayer.OnInfoListener mOnInfoListener;
  private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener;
  private IMediaPlayer.OnSeekCompleteListener mOnSeekCompletedListener;
  // SurfaceView需在Layout中定义，此处不在赘述
  private SurfaceHolder mSurfaceHolder;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hls_player);
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    initViews();
  }

  private void initViews() {
    if(TextUtils.isEmpty(uri)){
      finish();
      return;
    }
    initPlayerListener();
    startLoop();
  }
  private boolean isInit=false;

  private void startLoop() {
    isLoop=true;
    BProgressDialog.showProgressDialog(this);
    new Thread(){
      @Override public void run() {
        super.run();
        while (isLoop){
          new RestClient().getApiService()
              .postCameraStream(cameraId, new InfoBody(true).getHttpParams(), new Callback<CommonObjectResult<Object>>() {
                @Override public void success(CommonObjectResult<Object> res,
                    Response response) {
                    if(!res.isSuccess()){
                      return;
                    }
                    if(!isInit&&ksyMediaPlayer!=null&&!ksyMediaPlayer.isPlaying()){


                      runOnUiThread(new Runnable() {
                        @Override public void run() {
                          try {
                            Thread.sleep(25000);
                            initPlayer();

                          } catch (InterruptedException e) {
                            e.printStackTrace();
                          }
                        }
                      });
                    }
                }

                @Override public void failure(RetrofitError error) {

                }
              });
          try {
            Thread.sleep(30000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }.start();

  }


  private void initPlayerListener() {
    mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
      @Override
      public void onCompletion(IMediaPlayer mp) {
        // 播放完成，用户可选择释放播放器
        if(ksyMediaPlayer != null) {
          ksyMediaPlayer.stop();
          ksyMediaPlayer.release();
        }
      }
    };
    mOnInfoListener=new IMediaPlayer.OnInfoListener() {
      @Override public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        switch (what) {
          case KSYMediaPlayer.MEDIA_INFO_BUFFERING_START:
            Log.d(TAG, "开始缓冲数据");
            break;
          case KSYMediaPlayer.MEDIA_INFO_BUFFERING_END:
            Log.d(TAG, "数据缓冲完毕");
            break;
          case KSYMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
            Toast.makeText(HLSPlayerActivity.this, "开始播放音频", Toast.LENGTH_SHORT).show();
            break;
          case KSYMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
            BProgressDialog.dismissProgressDialog();
            Toast.makeText(HLSPlayerActivity.this, "开始渲染视频", Toast.LENGTH_SHORT).show();
            isInit=true;
            break;
        }
        return false;
      }
    };
    mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
      @Override
      public void onPrepared(IMediaPlayer mp) {
        if(ksyMediaPlayer != null) {
          // 设置视频伸缩模式，此模式为裁剪模式
          ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
          // 开始播放视频
          ksyMediaPlayer.start();
        }
      }
    };
    mOnErrorListener=new IMediaPlayer.OnErrorListener() {
      @Override public boolean onError(IMediaPlayer mp, int what, int extra) {

        Toasty.error(HLSPlayerActivity.this,getString(R.string.error_load_monitor_steam_server)+what,
            Toast.LENGTH_SHORT).show();
        BProgressDialog.dismissProgressDialog();
        return false;
      }
    };
    mSurfaceHolder=mVideoSurfaceView.getHolder();
    mSurfaceHolder.addCallback(mSurfaceCallback);

    ksyMediaPlayer = new KSYMediaPlayer.Builder(this.getApplicationContext()).build();
    ksyMediaPlayer.setTimeout(360,720);

    ksyMediaPlayer.setOnCompletionListener(mOnCompletionListener);
    ksyMediaPlayer.setOnPreparedListener(mOnPreparedListener);
    ksyMediaPlayer.setOnInfoListener(mOnInfoListener);
    ksyMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
    ksyMediaPlayer.setOnErrorListener(mOnErrorListener);
    ksyMediaPlayer.setOnSeekCompleteListener(mOnSeekCompletedListener);
  }

  private void initPlayer() {


    try {
      ksyMediaPlayer.setDataSource(uri);
      ksyMediaPlayer.prepareAsync();
      ksyMediaPlayer.start();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private final SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
      if(ksyMediaPlayer != null) {
        ksyMediaPlayer.setDisplay(holder);
        ksyMediaPlayer.setScreenOnWhilePlaying(true);
      }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
      if(ksyMediaPlayer != null) {
        ksyMediaPlayer.setDisplay(null);
      }
    }
  };

  @Override protected void onPause() {
    super.onPause();
    isLoop=false;
  }

  @Override protected void onResume() {
    super.onResume();
    isLoop=true;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if(ksyMediaPlayer != null)
      ksyMediaPlayer.release();
    ksyMediaPlayer = null;
    isLoop=false;
  }

}
