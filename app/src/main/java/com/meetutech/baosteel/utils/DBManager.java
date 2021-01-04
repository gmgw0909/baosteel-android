package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-04-26
//*********************************************************

import android.content.Context;
import com.meetutech.baosteel.BSApplication;
import com.meetutech.baosteel.model.MTDataObject;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import java.util.Arrays;
import java.util.List;

public class DBManager {

  private static DB instance;
  private static String MAIN_DATA_BASE = "baosteel.app";

  /**
   * getInstance
   *
   * @param cxt current Context
   */
  public synchronized static DB getInstance(Context cxt) {
    try {
      if (instance == null || !instance.isOpen()) {
        instance = DBFactory.open(cxt,MAIN_DATA_BASE);
      }
    } catch (SnappydbException e) {
      e.printStackTrace();
    } catch (Exception e){
      e.printStackTrace();
    }
    return instance;
  }

  /**
   * Destroy DB
   */
  public synchronized static boolean destroy() {
    try {
      if (instance == null || !instance.isOpen()) {
        return true;
      }
      instance.close();
    } catch (SnappydbException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static synchronized void init(Context context) {
    getInstance(context);
  }

  /**
   * Insert Cache
   *
   * @param key Cache Key
   * @param data Cache Key
   */
  public static boolean insertCache(String key, MTDataObject data) {

    try {
      if (instance == null || !instance.isOpen()) {
        init(BSApplication.getInstance());
      }
      instance.put(key, data);
    } catch (SnappydbException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Insert Array Cache
   */
  public static synchronized boolean insertArrayCache(String key, List<MTDataObject> cache) {

    try {
      if (instance == null || !instance.isOpen()) {
        init(BSApplication.getInstance());
      }
      instance.put(key, cache);
    } catch (SnappydbException e) {
      e.printStackTrace();
    }

    return true;
  }

  public static synchronized boolean removeCache(String key) {
    try {
      if (instance == null || !instance.isOpen()) {
        init(BSApplication.getInstance());
      }
      instance.del(key);
    } catch (SnappydbException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static Object getCache(String key) {
    try {

      if(!instance.isOpen()){
        init(BSApplication.getInstance());
      }
      return instance.getObject(key, MTDataObject.class).get_content();
    } catch (SnappydbException e) {
      e.printStackTrace();
      return null;
    } catch (NullPointerException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static synchronized List<MTDataObject> getArrayCache(String key) {
    try {
      return Arrays.asList(instance.getObjectArray(key, MTDataObject.class));
    } catch (SnappydbException e) {
      e.printStackTrace();
    }
    return null;
  }

}
