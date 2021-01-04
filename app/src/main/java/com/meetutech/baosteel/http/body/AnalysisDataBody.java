package com.meetutech.baosteel.http.body;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.body
// Author: culm at 2017-09-04
//*********************************************************

import com.meetutech.baosteel.model.MTDataObject;
import java.util.List;

public class AnalysisDataBody extends MTDataObject{

  private List<Data> data;

  public List<Data> getData() {
    return data;
  }

  public void setData(List<Data> data) {
    this.data = data;
  }

  public AnalysisDataBody(){}

  public static class Data extends MTDataObject{
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

    @Override public String toString() {
      return "Data{" + "varableId='" + varableId + '\'' + ", snapshotids=" + snapshotids + '}';
    }
  }

  @Override public String toString() {
    return "AnalysisDataBody{" + "data=" + data + '}';
  }
}
