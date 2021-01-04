package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-09-04
//*********************************************************

import android.text.TextUtils;
import com.lzy.ninegrid.ImageInfo;
import com.meetutech.baosteel.common.HTTPConstant;
import com.meetutech.baosteel.model.MTDataObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Snapshot extends MTDataObject{
  private String id;
  private long date;
  private boolean confirm;
  private boolean release;
  private String description;
  private boolean enabled;
  private String workstatId;
  private SnapshotMedia media;
  private WorkStatSnapshot workstat;
  private List<HashMap<String,String>> types;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public boolean isConfirm() {
    return confirm;
  }

  public void setConfirm(boolean confirm) {
    this.confirm = confirm;
  }

  public boolean isRelease() {
    return release;
  }

  public void setRelease(boolean release) {
    this.release = release;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getWorkstatId() {
    return workstatId;
  }

  public void setWorkstatId(String workstatId) {
    this.workstatId = workstatId;
  }

  public WorkStatSnapshot getWorkstat() {
    return workstat;
  }

  public void setWorkstat(WorkStatSnapshot workstat) {
    this.workstat = workstat;
  }

  public SnapshotMedia getMedia() {
    return media;
  }

  public void setMedia(SnapshotMedia media) {
    this.media = media;
  }

  public List<HashMap<String, String>> getTypes() {
    return types;
  }

  public void setTypes(List<HashMap<String, String>> types) {
    this.types = types;
  }

  public String getName() {
    String res=getWorkstat().getName();

    if(!TextUtils.isEmpty(getDescription())){
      res=getDescription();
    }

    return res;
  }

  public static class SnapshotMedia {
    private List<String> image;
    private List<String> video;

    public List<String> getImage() {
      return image;
    }

    public void setImage(List<String> image) {
      this.image = image;
    }

    public List<String> getVideo() {
      return video;
    }

    public void setVideo(List<String> video) {
      this.video = video;
    }

    public List<ImageInfo> genereateImageInfos(){
      List<ImageInfo> infos=new ArrayList<>();

      for(String subImage:image){
        ImageInfo info=new ImageInfo();
        info.setBigImageUrl(HTTPConstant.STATIC_FILES_HOST+subImage);
        info.setThumbnailUrl(HTTPConstant.STATIC_FILES_HOST+subImage);
        infos.add(info);
      }
      //
      //for(int i=0;i<12;i++){
      //    ImageInfo info=new ImageInfo();
      //    info.setBigImageUrl("http://imgsrc.baidu.com/imgad/pic/item/b21c8701a18b87d69bb9d13b0d0828381f30fd1f.jpg");
      //    info.setThumbnailUrl("http://imgsrc.baidu.com/imgad/pic/item/b21c8701a18b87d69bb9d13b0d0828381f30fd1f.jpg");
      //    infos.add(info);
      //}

      return infos;
    }

  }

  @Override public String toString() {
    return "Snapshot{" + "id='" + id + '\'' + ", date=" + date + ", confirm=" + confirm
        + ", release=" + release + ", enabled=" + enabled + ", workstatId='" + workstatId + '\''
        + ", media=" + media + ", workstat=" + workstat + ", types=" + types + '}';
  }
}
