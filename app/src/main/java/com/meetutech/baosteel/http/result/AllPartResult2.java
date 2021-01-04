package com.meetutech.baosteel.http.result;

import java.util.List;

public class AllPartResult2 {
    private String type;
    private List<AllPartItem> values;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AllPartItem> getValues() {
        return values;
    }

    public void setValues(List<AllPartItem> values) {
        this.values = values;
    }
}
