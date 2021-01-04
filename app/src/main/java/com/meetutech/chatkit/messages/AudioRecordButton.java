package com.meetutech.chatkit.messages;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.common.AppConstant;

public class AudioRecordButton extends Button implements AudioManager.AudioStageListener {

	private static final int STATE_NORMAL = 1;
	private static final int STATE_RECORDING = 2;
	private static final int STATE_WANT_TO_CANCEL = 3;
	private static final int DISTANCE_Y_CANCEL = 100;
	private static final int MAX_RECORDE_TIME = Integer.MAX_VALUE;//最长录音时间
	private static final int COUNTDOWN_SECONDS = 8;//倒计时时间

	private int mCurrentState = STATE_NORMAL;
	private boolean isRecording = false;
	private DialogManager mDialogManager;
	private AudioManager mAudioManager;
	private float mTime = 0;
	private boolean mReady;

	public AudioRecordButton(Context context) {
		this(context, null);
	}

	public AudioRecordButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDialogManager = new DialogManager(getContext());
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String dir = String.format("%s/%s/%s",Environment.getExternalStorageDirectory()
					, AppConstant.CACHE_DIRECTORY_PATH,AppConstant.CACHE_TYPE_AUDIO);

			mAudioManager = AudioManager.getInstance(dir);
			mAudioManager.setmMaxRecordeTime(MAX_RECORDE_TIME);
			mAudioManager.setOnAudioStageListener(this);

			setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					mReady = true;
					mAudioManager.prepareAudio();
					return false;
				}
			});
		} else {
			Toast.makeText(context, context.getString(R.string.error_failed_sdcard), Toast.LENGTH_SHORT).show();
		}
	}

	public interface AudioFinishRecorderListener {
		void onFinished(float seconds, String filePath);
	}

	private AudioFinishRecorderListener mListener;

	public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener) {
		mListener = listener;
	}

	private Runnable mGetVoiceLevelRunnable = new Runnable() {

		@Override
		public void run() {
			while (isRecording) {
				try {
					Thread.sleep(100);
					mTime = (float) Math.round((mTime + 0.1f) * 10) / 10;
					recordHandler.sendEmptyMessage(MSG_VOICE_CHANGE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private static final int MSG_AUDIO_PREPARED = 0X110;
	private static final int MSG_VOICE_CHANGE = 0X111;
	private static final int MSG_DIALOG_DIMISS = 0X112;
	private Thread thread;
	private boolean isCountDown = false;
	private boolean isSwitch = false;

	private Handler recordHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case MSG_AUDIO_PREPARED:
					mDialogManager.showRecordingDialog();
					isRecording = true;
					thread = new Thread(mGetVoiceLevelRunnable);
					thread.start();
					break;
				case MSG_VOICE_CHANGE:
					if (mTime >= MAX_RECORDE_TIME - COUNTDOWN_SECONDS-1) {
						if (mTime * 10 % 10 == 0) {//整秒
							showLastSeconds((int) mTime);
							isCountDown = true;
						}
						if (isSwitch && mCurrentState != STATE_WANT_TO_CANCEL)
						{//是切换状态且当前状态不是STATE_WANT_TO_CANCEL
							showLastSeconds((int) mTime);
							isCountDown = true;
							isSwitch = false;
						}
					} else {
						isCountDown = false;
						mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
					}
					break;
				case MSG_DIALOG_DIMISS:
					mDialogManager.dismissDialog();
					break;

			}
		}
	};

	private void showLastSeconds(int mTime) {

		if (mCurrentState != STATE_WANT_TO_CANCEL) {
			if (mTime >= MAX_RECORDE_TIME) {//达到最长录音时间
				isRecording = false;//停止录音
				mDialogManager.tooLong();
				mAudioManager.release();
				if (mListener != null) {// 并且callbackActivity，保存录音
					mListener.onFinished(mTime, mAudioManager
							.getCurrentFilePath());
				}
				recordHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
				isCountDown = false;
				reset();
			} else {
//                isCountDown = false;
				mDialogManager.updateVoiceSecond(MAX_RECORDE_TIME - mTime - 1);
			}
		}
	}

	@Override
	public void wellPrepared() {
		recordHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action) {
			case MotionEvent.ACTION_DOWN:
				changeState(STATE_RECORDING);
				break;
			case MotionEvent.ACTION_MOVE:

				if (isRecording) {
					// 根据x，y来判断用户是否想要取消
					if (wantToCancel(x, y)) {
						changeState(STATE_WANT_TO_CANCEL);
					} else {
						changeState(STATE_RECORDING);
					}

				}

				break;
			case MotionEvent.ACTION_UP:
				if (!mReady) {
					reset();
					return super.onTouchEvent(event);
				}
				if (!isRecording || mTime < 0.6f) {
					if (null != mAudioManager) {
						mDialogManager.tooShort();
						mAudioManager.cancel();
						recordHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
					}
				} else if (mCurrentState == STATE_RECORDING && isRecording) {

					mDialogManager.dismissDialog();
					mAudioManager.release();
					if (mListener != null) {
						mListener.onFinished(mTime, mAudioManager
								.getCurrentFilePath());
					}

				} else if (mCurrentState == STATE_WANT_TO_CANCEL) {
					mAudioManager.cancel();
					mDialogManager.dismissDialog();
				}

				reset();
				break;

		}

		return super.onTouchEvent(event);
	}
	private void reset() {
		isRecording = false;
		changeState(STATE_NORMAL);
		mReady = false;
		mTime = 0;
	}

	private boolean wantToCancel(int x, int y) {

		if (x < 0 || x > getWidth()) {
			return true;
		}
		if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
			return true;
		}

		return false;
	}

	private void changeState(int state) {

		if (mCurrentState != state) {
			mCurrentState = state;
			switch (mCurrentState) {
				case STATE_NORMAL:
					setBackgroundResource(R.drawable.button_recordnormal);
					setText(R.string.btn_chat_voice);

					break;
				case STATE_RECORDING:
					setBackgroundResource(R.drawable.button_recording);
					setText(R.string.recording);
					if (isRecording && !isCountDown) {
						mDialogManager.recording();
					} else {
						mDialogManager.countdown();
					}
					break;

				case STATE_WANT_TO_CANCEL:
					isSwitch = true;
					setBackgroundResource(R.drawable.button_recording);
					setText(R.string.want_to_cancle);
					mDialogManager.wantToCancel();
					break;

			}
		}
	}

	@Override
	public boolean onPreDraw() {
		return false;
	}

}
