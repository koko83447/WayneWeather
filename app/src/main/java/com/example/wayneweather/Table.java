package com.example.wayneweather;

import java.io.Serializable;

public class Table implements Serializable {
    private String startTime,endTime,parameterName,parameterUnit;

    public Table(String startTime, String endTime, String parameterName, String parameterUnit) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.parameterName = parameterName;
        this.parameterUnit = parameterUnit;
    }

    public Table(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterUnit() {
        return parameterUnit;
    }

    public void setParameterUnit(String parameterUnit) {
        this.parameterUnit = parameterUnit;
    }
}
