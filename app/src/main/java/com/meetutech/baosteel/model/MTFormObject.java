package com.meetutech.baosteel.model;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model
// Author: culm at 2017-04-27
//*********************************************************

import android.content.Context;
import com.meetutech.baosteel.exception.MTValidException;
import java.io.Serializable;

public abstract class MTFormObject implements Serializable{
  protected abstract boolean checkForm(Context context) throws MTValidException;
}
