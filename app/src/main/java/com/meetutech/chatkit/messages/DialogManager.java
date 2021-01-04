package com.meetutech.chatkit.messages;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meetutech.baosteel.R;

public class DialogManager {

	private Dialog mDialog;
	private ImageView mIcon;
	private ImageView mVoice;
	private TextView mLable;
	private Context mContext;

	public DialogManager(Context context) {
		mContext = context;
	}

	public void showRecordingDialog() {

		mDialog = new Dialog(mContext, R.style.Theme_audioDialog);
		// 用layoutinflater来引用布局
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.dialog_manager, null);
		mDialog.setContentView(view);

		mIcon = (ImageView) mDialog.findViewById(R.id.dialog_icon);
		mVoice = (ImageView) mDialog.findViewById(R.id.dialog_voice);
		mLable = (TextView) mDialog.findViewById(R.id.recorder_dialogtext);
		mDialog.show();

	}

	public void recording() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.VISIBLE);
			mLable.setVisibility(View.VISIBLE);

			mIcon.setImageResource(R.drawable.recorder);
			mLable.setText(R.string.shouzhishanghua);
			mLable.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	public void countdown() {
		if (mDialog != null && mDialog.isShowing()) {
			mLable.setText(R.string.shouzhishanghua);
			mLable.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	public void wantToCancel() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLable.setVisibility(View.VISIBLE);

			mIcon.setImageResource(R.drawable.cancel);
			mLable.setText(R.string.want_to_cancle);
			mLable.setBackgroundColor(Color.parseColor("#a50016"));
		}

	}

	public void tooShort() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLable.setVisibility(View.VISIBLE);

			mIcon.setImageResource(R.drawable.voice_to_short);
			mLable.setText(R.string.tooshort);
			mLable.setBackgroundColor(Color.parseColor("#a50016"));
		}
	}
	public void tooLong() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLable.setVisibility(View.VISIBLE);

			mIcon.setImageResource(R.drawable.voice_to_short);
			mLable.setText(R.string.tooshort);
			mLable.setBackgroundColor(Color.parseColor("#a50016"));
		}
	}

	public void dismissDialog() {

		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
			mDialog = null;
		}

	}

	public void updateVoiceLevel(int level) {

		if (mDialog != null && mDialog.isShowing()) {
			int resId = mContext.getResources().getIdentifier("v" + level,
					"drawable", mContext.getPackageName());
			mVoice.setImageResource(resId);
		}

	}


	public void updateVoiceSecond(int second) {
		if (mDialog != null && mDialog.isShowing()) {
			mVoice.setVisibility(View.GONE);
			int resId = mContext.getResources().getIdentifier("s_" + second,
					"drawable", mContext.getPackageName());
			mIcon.setImageResource(resId);
		}
	}

}