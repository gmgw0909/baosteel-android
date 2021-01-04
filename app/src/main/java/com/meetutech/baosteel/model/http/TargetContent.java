package com.meetutech.baosteel.model.http;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.model.http
// Author: culm at 2017-08-12
//*********************************************************

import com.alibaba.android.arouter.utils.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.meetutech.baosteel.model.MTDataObject;
import java.io.Serializable;
import java.util.List;

public class TargetContent extends MTDataObject{

  private TargetTable table;
  private TargetSurface surface;
  private String analysisAliasX;
  private @SerializedName("common-dot-plot") TargetDotplot common_dot_plot;
  private @SerializedName("table-style") String tableStyle;
  private @SerializedName("surface-fill-type") String surfaceFillType;
  private @SerializedName("curve-line-quantity") String curve_line_quantity;
  private @SerializedName("curve-refresh") CurveRefresh curveRefresh;
  private @SerializedName("curve-refresh-line-quantity") String curve_refresh_line_quantity;

  private List<Curve> curve;
  //312 Dot Plot
  private List<String> page;
  private Grid grid;
  private List<List<List<List<List<String>>>>> cell;

  public TargetDotplot getCommon_dot_plot() {
    return common_dot_plot;
  }

  public void setCommon_dot_plot(TargetDotplot common_dot_plot) {
    this.common_dot_plot = common_dot_plot;
  }

  public String getCurve_line_quantity() {
    return curve_line_quantity;
  }

  public void setCurve_line_quantity(String curve_line_quantity) {
    this.curve_line_quantity = curve_line_quantity;
  }

  public String getCurve_refresh_line_quantity() {
    return curve_refresh_line_quantity;
  }

  public void setCurve_refresh_line_quantity(String curve_refresh_line_quantity) {
    this.curve_refresh_line_quantity = curve_refresh_line_quantity;
  }

  public String getAnalysisAliasX() {
    return analysisAliasX;
  }

  public void setAnalysisAliasX(String analysisAliasX) {
    this.analysisAliasX = analysisAliasX;
  }

  public List<List<List<List<List<String>>>>> getCell() {
    return cell;
  }

  public void setCell(List<List<List<List<List<String>>>>> cell) {
    this.cell = cell;
  }

  public List<Curve> getCurve() {
    return curve;
  }

  public void setCurve(List<Curve> curve) {
    this.curve = curve;
  }

  public TargetSurface getSurface() {
    return surface;
  }

  public void setSurface(TargetSurface surface) {
    this.surface = surface;
  }

  public String getSurfaceFillType() {
    return surfaceFillType;
  }

  public void setSurfaceFillType(String surfaceFillType) {
    this.surfaceFillType = surfaceFillType;
  }

  public TargetTable getTable() {
    return table;
  }

  public void setTable(TargetTable table) {
    this.table = table;
  }

  public String getTableStyle() {
    return tableStyle;
  }

  public void setTableStyle(String tableStyle) {
    this.tableStyle = tableStyle;
  }

  public List<String> getPage() {
    return page;
  }

  public void setPage(List<String> page) {
    this.page = page;
  }

  public Grid getGrid() {
    return grid;
  }

  public void setGrid(Grid grid) {
    this.grid = grid;
  }

  public CurveRefresh getCurveRefresh() {
    return curveRefresh;
  }

  public void setCurveRefresh(CurveRefresh curveRefresh) {
    this.curveRefresh = curveRefresh;
  }

  public static class TargetTable extends MTDataObject{
    private int isMerge;
    private @SerializedName("row-size") int rowSize;
    private List<TableData> data;

    public int getIsMerge() {
      return isMerge;
    }

    public void setIsMerge(int isMerge) {
      this.isMerge = isMerge;
    }

    public int getRowSize() {
      return rowSize;
    }

    public void setRowSize(int rowSize) {
      this.rowSize = rowSize;
    }

    public List<TableData> getData() {
      return data;
    }

    public void setData(List<TableData> data) {
      this.data = data;
    }

