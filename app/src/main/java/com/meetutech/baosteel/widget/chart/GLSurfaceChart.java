package com.meetutech.baosteel.widget.chart;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.widget.chart
// Author: culm at 2017-08-19
//*********************************************************

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.meetutech.baosteel.model.http.TargetContent;
import com.meetutech.baosteel.utils.ViewUtils;

public class GLSurfaceChart extends GLSurfaceView {

  private float mPreviousX;
  private float mPreviousY;

  private float mDensity;


  private TargetContent.TargetSurface surfaceData;
  private ChartSurfaceRender mRenderer;
  private float density;

  public GLSurfaceChart(Context ctx) {
    super(ctx);
  }

  public GLSurfaceChart(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public TargetContent.TargetSurface getSurfaceData() {
    return surfaceData;
  }

  public void setSurfaceData(TargetContent.TargetSurface surfaceData) {
    this.surfaceData = surfaceData;
  }

  public void initScene(TargetContent.TargetSurface surfaceData){
    this.surfaceData=surfaceData;
    setEGLContextClientVersion(2);
    setEGLConfigChooser(8, 8, 8, 8, 16, 0);
    getHolder().setFormat(PixelFormat.TRANSPARENT);
    mRenderer=new ChartSurfaceRender(getContext());
    density = ViewUtils.getScreenResolution((Activity) getContext()).density;
    setRenderer(mRenderer,density);
    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
  }


  public boolean onTouchEvent(MotionEvent event)
  {
    if (event != null)
    {
      float x = event.getX();
      float y = event.getY();

      if (event.getAction() == MotionEvent.ACTION_MOVE)
      {
        if (mRenderer != null)
        {
          float deltaX = (x - mPreviousX) / mDensity / 2f;
          float deltaY = (y - mPreviousY) / mDensity / 2f;

          mRenderer.mDeltaX += deltaX;
          mRenderer.mDeltaY += deltaY;
        }
      }

      mPreviousX = x;
      mPreviousY = y;

      return true;
    }
    else
    {
      return super.onTouchEvent(event);
    }
  }

  public void setRenderer(ChartSurfaceRender renderer, float density)
  {
    mRenderer = renderer;
    mDensity = density;
    super.setRenderer(renderer);
  }
}
