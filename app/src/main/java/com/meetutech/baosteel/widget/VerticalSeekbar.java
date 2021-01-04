package com.meetutech.baosteel.widget;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.widget
// Author: culm at 2017-08-19
//*********************************************************

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class VerticalSeekbar extends DiscreteSeekBar {
  public VerticalSeekbar(Context context) {
    super(context);
  }

  public VerticalSeekbar(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public VerticalSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected synchronized void onDraw(Canvas canvas) {
    canvas.rotate(-90);
    canvas.translate(-getHeight(),0);
    super.onDraw(canvas);
  }
}
