package com.meetutech.baosteel.http.body;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.body
// Author: culm at 2017-09-04
//*********************************************************

import com.meetutech.baosteel.model.MTDataObject;
import java.util.ArrayList;
import java.util.List;

public class SnapshotDataBody extends MTDataObject{

  private List<Data> data;

  public List<Data> getData() {
    return data;
  }

  public void setData(List<Data> data) {
    this.data = data;
  }

  public SnapshotDataBody(){}

  public SnapshotDataBody(String snapshotId,String variableId){
    Data data=new Data();
    data.setVarableId(variableId);
    List<String> snapshotIds=new ArrayList<>();
    snapshotIds.add(snapshotId);
    data.setSnapshotids(snapshotIds);
    List<Data> datas=new ArrayList<>();
    datas.add(data);
    this.data=datas;
  }

  public SnapshotDataBody(List<String> snapshotIds,String variableId){
    Data data=new Data();
    data.setVarableId(variableId);
    data.setSnapshotids(snapshotIds);
    List<Data> datas=new ArrayList<>();
    datas.add(data);
    this.data=datas;
  }

  public static class Data {
    private String varableId;
    private List<String> snapshotids;

    public String getVarableId() {
      return varableId;
    }

    public void setVarableId(String varableId) {
      this.varableId = varableId;
    }

    public List<String> getSnapshotids() {
      return snapshotids;
    }

    public void setSnapshotids(List<String> snapshotids) {
      this.snapshotids = snapshotids;
    }
  }
}
