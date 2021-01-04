package com.meetutech.baosteel.ui.chat;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.ui.chat
// Author: culm at 2017-05-25
//*********************************************************

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.CacheKeyConstant;
import com.meetutech.baosteel.common.RouterConstant;
import com.meetutech.baosteel.http.api.RestClient;
import com.meetutech.baosteel.http.body.InfoBody;
import com.meetutech.baosteel.http.body.PostMessageBody;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.http.result.CommonObjectResult;
import com.meetutech.baosteel.http.result.UploadFileResult;
import com.meetutech.baosteel.model.UserInfo;
import com.meetutech.baosteel.model.http.MessageObject;
import com.meetutech.baosteel.service.ChatGroupService;
import com.meetutech.baosteel.ui.base.BaseActivity;
import com.meetutech.baosteel.utils.DBManager;
import com.meetutech.baosteel.utils.NetworkUtils;
import com.meetutech.baosteel.utils.PermissionUtils;
import com.meetutech.baosteel.utils.ViewUtils;
import com.meetutech.baosteel.widget.BProgressDialog;
import com.meetutech.baosteel.widget.layout.AbsPercentRelativeLayout;
import com.meetutech.chatkit.commons.ImageLoader;
import com.meetutech.chatkit.messages.AudioPlayClickListener;
import com.meetutech.chatkit.messages.AudioRecordButton;
import com.meetutech.chatkit.messages.MessageInput;
import com.meetutech.chatkit.messages.MessagesList;
import com.meetutech.chatkit.messages.MessagesListAdapter;
import com.meetutech.chatkit.utils.DateFormatter;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.Album;
import com.yixia.camera.VCamera;
import com.yixia.camera.util.Log;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

