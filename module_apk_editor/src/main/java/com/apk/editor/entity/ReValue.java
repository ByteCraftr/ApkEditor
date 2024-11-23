package com.apk.editor.entity;

public class ReValue {

    private String value;
    private String reValue;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReValue() {
        return reValue;
    }

    public void setReValue(String reValue) {
        this.reValue = reValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"value\":\"").append(value).append('\"');
        sb.append(",\"reValue\":\"").append(reValue).append('\"');
        sb.append('}');
        return sb.toString();
    }

}