    public static class TableData extends MTDataObject{
      private String title;
      private String subTitle;
      private String variableId;
      private String unit;

      public boolean returnIsVariableText(){
        return TextUtils.isEmpty(unit);
      }

      public String getTitle() {
        return title;
      }

      public void setTitle(String title) {
        this.title = title;
      }

      public String getSubTitle() {
        return subTitle;
      }

      public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
      }

      public String getVariableId() {
        return variableId;
      }

      public void setVariableId(String variableId) {
        this.variableId = variableId;
      }

      public String getUnit() {
        return unit;
      }

      public void setUnit(String unit) {
        this.unit = unit;
      }

      @Override public String toString() {
        return "TableData{" + "title='" + title + '\'' + ", subTitle='" + subTitle + '\''
            + ", variableId='" + variableId + '\'' + ", unit='" + unit + '\'' + '}';
      }
    }

    @Override public String toString() {
      return "TargetTable{" + "isMerge=" + isMerge + ", rowSize=" + rowSize + ", data=" + data
          + '}';
    }
  }

  public static class TargetSurface extends MTDataObject{
    private String size;
    private @SerializedName("nozzle-distance") String nozzle_distance;
    private @SerializedName("min-separation") String min_separation;
    private @SerializedName("fill-type") String fill_type;
    private @SerializedName("label-x") List<String> label_x;
    private @SerializedName("label-y") List<String> label_y;

    private  @SerializedName("0") List<String> data0;
    private  @SerializedName("1") List<String> data1;
    private  @SerializedName("2") List<String> data2;
    private  @SerializedName("3") List<String> data3;
    private  @SerializedName("4") List<String> data4;
    private  @SerializedName("5") List<String> data5;
    private  @SerializedName("6") List<String> data6;
    private  @SerializedName("7") List<String> data7;
    private  @SerializedName("8") List<String> data8;
    private  @SerializedName("9") List<String> data9;
    private  @SerializedName("10") List<String> data10;
    private  @SerializedName("11") List<String> data11;
    private  @SerializedName("12") List<String> data12;
    private  @SerializedName("13") List<String> data13;
    private  @SerializedName("14") List<String> data14;

    private  List<List<String>> data;

    public List<List<String>> getAllData(){
      if(data==null||data.size()==0){

        data.add(data0);
        data.add(data1);
        data.add(data2);
        data.add(data3);
        data.add(data4);
        data.add(data5);
        data.add(data6);
        data.add(data7);
        data.add(data8);
        data.add(data9);
        data.add(data10);
        data.add(data11);
        data.add(data12);
        data.add(data13);
        data.add(data14);

        for(int i=0;i<data.size();i++){
          if(data.get(i)==null){
            data=data.subList(0,i);
            break;
          }
        }

        return data;
      } else {
        return data;
      }
    }

    public String getSize() {
      return size;
    }

    public void setSize(String size) {
      this.size = size;
    }

    public String getNozzle_distance() {
      return nozzle_distance;
    }

    public void setNozzle_distance(String nozzle_distance) {
      this.nozzle_distance = nozzle_distance;
    }

    public String getMin_separation() {
      return min_separation;
    }

    public void setMin_separation(String min_separation) {
      this.min_separation = min_separation;
    }

    public String getFill_type() {
      return fill_type;
    }

    public void setFill_type(String fill_type) {
      this.fill_type = fill_type;
    }

    public List<String> getLabel_x() {
      return label_x;
    }

    public void setLabel_x(List<String> label_x) {
      this.label_x = label_x;
    }

    public List<String> getLabel_y() {
      return label_y;
    }

    public void setLabel_y(List<String> label_y) {
      this.label_y = label_y;
    }

    public List<String> getData0() {
      return data0;
    }

    public void setData0(List<String> data0) {
      this.data0 = data0;
    }

    public List<String> getData1() {
      return data1;
    }

    public void setData1(List<String> data1) {
      this.data1 = data1;
    }

    public List<String> getData2() {
      return data2;
    }

    public void setData2(List<String> data2) {
      this.data2 = data2;
    }

    public List<String> getData3() {
      return data3;
    }

    public void setData3(List<String> data3) {
      this.data3 = data3;
    }

    public List<String> getData4() {
      return data4;
    }

    public void setData4(List<String> data4) {
      this.data4 = data4;
    }

    public List<String> getData5() {
      return data5;
    }

    public void setData5(List<String> data5) {
      this.data5 = data5;
    }

    public List<String> getData6() {
      return data6;
    }

    public void setData6(List<String> data6) {
      this.data6 = data6;
    }

    public List<String> getData7() {
      return data7;
    }

    public void setData7(List<String> data7) {
      this.data7 = data7;
    }

    public List<String> getData8() {
      return data8;
    }

    public void setData8(List<String> data8) {
      this.data8 = data8;
    }

    public List<String> getData9() {
      return data9;
    }

    public void setData9(List<String> data9) {
      this.data9 = data9;
    }

    public List<String> getData10() {
      return data10;
    }

    public void setData10(List<String> data10) {
      this.data10 = data10;
    }

    public List<String> getData11() {
      return data11;
    }

    public void setData11(List<String> data11) {
      this.data11 = data11;
    }

    public List<String> getData12() {
      return data12;
    }

    public void setData12(List<String> data12) {
      this.data12 = data12;
    }

    public List<String> getData13() {
      return data13;
    }

    public void setData13(List<String> data13) {
      this.data13 = data13;
    }

    public List<String> getData14() {
      return data14;
    }

    public void setData14(List<String> data14) {
      this.data14 = data14;
    }

    public List<List<String>> getData() {
      return data;
    }

    public void setData(List<List<String>> data) {
      this.data = data;
    }

    @Override public String toString() {
      return "TargetSurface{" + "size='" + size + '\'' + ", nozzle_distance='" + nozzle_distance
          + '\'' + ", min_separation='" + min_separation + '\'' + ", fill_type='" + fill_type + '\''
          + ", label_x=" + label_x + ", label_y=" + label_y + ", data0=" + data0 + ", data1="
          + data1 + ", data2=" + data2 + ", data3=" + data3 + ", data4=" + data4 + ", data5="
          + data5 + ", data6=" + data6 + ", data7=" + data7 + ", data8=" + data8 + ", data9="
          + data9 + ", data10=" + data10 + ", data11=" + data11 + ", data12=" + data12 + ", data13="
          + data13 + ", data14=" + data14 + ", data=" + data + '}';
    }
  }

  public static class Grid extends MTDataObject {
    private List<List<List<String>>> name;
    private List<List<List<String>>> size;

    public List<List<List<String>>> getName() {
      return name;
    }

    public void setName(List<List<List<String>>> name) {
      this.name = name;
    }

    public List<List<List<String>>> getSize() {
      return size;
    }

    public void setSize(List<List<List<String>>> size) {
      this.size = size;
    }


  }

  @Override public String toString() {
    return "TargetContent{" + "table=" + table + ", surface=" + surface + ", tableStyle='"
        + tableStyle + '\'' + ", surfaceFillType='" + surfaceFillType + '\'' + '}';
  }

  public static class TargetDotplot implements Serializable{

    private String size;
    private String background;
    private List<List<String>> data;

    public String getSize() {
      return size;
    }

    public void setSize(String size) {
      this.size = size;
    }

    public String getBackground() {
      return background;
    }

    public void setBackground(String background) {
      this.background = background;
    }

    public List<List<String>> getData() {
      return data;
    }

    public void setData(List<List<String>> data) {
      this.data = data;
    }
  }

  public static class CurveRefresh {
    private @SerializedName("1") CurveSubLine line1;
    private @SerializedName("2") CurveSubLine line2;
    private @SerializedName("3") CurveSubLine line3;
    private @SerializedName("4") CurveSubLine line4;
    private @SerializedName("5") CurveSubLine line5;
    private @SerializedName("6") CurveSubLine line6;
    private @SerializedName("7") CurveSubLine line7;
    private @SerializedName("8") CurveSubLine line8;
    private @SerializedName("9") CurveSubLine line9;

    private CurveSubLine[] allLines=new CurveSubLine[9];



    private int noLine;
    private @SerializedName("dot-quantity") String dot_quantity;
    private @SerializedName("dot-label") List<String> dot_label;
    private List<CurveRefreshDot> line;

    public CurveSubLine[] getAllLines() {
      return allLines;
    }

    public void setAllLines(CurveSubLine[] allLines) {
      this.allLines = allLines;
    }

    public void initAllLines(){
      allLines[0]=line1;
      allLines[1]=line2;
      allLines[2]=line3;
      allLines[3]=line4;
      allLines[4]=line5;
      allLines[5]=line6;
      allLines[6]=line7;
      allLines[7]=line8;
      allLines[8]=line9;
    }

    public CurveSubLine getLine1() {
      return line1;
    }

    public void setLine1(CurveSubLine line1) {
      this.line1 = line1;
    }

    public CurveSubLine getLine2() {
      return line2;
    }

    public void setLine2(CurveSubLine line2) {
      this.line2 = line2;
    }

    public CurveSubLine getLine3() {
      return line3;
    }

    public void setLine3(CurveSubLine line3) {
      this.line3 = line3;
    }

    public CurveSubLine getLine4() {
      return line4;
    }

    public void setLine4(CurveSubLine line4) {
      this.line4 = line4;
    }

    public CurveSubLine getLine5() {
      return line5;
    }

    public void setLine5(CurveSubLine line5) {
      this.line5 = line5;
    }

    public CurveSubLine getLine6() {
      return line6;
    }

    public void setLine6(CurveSubLine line6) {
      this.line6 = line6;
    }

    public CurveSubLine getLine7() {
      return line7;
    }

    public void setLine7(CurveSubLine line7) {
      this.line7 = line7;
    }

    public CurveSubLine getLine8() {
      return line8;
    }

    public void setLine8(CurveSubLine line8) {
      this.line8 = line8;
    }

    public CurveSubLine getLine9() {
      return line9;
    }

    public void setLine9(CurveSubLine line9) {
      this.line9 = line9;
    }

    public int getNoLine() {
      return noLine;
    }

    public void setNoLine(int noLine) {
      this.noLine = noLine;
    }

    public String getDot_quantity() {
      return dot_quantity;
    }

    public void setDot_quantity(String dot_quantity) {
      this.dot_quantity = dot_quantity;
    }

    public List<String> getDot_label() {
      return dot_label;
    }

    public void setDot_label(List<String> dot_label) {
      this.dot_label = dot_label;
    }

    public List<CurveRefreshDot> getLine() {
      return line;
    }

    public void setLine(List<CurveRefreshDot> line) {
      this.line = line;
    }
  }

  public static class CurveSubLine {
    private String label;
    private String color;
    private @SerializedName("shot-dot") String shot_dot;
    private @SerializedName("show-fillcolor") String show_fillcolor;

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }

    public String getShot_dot() {
      return shot_dot;
    }

    public void setShot_dot(String shot_dot) {
      this.shot_dot = shot_dot;
    }

    public String getShow_fillcolor() {
      return show_fillcolor;
    }

    public void setShow_fillcolor(String show_fillcolor) {
      this.show_fillcolor = show_fillcolor;
    }

    @Override public String toString() {
      return "CurveSubLine{" + "label='" + label + '\'' + ", color='" + color + '\''
          + ", shot_dot='" + shot_dot + '\'' + ", show_fillcolor='" + show_fillcolor + '\'' + '}';
    }
  }

  public static class CurveRefreshDot {
    private List<String> dot;

    public List<String> getDot() {
      return dot;
    }

    public void setDot(List<String> dot) {
      this.dot = dot;
    }

    @Override public String toString() {
      return "CurveRefreshDot{" + "dot=" + dot + '}';
    }
  }
}