@Route(path = RouterConstant.CHAT_URL) public class ChatGroupActivity extends BaseActivity
    implements View.OnClickListener {

  public static String expId;
  public static boolean PICK_VIDEO=false;
  private static String CURR_VIDEO_PATH;
  public @Autowired(name = "title") String header_title;
  public @Autowired(name = "experimentId") String experimentId;

  public @BindView(R.id.tv_header_title) TextView tv_header_title;
  public @BindView(R.id.tv_header_back) TextView tv_header_back;
  public @BindView(R.id.listView) MessagesList listView;
  public @BindView(R.id.rl_mainView) AbsPercentRelativeLayout rl_mainView;
  public @BindView(R.id.input) MessageInput messageInput;
  public MessagesListAdapter<MessageObject> adapter = null;

  public AbsPercentRelativeLayout rl_attachmentView;
  public Button btn_attachment_image;
  public Button btn_attachment_video;
  public AudioRecordButton btn_attachment_audio;

  public static List<MessageObject> messageList;

  public static ChatGroupActivity instance;
  private boolean isPlaying = false;

  private boolean isAttachmentViewShow = false;
  private ArrayList<String> mImageList;
  public static final int RESULT_PICK_FORM_GALLERY = 0x0100;
  public static final int RESULT_PICK_FORM_VIDEO = 0x0101;

  public static final int RECORD_AUDIO = 0;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_group);
    try {
      VCamera.initialize(this);
    }catch (UnsatisfiedLinkError e){
      e.printStackTrace();
    }
    ButterKnife.bind(this);
    ARouter.getInstance().inject(this);
    instance = this;
    initViews();
    initService();
  }

  @Override protected void onResume() {
    super.onResume();
    checkAudioPermission();
  }

  private void checkAudioPermission() {
    PermissionUtils.checkWriteDiskPermissionStatus(this);
    PermissionUtils.checkAudioPermissionStatus(this);
  }

  private void initService() {
    startChatService();
  }

  private void initViews() {
    AudioPlayClickListener.setContext(this);
    tv_header_title.setText(header_title);
    ViewUtils.adjustViewTextSize(tv_header_back, 38);
    ViewUtils.adjustViewTextSize(tv_header_title, 35);
    messageList = new ArrayList<>();
    expId = experimentId;
    BSApplication.mainUserId = ((UserInfo) DBManager.getCache(CacheKeyConstant.AUTH_INFO)).getId();
    MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
    adapter = new MessagesListAdapter<MessageObject>(BSApplication.mainUserId, imageLoader);
    adapter.addToEnd(messageList, true);
    adapter.setDateHeadersFormatter(new DateFormatter.Formatter() {
      @Override public String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
      }
    });
    listView.setAdapter(adapter);

    //初始化附件View
    rl_attachmentView =
        (AbsPercentRelativeLayout) getLayoutInflater().inflate(R.layout.activity_chat_attachment,
            null);
    btn_attachment_audio =
        (AudioRecordButton) rl_attachmentView.findViewById(R.id.btn_attachment_audio);
    btn_attachment_image = (Button) rl_attachmentView.findViewById(R.id.btn_attachment_image);
    btn_attachment_image.setOnClickListener(this);
    btn_attachment_audio.setAudioFinishRecorderListener(
        new AudioRecordButton.AudioFinishRecorderListener() {

          @Override public void onFinished(float seconds, String filePath) {
            postMediaMessage(filePath, MessageObject.TYPE_AUDIO);
          }
        });
    btn_attachment_video = (Button) rl_attachmentView.findViewById(R.id.btn_attachment_video);
    btn_attachment_video.setOnClickListener(this);

    Drawable voiceDrawable = getResources().getDrawable(R.drawable.ic_keyboard_voice);
    Drawable imageDrawable = getResources().getDrawable(R.drawable.ic_keyboard_gallery);
    Drawable videoDrawable = getResources().getDrawable(R.drawable.ic_keyboard_video);

    voiceDrawable.setBounds(0, ViewUtils.getDimenByRateWidth(10), ViewUtils.getDimenByRateWidth(90),
        ViewUtils.getDimenByRateWidth(100));
    imageDrawable.setBounds(0, ViewUtils.getDimenByRateWidth(10), ViewUtils.getDimenByRateWidth(90),
        ViewUtils.getDimenByRateWidth(100));
    videoDrawable.setBounds(0, ViewUtils.getDimenByRateWidth(10),
        ViewUtils.getDimenByRateWidth(100), ViewUtils.getDimenByRateWidth(110));

    btn_attachment_audio.setCompoundDrawables(null, voiceDrawable, null, null);
    btn_attachment_image.setCompoundDrawables(null, imageDrawable, null, null);
    btn_attachment_video.setCompoundDrawables(null, videoDrawable, null, null);

    messageInput.setInputListener(new MessageInput.InputListener() {
      @Override public boolean onSubmit(CharSequence input) {
        //validate and send message
        postTextMessage(String.valueOf(input));
        return true;
      }
    });
    messageInput.setAttachmentsListener(new MessageInput.AttachmentsListener() {
      @Override public void onAddAttachments() {
        toggleAttachmentView(isAttachmentViewShow);
      }
    });
  }

  private void toggleAttachmentView(boolean isAttachmentViewShow) {
    if (!isAttachmentViewShow) {
      showAttachmentView();
    } else {
      hideAttachmentView();
    }
  }

  private void hideAttachmentView() {
    if (rl_attachmentView != null) {
      rl_mainView.removeView(rl_attachmentView);
    }
    //调整输入框位置
    AbsPercentRelativeLayout.LayoutParams params =
        (AbsPercentRelativeLayout.LayoutParams) messageInput.getLayoutParams();
    params.setMargins(0, 0, 0, 0);
    messageInput.setLayoutParams(params);
    isAttachmentViewShow = false;
  }

  private void showAttachmentView() {
    AbsPercentRelativeLayout.LayoutParams params =
        rl_attachmentView.getLayoutParams() == null ? new AbsPercentRelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewUtils.getDimenByRateWidth(210.0f))
            : (AbsPercentRelativeLayout.LayoutParams) rl_attachmentView.getLayoutParams();

    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    rl_mainView.addView(rl_attachmentView, params);

    //调整输入框位置
    params = (AbsPercentRelativeLayout.LayoutParams) messageInput.getLayoutParams();
    params.setMargins(0, 0, 0, ViewUtils.getDimenByRateWidth(210.0f));
    messageInput.setLayoutParams(params);
    isAttachmentViewShow = true;
  }

  private void postTextMessage(String input) {
    PostMessageBody body = new PostMessageBody();
    body.setMessage(input);
    body.setType(MessageObject.TYPE_TEXT);
    new RestClient().getApiService()
        .postChatMessage(experimentId, new InfoBody(true).getHttpParams(), body,
            new Callback<CommonObjectResult<MessageObject>>() {
              @Override
              public void success(CommonObjectResult<MessageObject> messageObjectCommonObjectResult,
                  Response response) {

              }

              @Override public void failure(RetrofitError error) {
                Toasty.error(ChatGroupActivity.this, getString(R.string.message_send_failed),
                    Toast.LENGTH_SHORT).show();
              }
            });
  }

  public void startChatService() {
    Intent intent = new Intent(getBaseContext(), ChatGroupService.class);
    intent.putExtra("experimentId", experimentId);
    startService(intent);
  }

  public void stopChatService() {
    stopService(new Intent(getBaseContext(), ChatGroupService.class));
  }

  @OnClick(R.id.rl_header_back) public void onBackClick() {
    finish();
  }

  public static void notifyDataChanged() {
    if (messageList == null) {
      return;
    }
    try {
      for (MessageObject object : messageList) {
        instance.adapter.deleteById(object.getId());
      }
      instance.adapter.addToEnd(messageList, true);
    } catch (NullPointerException e) {
      //TODO:处理停止轮询服务后，adapter冲突问题
      e.printStackTrace();
    }
  }

  @Override public void finish() {
    super.finish();
    instance = null;
    stopChatService();
  }

  public ImageLoader imageLoader = new ImageLoader() {
    @Override public void loadImage(ImageView imageView, String url) {
      Log.i("culm","Load Image URL:"+url);
      //com.nostra13.universalimageloader.core.ImageLoader.getInstance()
      //    .displayImage(url,imageView);
      Picasso.with(ChatGroupActivity.this).load(url).fit().into(imageView,
          new com.squareup.picasso.Callback() {
            @Override public void onSuccess() {
              Log.i("culm","Load Image success");

            }

            @Override public void onError() {
              Log.i("culm","Load Image error");
            }
          });
    }
  };

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_attachment_image:
        pickImageFromGallery();
        break;
      case R.id.btn_attachment_video:
        startActivity(new Intent(this, VideoCameraActivity.class));
        break;
      default:
        break;
    }
  }

  private void pickImageFromGallery() {
    Album.album(this)
        .title(getString(R.string.btn_select_gallery))
        .selectCount(1)
        .columnCount(2)
        .camera(true)
        .checkedList(mImageList)
        .start(RESULT_PICK_FORM_GALLERY);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == RESULT_PICK_FORM_GALLERY) {
      if (resultCode == RESULT_OK) {
        ArrayList<String> imageList = Album.parseResult(data);
        if (imageList.size() <= 0) {
          return;
        }
        postMediaMessage(imageList.get(0), MessageObject.TYPE_IMAGE);
      } else if (resultCode == RESULT_CANCELED) {
        Toasty.warning(this, getString(R.string.pick_gallery_cancel), Toast.LENGTH_SHORT).show();
      }
    }
  }



  private void postMediaMessage(String path, final String type) {

    if (!NetworkUtils.isOnline(this)) {
      Toasty.error(this, getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
      return;
    }

    BProgressDialog.showProgressDialog(this, getString(R.string.message_sending));
    new RestClient().getApiService()
        .uploadFile("chats", new InfoBody(true).getHttpParams(),
            new TypedFile("multipart/form-data", new File(path)),
            new Callback<CommonListResult<UploadFileResult>>() {
              @Override
              public void success(CommonListResult<UploadFileResult> res, Response response) {

                if (res.getData() == null || res.getData().size() <= 0) {
                  Toasty.success(ChatGroupActivity.this, getString(R.string.message_upload_failed),
                      Toast.LENGTH_SHORT).show();
                  BProgressDialog.dismissProgressDialog();
                  return;
                }

                PostMessageBody body = new PostMessageBody();
                body.setMessage(res.getData().get(0).getUrl());
                body.setType(type);
                new RestClient().getApiService()
                    .postChatMessage(experimentId, new InfoBody(true).getHttpParams(), body,
                        new Callback<CommonObjectResult<MessageObject>>() {
                          @Override public void success(
                              CommonObjectResult<MessageObject> messageObjectCommonObjectResult,
                              Response response) {
                            BProgressDialog.dismissProgressDialog();
                          }

                          @Override public void failure(RetrofitError error) {
                            Toasty.error(ChatGroupActivity.this,
                                getString(R.string.message_send_failed), Toast.LENGTH_SHORT).show();
                            BProgressDialog.dismissProgressDialog();
                          }
                        });
              }

              @Override public void failure(RetrofitError error) {
                BProgressDialog.dismissProgressDialog();
                Toasty.error(ChatGroupActivity.this, getString(R.string.message_upload_failed),
                    Toast.LENGTH_SHORT).show();
              }
            });
  }

  public static void setVideoPath(String result, boolean b) {
    if(b) {
      CURR_VIDEO_PATH = result;
      if(instance!=null) {
        instance.postMediaMessage(result,MessageObject.TYPE_VIDEO);
      }
    }
  }

  private void postVideoMessage(String result) {
    Toasty.success(this,result,Toast.LENGTH_SHORT).show();
  }
}
