package com.meetutech.baosteel.ui.chat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.widget.video.MyVideoView;
import com.meetutech.baosteel.widget.video.TouchView;
import com.meetutech.baosteel.widget.video.TuyaView;
import com.yixia.camera.MediaRecorderBase;
import com.yixia.videoeditor.adapter.UtilityAdapter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class EditVideoActivity extends BaseActivity implements View.OnClickListener{

    private MyVideoView vv_play;
    private LinearLayout ll_color;

    private int[] drawableBg = new int[]{R.drawable.color1, R.drawable.color2, R.drawable.color3, R.drawable.color4, R.drawable.color5};
    private int[] colors = new int[]{R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5};
    private int dp20;
    private int dp25;

    private int currentColorPosition;
    private TuyaView tv_video;
    private ImageView iv_pen;
    //private ImageView iv_icon;
    //private RelativeLayout rl_expression;
    private RelativeLayout rl_touch_view;
    private RelativeLayout rl_edit_text;
    private EditText et_tag;
    private TextView tv_tag;
    private InputMethodManager manager;
    private int windowHeight;
    private int windowWidth;
    private String path;
    private RelativeLayout rl_tuya;
    private RelativeLayout rl_close;
    private RelativeLayout rl_title;
    private RelativeLayout rl_bottom;
    private TextView tv_hint_delete;
    private int dp100;
    private ImageView iv_speed;
    private LinearLayout ll_progress;
    private SeekBar sb_speed;
    private TextView tv_speed;
    private float videoSpeed = 1;
    private int videoWidth;
    private int videoHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_video);

        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        windowHeight = getWindowManager().getDefaultDisplay().getHeight();

        dp100 = (int) getResources().getDimension(R.dimen.dp100);

        initUI();

        Intent intent = getIntent();
        path = intent.getStringExtra("path");

        vv_play.setVideoPath(path);
        vv_play.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                vv_play.setLooping(true);
                vv_play.start();
            }
        });

        vv_play.setOnPlayStateListener(new MyVideoView.OnPlayStateListener() {
            @Override
            public void onStateChanged(boolean isPlaying) {
                if(isPlaying){
                    videoWidth = vv_play.getVideoWidth();
                    videoHeight = vv_play.getVideoHeight();
                    float widthF = videoWidth*1f/ MediaRecorderBase.VIDEO_HEIGHT;
                    float heightF = videoHeight*1f/ MediaRecorderBase.VIDEO_WIDTH;
                    ViewGroup.LayoutParams layoutParams = vv_play.getLayoutParams();
                    layoutParams.width = (int) (windowWidth*widthF);
                    layoutParams.height = (int) (windowHeight*heightF);
                    vv_play.setLayoutParams(layoutParams);
                    vv_play.setLooping(true);

                    ViewGroup.LayoutParams layoutParams1 = rl_tuya.getLayoutParams();
                    layoutParams1.width = layoutParams.width;
                    layoutParams1.height = layoutParams.height;
                    rl_tuya.setLayoutParams(layoutParams1);
                }
            }
        });

        //当进行涂鸦操作时, 隐藏标题栏和底部工具栏
        tv_video.setOnTouchListener(new TuyaView.OnTouchListener() {
            @Override
            public void onDown() {
                changeMode(false);
            }
            @Override
            public void onUp() {
                changeMode(true);
            }
        });
    }

    private void initUI() {

        vv_play = (MyVideoView) findViewById(R.id.vv_play);
        RelativeLayout rv_pen = (RelativeLayout) findViewById(R.id.rl_pen);
        //RelativeLayout rv_icon = (RelativeLayout) findViewById(R.id.rl_icon);
        RelativeLayout rv_text = (RelativeLayout) findViewById(R.id.rl_text);
        ll_color = (LinearLayout) findViewById(R.id.ll_color);
        tv_video = (TuyaView) findViewById(R.id.tv_video);
        //rl_expression = (RelativeLayout) findViewById(R.id.rl_expression);
        rl_touch_view = (RelativeLayout) findViewById(R.id.rl_touch_view);
        TextView tv_close = (TextView) findViewById(R.id.tv_close);
        TextView tv_finish = (TextView) findViewById(R.id.tv_finish);
        rl_edit_text = (RelativeLayout) findViewById(R.id.rl_edit_text);
        et_tag = (EditText) findViewById(R.id.et_tag);
        tv_tag = (TextView) findViewById(R.id.tv_tag);
        TextView tv_finish_video = (TextView) findViewById(R.id.tv_finish_video);
        iv_pen = (ImageView) findViewById(R.id.iv_pen);
        //iv_icon = (ImageView) findViewById(R.id.iv_icon);
        iv_speed = (ImageView) findViewById(R.id.iv_speed);
        rl_tuya = (RelativeLayout) findViewById(R.id.rl_tuya);
        rl_close = (RelativeLayout) findViewById(R.id.rl_close);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        tv_hint_delete = (TextView) findViewById(R.id.tv_hint_delete);
        RelativeLayout rl_speed = (RelativeLayout) findViewById(R.id.rl_speed);
        ll_progress = (LinearLayout) findViewById(R.id.ll_progress);
        sb_speed = (SeekBar) findViewById(R.id.sb_speed);
        tv_speed = (TextView) findViewById(R.id.tv_speed);
        RelativeLayout rl_cut = (RelativeLayout) findViewById(R.id.rl_cut);

        RelativeLayout rl_back =  (RelativeLayout) findViewById(R.id.rl_back);

        rv_pen.setOnClickListener(this);
        //rv_icon.setOnClickListener(this);
        rv_text.setOnClickListener(this);

        rl_back.setOnClickListener(this);
        ll_color.setOnClickListener(this);
        tv_close.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
        tv_finish_video.setOnClickListener(this);
        rl_close.setOnClickListener(this);
        rl_speed.setOnClickListener(this);
        rl_cut.setOnClickListener(this);

        initColors();
        //initExpression();
        initSpeed();

        et_tag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                tv_tag.setText(s.toString());
            }
        });
    }

    private void initSpeed() {

        sb_speed.setMax(200);
        sb_speed.setProgress(100);
        sb_speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < 50){
                    progress = 50;
                    sb_speed.setProgress(50);
                }
                videoSpeed = progress/100f;
                tv_speed.setText(videoSpeed+"");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    //更改界面模式
    private void changeMode(boolean flag){
        if(flag){
            rl_title.setVisibility(View.VISIBLE);
            rl_bottom.setVisibility(View.VISIBLE);
        }else{
            rl_title.setVisibility(View.GONE);
            rl_bottom.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化表情
     */
    //private void initExpression() {
    //
    //    int dp80 = (int) getResources().getDimension(R.dimen.dp80);
    //    int dp10 = (int) getResources().getDimension(R.dimen.dp10);
    //    for (int x=0; x<expressions.length; x++){
    //        ImageView imageView = new ImageView(this);
    //        imageView.setPadding(dp10, dp10, dp10, dp10);
    //        final int result = expressions[x];
    //        imageView.setImageResource(result);
    //        imageView.setLayoutParams(new ViewGroup.LayoutParams(windowWidth /4, dp80));
    //        imageView.setX(x%4* windowWidth /4);
    //        imageView.setY(x/4* dp80);
    //        imageView.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                rl_expression.setVisibility(View.GONE);
    //                iv_icon.setImageResource(R.mipmap.icon);
    //                addExpressionToWindow(result);
    //            }
    //        });
    //        rl_expression.addView(imageView);
    //    }
    //}

    /**
     * 添加表情到界面上
     */
    //private void addExpressionToWindow(int result){
    //
    //    TouchView touchView = new TouchView(this);
    //    touchView.setBackgroundResource(result);
    //    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dp100, dp100);
    //    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
    //    touchView.setLayoutParams(layoutParams);
    //
    //    touchView.setLimitsX(0, windowWidth);
    //    touchView.setLimitsY(0, windowHeight-dp100/2);
    //    touchView.setOnLimitsListener(new TouchView.OnLimitsListener() {
    //        @Override
    //        public void OnOutLimits(float x, float y) {
    //            tv_hint_delete.setTextColor(Color.RED);
    //        }
    //        @Override
    //        public void OnInnerLimits(float x, float y) {
    //            tv_hint_delete.setTextColor(Color.WHITE);
    //        }
    //    });
    //    touchView.setOnTouchListener(new TouchView.OnTouchListener() {
    //        @Override
    //        public void onDown(TouchView view, MotionEvent event) {
    //            tv_hint_delete.setVisibility(View.VISIBLE);
    //            changeMode(false);
    //        }
    //        @Override
    //        public void onMove(TouchView view, MotionEvent event) {
    //
    //        }
    //        @Override
    //        public void onUp(TouchView view, MotionEvent event) {
    //            tv_hint_delete.setVisibility(View.GONE);
    //            changeMode(true);
    //            if(view.isOutLimits()){
    //                rl_touch_view.removeView(view);
    //            }
    //        }
    //    });
    //
    //    rl_touch_view.addView(touchView);
    //}

    /**
     * 添加文字到界面上
     */
    private void addTextToWindow() {

        TouchView touchView = new TouchView(getApplicationContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(tv_tag.getWidth(), tv_tag.getHeight());
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        touchView.setLayoutParams(layoutParams);
        Bitmap bitmap = Bitmap.createBitmap(tv_tag.getWidth(), tv_tag.getHeight(), Bitmap.Config.ARGB_8888);
        tv_tag.draw(new Canvas(bitmap));
        touchView.setBackground(new BitmapDrawable(bitmap));

        touchView.setLimitsX(0, windowWidth);
        touchView.setLimitsY(0, windowHeight-dp100/2);
        touchView.setOnLimitsListener(new TouchView.OnLimitsListener() {
            @Override
            public void OnOutLimits(float x, float y) {
                tv_hint_delete.setTextColor(Color.RED);
            }
            @Override
            public void OnInnerLimits(float x, float y) {
                tv_hint_delete.setTextColor(Color.WHITE);
            }
        });
        touchView.setOnTouchListener(new TouchView.OnTouchListener() {
            @Override
            public void onDown(TouchView view, MotionEvent event) {
                tv_hint_delete.setVisibility(View.VISIBLE);
                changeMode(false);
            }
            @Override
            public void onMove(TouchView view, MotionEvent event) {

            }
            @Override
            public void onUp(TouchView view, MotionEvent event) {
                tv_hint_delete.setVisibility(View.GONE);
                changeMode(true);
                if(view.isOutLimits()){
                    rl_touch_view.removeView(view);
                }
            }
        });

        rl_touch_view.addView(touchView);

        et_tag.setText("");
        tv_tag.setText("");
    }

    /**
     * 初始化底部颜色选择器
     */
    private void initColors() {

        dp20 = (int)getResources().getDimension(R.dimen.dp20);
        dp25 = (int)getResources().getDimension(R.dimen.dp25);

        for (int x = 0; x< drawableBg.length; x++){
            RelativeLayout relativeLayout = new RelativeLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            relativeLayout.setLayoutParams(layoutParams);

            View view = new View(this);
            view.setBackgroundDrawable(getResources().getDrawable(drawableBg[x]));
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(dp20, dp20);
            layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
            view.setLayoutParams(layoutParams1);
            relativeLayout.addView(view);

            final View view2 = new View(this);
            view2.setBackgroundResource(R.mipmap.color_click);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(dp25, dp25);
            layoutParams2.addRule(RelativeLayout.CENTER_IN_PARENT);
            view2.setLayoutParams(layoutParams2);
            if(x != 0) {
                view2.setVisibility(View.GONE);
            }
            relativeLayout.addView(view2);

            final int position = x;
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentColorPosition != position) {
                        view2.setVisibility(View.VISIBLE);
                        ViewGroup parent = (ViewGroup) v.getParent();
                        ViewGroup childView = (ViewGroup) parent.getChildAt(currentColorPosition);
                        childView.getChildAt(1).setVisibility(View.GONE);
                        tv_video.setNewPaintColor(getResources().getColor(colors[position]));
                        currentColorPosition = position;
                    }
                }
            });

            ll_color.addView(relativeLayout, x);
        }
    }

    boolean isFirstShowEditText;
    /**
     * 弹出键盘
     */
    public void popupEditText(){

        isFirstShowEditText = true;
        et_tag.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(isFirstShowEditText) {
                    isFirstShowEditText = false;
                    et_tag.setFocusable(true);
                    et_tag.setFocusableInTouchMode(true);
                    et_tag.requestFocus();
                    isFirstShowEditText = !manager.showSoftInput(et_tag, 0);
                }
            }
        });
    }

    /**
     * 执行文字编辑区域动画
     */
    private void startAnim(float start, float end, AnimatorListenerAdapter listenerAdapter){

        ValueAnimator va = ValueAnimator.ofFloat(start, end).setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                rl_edit_text.setY(value);
            }
        });
        if(listenerAdapter != null) va.addListener(listenerAdapter);
        va.start();
    }

    /**
     * 更改画笔状态的界面
     */
    private void changePenState(boolean flag){

        if(flag){
            tv_video.setDrawMode(flag);
            tv_video.setNewPaintColor(getResources().getColor(colors[currentColorPosition]));
            iv_pen.setImageResource(R.mipmap.pen_click);
            ll_color.setVisibility(View.VISIBLE);
        }else{
            tv_video.setDrawMode(flag);
            ll_color.setVisibility(View.GONE);
            iv_pen.setImageResource(R.mipmap.pen);
        }
    }

    /**
     * 更改表情状态的界面
     */
    //private void changeIconState(boolean flag){
    //
    //    if(flag){
    //        iv_icon.setImageResource(R.mipmap.icon_click);
    //        rl_expression.setVisibility(View.VISIBLE);
    //    }else{
    //        rl_expression.setVisibility(View.GONE);
    //        iv_icon.setImageResource(R.mipmap.icon);
    //    }
    //}

    /**
     * 更改文字输入状态的界面
     */
    private void changeTextState(boolean flag){

        if(flag) {
            rl_edit_text.setY(windowHeight);
            rl_edit_text.setVisibility(View.VISIBLE);
            startAnim(rl_edit_text.getY(), 0, null);
            popupEditText();
        }else{
            manager.hideSoftInputFromWindow(et_tag.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            startAnim(0, windowHeight, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rl_edit_text.setVisibility(View.GONE);
                }
            });
        }
    }

    /**
     * 更改视频剪切界面
     */
    private void changeCutState(){

        Intent intent = new Intent(this, CutVideoActivity.class);
        intent.putExtra("path", path);
        startActivityForResult(intent, 1);
    }

    /**
     * 更改视频加速
     */
    private void changeSpeedState(boolean flag){

        if(flag) {
            ll_progress.setVisibility(View.VISIBLE);
            iv_speed.setImageResource(R.mipmap.speed_click);
        }else{
            ll_progress.setVisibility(View.GONE);
            iv_speed.setImageResource(R.mipmap.speed);
        }
    }

    /**
     * 合成图片到视频里
     */
    private String mergeImage(String path){

        //得到涂鸦view的bitmap图片
        Bitmap bitmap = Bitmap.createBitmap(rl_tuya.getWidth(), rl_tuya.getHeight(), Bitmap.Config.ARGB_8888);
        rl_tuya.draw(new Canvas(bitmap));
        //这步是根据视频尺寸来调整图片宽高,和视频保持一致
        Matrix matrix = new Matrix();
        matrix.postScale(videoWidth * 1f / bitmap.getWidth(), videoHeight * 1f / bitmap.getHeight());
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        String imagePath = BSApplication.VIDEO_PATH +"/tuya.png";
        File file = new File(imagePath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mergeVideo = BSApplication.VIDEO_PATH+"/mergeVideo.mp4";

        //ffmpeg -i videoPath -i imagePath -filter_complex overlay=0:0 -vcodec libx264 -profile:v baseline -preset ultrafast -b:v 3000k -g 30 -f mp4 outPath
        StringBuilder sb = new StringBuilder();
        sb.append("ffmpeg");
        sb.append(" -i");
        sb.append(" "+path);
        sb.append(" -i");
        sb.append(" "+imagePath);
        sb.append(" -filter_complex");
        sb.append(" overlay=0:0");
        sb.append(" -vcodec libx264 -profile:v baseline -preset ultrafast -b:v 3000k -g 25");
        sb.append(" -f mp4");
        sb.append(" "+mergeVideo);

        int i = UtilityAdapter.FFmpegRun("", sb.toString());
        if(i == 0){
            return mergeVideo;
        }else{
            return "";
        }
    }

    /**
     * 调整视频播放速度
     */
    private String adjustVideoSpeed(String path, float speed){

        String outPut = BSApplication.VIDEO_PATH+"/speedVideo.mp4";

        //./ffmpeg -i 2x.mp4 -filter_complex "[0:v]setpts=0.5*PTS[v];[0:a]atempo=2.0[a]" -map "[v]" -map "[a]" output3.mp4
        String filter = String.format(Locale.getDefault(), "[0:v]setpts=%f*PTS[v];[0:a]atempo=%f[a]", 1/speed, speed);
        StringBuilder sb = new StringBuilder("ffmpeg");
        sb.append(" -i");
        sb.append(" "+path);
        sb.append(" -filter_complex");
        sb.append(" "+filter);
        sb.append(" -map");
        sb.append(" [v]");
        sb.append(" -map");
        sb.append(" [a]");
        sb.append(" -y");
        sb.append(" "+outPut);

        int i = UtilityAdapter.FFmpegRun("", sb.toString());
        if(i == 0){
            return outPut;
        }else{
            return "";
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vv_play.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vv_play.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK){
            vv_play.setVideoPath(path);
        }
    }

    private void finishVideo(){

        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                showProgressDialog();
            }
            @Override
            protected String doInBackground(Void... params) {
                String videoPath = mergeImage(path);
                if(videoSpeed != 1){
                    videoPath = adjustVideoSpeed(videoPath, videoSpeed);
                }
                return videoPath;
            }
            @Override
            protected void onPostExecute(String result) {
                closeProgressDialog();
                if(!TextUtils.isEmpty(result)) {
                    ChatGroupActivity.setVideoPath(result,true);
                    finish();
                    if(VideoCameraActivity.getInstance()!=null) {
                        VideoCameraActivity.getInstance().finish();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "视频编辑失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.rl_pen:
                changePenState(!(ll_color.getVisibility()== View.VISIBLE));
                //changeIconState(false);
                changeTextState(false);
                changeSpeedState(false);
                break;
            //case R.id.rl_icon:
            //    //changeIconState(!(rl_expression.getVisibility()== View.VISIBLE));
            //    changePenState(false);
            //    changeTextState(false);
            //    changeSpeedState(false);
            //    break;
            case R.id.rl_text:
                changeTextState(!(rl_edit_text.getVisibility()== View.VISIBLE));
                changePenState(false);
                //changeIconState(false);
                changeSpeedState(false);
                break;
            case R.id.rl_back:
                tv_video.backPath();
                break;
            case R.id.tv_close:
                changeTextState(!(rl_edit_text.getVisibility()== View.VISIBLE));
                break;
            case R.id.tv_finish:
                changeTextState(!(rl_edit_text.getVisibility()== View.VISIBLE));
                if(et_tag.getText().length() > 0) {
                    addTextToWindow();
                }
                break;
            case R.id.tv_finish_video:
                finishVideo();
                break;
            case R.id.rl_close:
                onBackPressed();
                break;
            case R.id.rl_speed:
                changeTextState(false);
                changePenState(false);
                //changeIconState(false);
                changeSpeedState(!(ll_progress.getVisibility()== View.VISIBLE));
                break;
            case R.id.rl_cut:
                changeCutState();
                break;
        }
    }
}
