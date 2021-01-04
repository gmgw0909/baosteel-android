package com.meetutech.baosteel.http.result;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;

public class OnePartResult {
    private double airflow;
    private double gasflow;
    private double pressureinfrontoforifice;
    private double pressurebehindorifice;
    private double exhaustemperature;
    private double exhaustpressure;
    private double smokeo2;
    private double smokeno;
    private double smokeco2;
    private double smokeno2;
    private double smokeco;
    private double smokenox;
    private double furnacetemperature;
    private double furnaceressure;
    private List<LinkedTreeMap<String, Double>> primaryheatexchanger;
    private List<LinkedTreeMap<String, Double>> secondaryheatexchanger;
    private List<LinkedTreeMap<String, Double>> axistemperaturegroup;

    public void setAirflow(double airflow) {
        this.airflow = airflow;
    }

    public double getAirflow() {
        return airflow;
    }

    public void setGasflow(double gasflow) {
        this.gasflow = gasflow;
    }

    public double getGasflow() {
        return gasflow;
    }

    public void setPressureinfrontoforifice(double pressureinfrontoforifice) {
        this.pressureinfrontoforifice = pressureinfrontoforifice;
    }

    public double getPressureinfrontoforifice() {
        return pressureinfrontoforifice;
    }

    public void setPressurebehindorifice(double pressurebehindorifice) {
        this.pressurebehindorifice = pressurebehindorifice;
    }

    public double getPressurebehindorifice() {
        return pressurebehindorifice;
    }

    public void setExhaustemperature(double exhaustemperature) {
        this.exhaustemperature = exhaustemperature;
    }

    public double getExhaustemperature() {
        return exhaustemperature;
    }

    public void setExhaustpressure(double exhaustpressure) {
        this.exhaustpressure = exhaustpressure;
    }

    public double getExhaustpressure() {
        return exhaustpressure;
    }

    public void setSmokeo2(double smokeo2) {
        this.smokeo2 = smokeo2;
    }

    public double getSmokeo2() {
        return smokeo2;
    }

    public void setSmokeno(double smokeno) {
        this.smokeno = smokeno;
    }

    public double getSmokeno() {
        return smokeno;
    }

    public void setSmokeco2(double smokeco2) {
        this.smokeco2 = smokeco2;
    }

    public double getSmokeco2() {
        return smokeco2;
    }

    public void setSmokeno2(double smokeno2) {
        this.smokeno2 = smokeno2;
    }

    public double getSmokeno2() {
        return smokeno2;
    }

    public void setSmokeco(double smokeco) {
        this.smokeco = smokeco;
    }

    public double getSmokeco() {
        return smokeco;
    }

    public void setSmokenox(double smokenox) {
        this.smokenox = smokenox;
    }

    public double getSmokenox() {
        return smokenox;
    }

    public void setFurnacetemperature(double furnacetemperature) {
        this.furnacetemperature = furnacetemperature;
    }

    public double getFurnacetemperature() {
        return furnacetemperature;
    }

    public void setFurnaceressure(double furnaceressure) {
        this.furnaceressure = furnaceressure;
    }

    public double getFurnaceressure() {
        return furnaceressure;
    }

    public void setAxistemperaturegroup(List<LinkedTreeMap<String, Double>> axistemperaturegroup) {
        this.axistemperaturegroup = axistemperaturegroup;
    }

    public List<LinkedTreeMap<String, Double>> getAxistemperaturegroup() {
        return axistemperaturegroup;
    }

    public List<LinkedTreeMap<String, Double>> getPrimaryheatexchanger() {
        return primaryheatexchanger;
    }

    public void setPrimaryheatexchanger(List<LinkedTreeMap<String, Double>> primaryheatexchanger) {
        this.primaryheatexchanger = primaryheatexchanger;
    }

    public List<LinkedTreeMap<String, Double>> getSecondaryheatexchanger() {
        return secondaryheatexchanger;
    }

    public void setSecondaryheatexchanger(List<LinkedTreeMap<String, Double>> secondaryheatexchanger) {
        this.secondaryheatexchanger = secondaryheatexchanger;
    }
}
